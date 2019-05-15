package main;
import input.*;
import randomFrequencies.*;
import modulator.*;
import signals.*;
import graphs.*;
import connections.Server;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;

public class ModulatorMain {
	
	public static void main(String args[]) {
	
		//Initialize a JFrame
		JFrame frame = new JFrame("Modulator");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		//Generate the frequency sequence
		PseudoRandomCodeGenerator pseudoRandomCodeGenerator = new PseudoRandomCodeGenerator();
		pseudoRandomCodeGenerator.frequencySelect();
		int[] frequencySequence;
		frequencySequence = pseudoRandomCodeGenerator.getFrequencySequence();
		
		//Creating an input panel and reading input.
		inputReader inputReader = new inputReader();
		inputReader.getUserInput();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		JPanel inputPanel = inputReader.getInputPanel();
		frame.add(inputPanel);
		inputPanel.setLocation(150, 50);
		inputPanel.setSize(250, 150);
		inputPanel.setBorder(blackline);
		frame.setVisible(true);
		
		//Collect data from user
		while(inputReader.isDataSet() == false) {
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}//Busy wait until data is available.
		int[] inputDataStream = inputReader.getInputData();
		int period = inputReader.getHopPeriod();
		
		//Displaying input signal
		DigitalSignal inputSignal = new DigitalSignal();
		inputSignal.setCode(inputDataStream);
		inputSignal.start();
	    frame.add(inputSignal);
	    inputSignal.setLocation(800, 20);
	    inputSignal.setSize(500, 200);
	  	inputSignal.setBackground(Color.BLACK);
	  	inputSignal.setVisible(true);
	  	//frame.setVisible(true);
	  	
		//FHSS Graph
		FHSSGraph graph = new FHSSGraph();
		graph.setFrequencySequence(frequencySequence);
	    frame.add(graph);
		graph.setLocation(70, 50);
		graph.setSize(1100, 1100);
		graph.setBackground(Color.BLACK);
		graph.setVisible(true);
			    
	    //Displaying Carrier Signal
	    AnalogSignal  carrierSignal = new AnalogSignal();
		carrierSignal.setFrequencySequency(frequencySequence);
		carrierSignal.setHoppingPeriod(period);
		carrierSignal.setFrequencyList();
		carrierSignal.setDataPoints();
        frame.add(carrierSignal);
        carrierSignal.start();
        carrierSignal.setLocation(800, 270);
        carrierSignal.setSize(500,200);
        carrierSignal.setBackground(Color.BLACK);
        frame.setVisible(true);
        
        //Modulating the carrier
        ModulateSignal modulateSignal = new ModulateSignal();
		modulateSignal.setFrequencySequence(frequencySequence);
		modulateSignal.setInputStream(inputDataStream);
		modulateSignal.setTimeInterval(period);
		modulateSignal.modulate();
		int[] modulatedStream;
		modulatedStream = modulateSignal.getModulatedStream();
		try {
			modulateSignal.frequencyInfoToFile();
		}catch(IOException IOE) {
			System.out.println(IOE.getMessage());
		}
		try {
			modulateSignal.modualtedStreamToFile();
		}catch(IOException IOE) {
			System.out.println(IOE.getMessage());
		}
		
		//Displaying the modulated signal.
		AnalogSignal  modulatedSignal = new AnalogSignal();
		modulatedSignal.setFrequencySequency(modulatedStream);
		modulatedSignal.setHoppingPeriod(1);
		modulatedSignal.setFrequencyList();
		modulatedSignal.setDataPoints();
        frame.add(modulatedSignal);
        modulatedSignal.start();
        modulatedSignal.setLocation(800, 520);
        modulatedSignal.setSize(500,200);
        modulatedSignal.setBackground(Color.BLACK);
        frame.setVisible(true);
        
        frame.setVisible(false);
        try {
        	Thread.sleep(10);
        }catch(InterruptedException e) {
        	e.printStackTrace();
        }
        frame.setVisible(true);
        
        //Establishing connection to client and transferring the message.
        try {
        	Server server = new Server();
    		server.connect();
    		server.sendFile("FrequencyInformation.txt");
    		System.out.println("Frequency information sent");
    		server.sendFile("ModulatedStream.txt");
    		System.out.println("Modulated Stream sent");
    		server.close();
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
        
        //frame.setLayout(new GridLayout(3, 1));
        JLabel input=new JLabel("Input Signal ");
        JLabel fhssgraph=new JLabel("FHSS Graph");
        JLabel inputsignal=new JLabel("Input Signal");
        JLabel carriersignal=new JLabel("Carrier Signal");
        JLabel modulatedsignal=new JLabel("Modulated Signal");
        
        frame.add(input);
        Dimension size=input.getPreferredSize();
        input.setBounds(20,20,90,50);
        
        frame.add(fhssgraph);
        size=fhssgraph.getPreferredSize();
        fhssgraph.setBounds(10,520,90,50);
        
        frame.add(inputsignal);
        size=inputsignal.getPreferredSize();
        input.setBounds(650,100,90,50);
        
        frame.add(carriersignal);
        size=carriersignal.getPreferredSize();
        carriersignal.setBounds(650,320,90,50);
        
        frame.add(modulatedsignal);
        size=modulatedsignal.getPreferredSize();
        modulatedsignal.setBounds(635,620,120,50);
	}

}
