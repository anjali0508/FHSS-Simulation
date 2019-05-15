package connections;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	Socket socket;
	InputStream inputStream;
	
	public void connect() throws IOException {
		socket = new Socket("192.168.43.114", 9000);
		System.out.println("Connected to server.");
		//Obtain input stream associated with the socket.
		inputStream = socket.getInputStream();
	}
	
	public void receiveFile(String fileName) throws FileNotFoundException, IOException{
		
		//Create an output stream for the file.
		FileOutputStream fileOutput = new FileOutputStream(fileName);
		//Create buffered output stream associated with the file output stream.
		//BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);
		//Create a byte array
		byte[] byteArray = new byte[1024];
		int bytesRead = inputStream.read(byteArray, 0, byteArray.length);
		//Write to byteArray i.e., file.
		fileOutput.write(byteArray, 0, bytesRead);
		//Close the resources	
		fileOutput.close();
	}
	
	public void close() throws IOException{
		socket.close();
	}
	
	/*
	public static void main(String args[]) {
		Client client = new Client();
		try {
			client.connect();
			client.receiveFile("frequencyInformation.txt");
			System.out.println("Frequency Information Received");
			client.receiveFile("modulatedStream.txt");
			System.out.println("Modulated Stream Received");
			client.close();
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	*/

}
