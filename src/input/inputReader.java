package input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class inputReader extends JComponent{ 
	JTextField binaryData;
	JTextField period;
	JLabel binaryDataLabel;
	JLabel periodLabel;
	JButton send;
	int[] inputData;
	int inputPeriod;
	boolean dataSet = false;
	
	public inputReader() {
		//Initializing text boxes, label and button.
	    binaryDataLabel = new JLabel("Enter binary data : ");
	    periodLabel = new JLabel("Enter period of each frequency : ");
	    //binaryDataLabel.setForeground(Color.WHITE);
	    //periodLabel.setForeground(Color.WHITE);
	    binaryData = new JTextField(20);
	    period = new JTextField(20);
	    send = new JButton("Send");
	}
	
	public static boolean isBackspace(char ch) {
	      return (ch == 8);
	 }
	
	 public void getUserInput(){
		 //Adding event listener to validate binary data.
	     binaryData.addKeyListener(new KeyAdapter(){
	    	 public void keyPressed(KeyEvent keyEvent){
	             char character = keyEvent.getKeyChar();
	             if(!((character =='0' || character == '1') || isBackspace(character))){
	            	 JOptionPane.showMessageDialog(null, "Input must be binary.");
	                 binaryData.setText("");
	              }
	          }
	       });
	      
	      //Adding event listener to validate period.
	      period.addKeyListener(new KeyAdapter(){
	          public void keyPressed(KeyEvent keyEvent){
	              char character = keyEvent.getKeyChar();
	              if(!(Character.isDigit(character) || isBackspace(character))){
	            	  JOptionPane.showMessageDialog(null, "Input must be a number.");
	                  period.setText("");
	              }
	          }
	       });
	        
	     //Adding action listener for send button.
	      send.addActionListener(new ActionListener(){
	    	  public void actionPerformed(ActionEvent e) {
	    		  if (e.getSource() == send) {
	    			  //Store code in the binaryData array.
	    		      String binaryString = binaryData.getText();	    		  	  
	    		  	  int length = binaryString.length();
	    		  	  //Check if input is empty.
	    		  	  if(length == 0){
		            	  JOptionPane.showMessageDialog(null, "Please enter the binary code.");
		                  binaryData.setText("");
		                  return;
		              }
	    		  	  inputData = new int[length];
	    		  	  for(int i=0; i<length; i++){
	    		  		 inputData[i] = Character.getNumericValue(binaryString.charAt(i)); 
	    		  	  }
	    		  	  
	    		  	  //Store period.
	    		  	  String hopPeriod = period.getText();
	    		  	  if(hopPeriod.length() == 0){
		            	  JOptionPane.showMessageDialog(null, "Please enter the period.");
		                  period.setText("");
		                  return;
		              }
	    		  	  inputPeriod = Integer.parseInt(hopPeriod);
	    		  	  
	    		  	  dataSet = true;
	    		  	  
	    		  }
	    	  }
	      });
	 }
	     
	 public int[] getInputData() {
		return inputData;
	 }
	 
	 public int getHopPeriod() {
		return inputPeriod;
	 }
	 
	 public boolean isDataSet() {
		 return dataSet;
	 }
	 
	 public JPanel getInputPanel() {
		 JPanel inputPanel = new JPanel();
		 inputPanel.add(binaryDataLabel);
		 inputPanel.add(binaryData);
		 inputPanel.add(periodLabel);
		 inputPanel.add(period);
		 inputPanel.add(send);
		 return inputPanel;
	 }
  
}	

