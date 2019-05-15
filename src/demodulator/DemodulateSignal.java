package demodulator;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DemodulateSignal {
	int[] modulatedStream;
	int[] demodulatedStream;
	int[] frequencySequence;
	int period;

	public void setModulatedStream(int[] modulatedStream) {
		this.modulatedStream = new int[modulatedStream.length];
		this.demodulatedStream = new int[modulatedStream.length];
		this.modulatedStream = modulatedStream.clone();
	}
	
	public void setFrequencySequence(int[] frequencySequence) {
		this.frequencySequence = new int[frequencySequence.length];
		this.frequencySequence = frequencySequence.clone();
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public void setModulatedStreamFromFile(String fileName) throws IOException  {
		List<Integer> ModStream = new ArrayList<Integer>();
		Scanner scanner = new Scanner(new File(fileName));	
		//Scan the file contents into a list of integers.
	    while(scanner.hasNextInt()) {
	    	ModStream.add(scanner.nextInt());
	    }
	    //Convert List to an array.
	    modulatedStream = new int[ModStream.size()];
	    Integer[] tempStream = new Integer[ModStream.size()];
	    ModStream.toArray(tempStream);
	    for(int i = 0; i < modulatedStream.length; i++) {
	    	modulatedStream[i] = (int)tempStream[i];
	    }
	    scanner.close();
	    //Allocate memory for demodulated stream.
	    this.demodulatedStream = new int[modulatedStream.length];
	}
	
	public void setFrequencyInfoFromFile(String fileName) throws IOException  {
		List<Integer> freqSequence = new ArrayList<Integer>();
		Scanner scanner = new Scanner(new File(fileName));
		//First data gives period
		if(scanner.hasNextInt()) {
			period = scanner.nextInt();
		}
		//Next set of data gives frequency sequence
	    while(scanner.hasNextInt()) {
	    	freqSequence.add(scanner.nextInt());
	    }
	    //Convert List to an array.
	    frequencySequence = new int[freqSequence.size()];
	    Integer[] tempStream = new Integer[freqSequence.size()];
	    freqSequence.toArray(tempStream);
	    for(int i = 0; i < frequencySequence.length; i++) {
	    	frequencySequence[i] = (int)tempStream[i];
	    }
	    scanner.close();
	}
	
	
	public void demodulate() {
		int counter = 0;
		int carrierFrequency;
		int frequencyIndex = -1;
		int delta;
		
		for(int i = 0; i < modulatedStream.length; i++, counter++) {
			if(counter % period == 0) {
				frequencyIndex = ++frequencyIndex % 8; //8 different frequencies.
			}
			carrierFrequency = frequencySequence[frequencyIndex];
			delta = modulatedStream[i] - carrierFrequency;
			if(delta == 50) {
				 demodulatedStream[i] = 1;
			}else {
				demodulatedStream[i] = 0;
			}
		}	
	}
	
	public int[] getDemodulatedStream() {
		return demodulatedStream;
	}
	
	public String getDemodulatedString() {
		String demodulatedString = "";
		for(int i: demodulatedStream) {
			demodulatedString = demodulatedString + " " + i;
		}
		return demodulatedString;
	}
	
	/*
	public void demodualtedStreamToFile() throws IOException {
		FileWriter outputFile = new FileWriter("DemodulatedStream.txt");
		for (int i = 0; i < demodulatedStream.length; i++) {
			outputFile.write(demodulatedStream[i]);
		}
		outputFile.close();
	}
	*/
	
	/*
	public static void main(String args[]) {
		
		DemodulateSignal demodulateSignal = new DemodulateSignal();
		
		try {
			demodulateSignal.setFrequencyInfoFromFile("FrequencyInformation.txt");
		}catch(IOException IOE) {
			System.out.println(IOE.getMessage());
		}
		
		try {
			demodulateSignal.setModulatedStreamFromFile("ModulatedStream.txt");
		}catch(IOException IOE) {
			System.out.println(IOE.getMessage());
		}
		
		
		demodulateSignal.demodulate();
		int[] demodulatedStream;
		demodulatedStream = demodulateSignal.getDemodulatedStream();
		
		for(int i: demodulatedStream) {
			System.out.print(" " + i);
		}
		
		System.out.print(demodulatedStream.toString());
	}
	*/
}
