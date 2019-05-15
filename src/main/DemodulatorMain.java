package main;

import signals.*;
import demodulator.*;

import javax.swing.*;

import connections.Client;

import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;


public class DemodulatorMain {
	public static void main(String args[]) {

		//Establish connection to server and receive files.
		try {
			Client client = new Client();
			client.connect();
			client.receiveFile("frequencyInformation.txt");
			System.out.println("Frequency Information Received");
			client.receiveFile("modulatedStream.txt");
			System.out.println("Modulated Stream Received");
			client.close();
		}catch (Exception exception) {
			System.out.println(exception.getMessage());
			System.exit(0);
		}

		
		//Initialize a JFrame
		JFrame frame = new JFrame("Demodulator");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(1250, 500); //SET IT TO SCREEN SIZE
		frame.setLocation(0, 250);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
		
		//Demodulate signal
		DemodulateSignal demodulateSignal = new DemodulateSignal();
		//Read the frequency information.
		try {
			demodulateSignal.setFrequencyInfoFromFile("FrequencyInformation.txt");
		}catch(IOException IOE) {
			System.out.println(IOE.getMessage());
		}
		//Read the modulated stream.
		try {
			demodulateSignal.setModulatedStreamFromFile("ModulatedStream.txt");
		}catch(IOException IOE) {
			System.out.println(IOE.getMessage());
		}
		//Demodulate
		demodulateSignal.demodulate();
		
		/*
		//Create a label to display demodulated bit sequence.
		JLabel bitStreamLabel = new JLabel("The bit stream is: ");
		frame.add(bitStreamLabel);
		frame.setVisible(true);
		bitStreamLabel.setLocation(100, 100);
		bitStreamLabel.setSize(250, 50);
		bitStreamLabel.setForeground(new Color(255, 102, 102)); //Red shade
		bitStreamLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
		*/
		
		String bitString = "The bit stream is: ";
		JLabel bitStreamData = new JLabel(bitString);
		frame.add(bitStreamData);
		frame.setVisible(true);
		bitStreamData.setLocation(100, 50);
		bitStreamData.setSize(500, 50);
		bitStreamData.setForeground(new Color(255, 102, 102)); //Red shade
		bitStreamData.setFont(new Font("Times New Roman", Font.BOLD, 26));
		
		String outputStream = demodulateSignal.getDemodulatedString();
		JLabel[] outputArray = new JLabel[outputStream.length()/30 + 1];
		for(int i = 0, j = 0; i < outputStream.length(); i = i + 30, j++) {
			String data;
			try {
				data = outputStream.substring(i, i + 30);
				System.out.println(data);
			}catch(Exception e) {
				data = outputStream.substring(i);
			}
			outputArray[j] = new JLabel(data);
			outputArray[j].setLocation(100, 50 * (j + 2));
			outputArray[j].setSize(500, 50);
			outputArray[j].setForeground(new Color(255, 102, 102)); //Red shade
			outputArray[j].setFont(new Font("Times New Roman", Font.BOLD, 26));
			frame.add(outputArray[j]);
			frame.setVisible(true);
		}
		
		//Displaying output signal
		int[] bitStream = demodulateSignal.getDemodulatedStream();
		DigitalSignal outputSignal = new DigitalSignal();
		outputSignal.setCode(bitStream);
		//inputSignal.animateSignal();
	    frame.add(outputSignal);
	    outputSignal.start();
	    outputSignal.setSize(500, 250);
	  	outputSignal.setLocation(600, 100);
	  	outputSignal.setBackground(Color.BLACK);
	    frame.setVisible(true);
		
		
	}
}
