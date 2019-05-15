package modulator;

import java.io.FileWriter; 
import java.io.IOException;

public class ModulateSignal {
	int[] frequencySequence;
	int[] inputStream;
	int[] modulatedStream;
	int timeInterval;
	
	public void setFrequencySequence(int[] frequencySequence) {
		this.frequencySequence = new int[frequencySequence.length];
		this.frequencySequence = frequencySequence.clone();
	}
	
	public void setInputStream(int[] inputStream) {
		this.inputStream = new int[inputStream.length];
		this.inputStream = inputStream.clone();
	}
	
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	public void modulate() {
		int counter = 0;
		int carrierFrequency;
		int frequencyIndex = -1;
		this.modulatedStream = new int[inputStream.length];
		for(int i = 0; i < inputStream.length; i++, counter++) {
			if(counter % timeInterval == 0) {
				frequencyIndex = ++frequencyIndex % 8; //8 different frequencies.
			}
			int bit = inputStream[i];
			carrierFrequency = frequencySequence[frequencyIndex];
			if(bit == 0) {
				carrierFrequency -= 50;
			}else {
				carrierFrequency += 50;
			}
			modulatedStream[i] = carrierFrequency;
		}
	}
	
	public int[] getModulatedStream() {
		return modulatedStream;
	}
	
	public void modualtedStreamToFile() throws IOException {
		FileWriter outputFile = new FileWriter("ModulatedStream.txt");
		for (int i = 0; i < modulatedStream.length; i++) {
			//Boxing to Integer and obtaining the string value.
			Integer outBit = modulatedStream[i];
			outputFile.write(outBit.toString());
			outputFile.write("\n");
		}
		outputFile.close();
	}
	
	public void frequencyInfoToFile() throws IOException {
		FileWriter outputFile = new FileWriter("FrequencyInformation.txt");
		//Write time period
		Integer outBit = timeInterval;
		outputFile.write(outBit.toString());
		outputFile.write("\n");
		//Write frequency sequence
		for (int i = 0; i < frequencySequence.length; i++) {
			//Boxing to Integer and obtaining the string value.
			outBit = frequencySequence[i];
			outputFile.write(outBit.toString());
			outputFile.write("\n");
		}
		outputFile.close();
	}

}

