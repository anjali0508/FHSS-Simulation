package connections;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class Server {
	
	ServerSocket serverSocket;
	Socket socket;
	OutputStream outputStream;
	
	public void connect() throws IOException{
		serverSocket = new ServerSocket(9000);
		System.out.println("Server started and waiting for client");
		socket = serverSocket.accept();
		System.out.println("Connected to a client" + socket.getInetAddress());
		//Create an output stream for the socket
    	outputStream = socket.getOutputStream();
		
	}
	
	public void sendFile(String fileName) throws FileNotFoundException, IOException {
	    File fileStream = new File(fileName);
	    //Create a file input stream.
    	FileInputStream fileInput = new FileInputStream(fileStream);
    	//Create buffered input stream for the file input stream.
    	//BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
    	//Create a byte array to read from the buffered input stream.
    	byte[] byteArray = new byte[(int) fileStream.length()];
    	//Read from buffered input stream into the byte array.
    	fileInput.read(byteArray, 0, byteArray.length);
    	//Write byte array to the output stream.
    	outputStream.write(byteArray, 0, byteArray.length);
    	outputStream.flush();
    	fileInput.close();
	}
	
	public void close() throws IOException{
		outputStream.close();
		socket.close();
		serverSocket.close();
	}
	
	/*
	public static void main(String args[]) {
		Server server = new Server();
		try {
			server.connect();
			server.sendFile("FrequencyInformation.txt");
			System.out.println("Frequency information sent");
			server.sendFile("ModulatedStream.txt");
			System.out.println("Modulated Stream sent");
			server.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}
	*/

}


