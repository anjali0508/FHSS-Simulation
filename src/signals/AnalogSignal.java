package signals;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AnalogSignal extends JPanel implements Runnable{
	
	//Variables for storing frequency related information.
	int[] frequencySequence; 
	int[] frequencyList; //An array of frequencies repeated according to the hopping period.
	int frequencyPeriod;
	int frequency;
	
	//Variables for creating a sine wave.
	final int numberOfPoints;
	int[] dataPoints;
	final int AMPLITUDE = 75;
	final int SCALE = 200;
		
	//Variables for animation.
	int startX = 50; //Indicates starting X coefficient for graph.
	int frequencyCounter = 0;
	int dataPointCounter = 0;
	
	public AnalogSignal() {
		numberOfPoints = SCALE * 2;
		dataPoints = new int[numberOfPoints];
	}
	
	public void setFrequencySequency(int[] frequencySequence) {
		this.frequencySequence = new int[frequencySequence.length];
		this.frequencySequence = frequencySequence.clone();
		frequency = frequencySequence[0];
	}
	
	public void setHoppingPeriod(int period) {
		frequencyPeriod = period;
	}
	
	public void setFrequencyList() {
		int frequencyIndex = -1; //Index to pick frequencies from frequencySequence.
		this.frequencyList = new int[frequencySequence.length * frequencyPeriod];
		
		for(int i = 0, counter = 0; i < frequencyList.length; i++, counter++) {
			if (counter%frequencyPeriod == 0) {
				frequencyIndex++;
			}
			frequencyList[i] = frequencySequence[frequencyIndex];
		}
	}
	
	//Setting the data points required for one cycle.
	public void setDataPoints() {
		for(int i = 0; i < numberOfPoints; i++) {
			double radian = Math.PI / SCALE * i + Math.PI; //If not +PI wave gets inverted because y increases downwards on the screen.
			dataPoints[i] = (int) (Math.sin(radian) * AMPLITUDE); 
		}	
	}
	
	
	public void start() {
		new Thread(this).start();
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(25);
				dataPointCounter = (dataPointCounter + 10) % 400;
				if(dataPointCounter == 0) {
					frequencyCounter = (frequencyCounter + 1) % frequencyList.length;
				}
			    repaint();
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);//Clears component before repainting
        g.setColor(Color.CYAN);
		int startX = 500;//Initialize starting x coordinate.
		int x1 = 0, y1 = 0; //Coordinates to plot the wave

		for(int j = frequencyCounter; startX > 0; j = (j + 1) % frequencyList.length) {
			frequency = frequencyList[j];
			//Selecting an appropriate wavelength = 50000.
			double stepSize = (double) 50000 / (numberOfPoints * frequency);
			//System.out.println(counter + " " + j + " " + frequency);
			int k = 0;
			if(startX == 500) {
				k = dataPointCounter;
			}
			for(int i = 0; k < numberOfPoints; i++, k++) {
				x1 = startX - (int)(i * stepSize) ;
				y1 = dataPoints[k] + 100;	
				g.drawOval(x1, y1, 2, 2);
			}
			startX = x1;
		}
		
	}
	
	/*
	public static void main(String[] args) {
		AnalogSignal s = new AnalogSignal();
		int[] a = {300, 1500,500,1000};
		s.setFrequencySequency(a);
		s.setHoppingPeriod(2);
		s.setFrequencyList();
		for(int i : s.frequencyList) {
			System.out.println(i);
		}
		
        s.start();
        s.setSize(500, 600);
        s.setDataPoints();
        s.setVisible(true);
        JFrame f = new JFrame();
        f.add(s);
        f.setSize(1000, 1000);
        f.setVisible(true);
        
    }	
    */
}


