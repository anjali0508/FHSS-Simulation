package randomFrequencies;

import java.util.Arrays;

import signals.AnalogSignal;

class RandomNumberGenerator{
	int a = 5;
	int b = 11;
	int m = 8;
	//Use current time as initial seed for the random number generator.
	int seed = (int) System.currentTimeMillis(); //Convert long to int
	int y;     //Xn+1
	
	//Formula used: Xn+1 = (a * seed + b) mod m.
	int generateRandom() {
		y = (a * seed + b) % m;
		seed = y;
		return y;
	}
}

public class PseudoRandomCodeGenerator {
	//int[] kbitArray = {000, 001, 010, 011, 100, 101, 110, 111};
	//int[] bitPattern = new int[8];  //Array storing random bit pattern
	int[] frequencyList = {300, 400, 500, 600, 700, 800, 900, 1000};
	int[] randomFrequency = new int[8];
	RandomNumberGenerator generator;
	
	public PseudoRandomCodeGenerator(){
		generator = new RandomNumberGenerator();
	}
	
	//Function to generate random frequency sequence
	public void frequencySelect(){
		int random;
		int[] randomCheck = {1, 1, 1, 1, 1, 1, 1, 1};
		//Arrays.fill(randomCheck, 1);
		int i = 0;
		while(i < 8) {
			random = Math.abs(generator.generateRandom());  //Generates a random index between 0 and 7.
			//Check if frequency is repeated. If yes, enter loop again. 
			if (randomCheck[random] != 0) {
				randomFrequency[i] = frequencyList[random];
				//bitPattern[i] = kbitArray[random];
				randomCheck[random] = 0;
			}else {
				continue;
			}
			i++;
		}
	}
	
	//frequencySelect() must be called before getters.
	
	public int[] getFrequencySequence() {
		return randomFrequency;
	}
	
	/*
	public int[] getBitPattern() {
		return bitPattern;
	}
	*/
}


