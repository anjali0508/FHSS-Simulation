package graphs;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class FHSSGraph extends JPanel {
	
	int[] frequencySequence;
	
	public void setFrequencySequence(int[] frequencySequence) {
		this.frequencySequence = new int[frequencySequence.length];
		this.frequencySequence = frequencySequence.clone();
	}
	  
	  public void drawgraph(Graphics g, Graphics2D g2) {
		  int x, y;
		  
		  // Drawing the axes.
		  g.setColor(Color.BLACK);
		  g2.setStroke(new BasicStroke(2));
		  Line2D yaxis = new Line2D.Float(50,260, 50, 610);
		  Line2D xaxis = new Line2D.Float(50, 630, 400, 630);
		  g2.draw(xaxis);
		  g2.draw(yaxis);
		  
		  //Drawing the constriction.
		  Line2D crooked1 = new Line2D.Float(50,610,55,613);
		  Line2D crooked2 = new Line2D.Float(55,613,45,616);
		  Line2D crooked3 = new Line2D.Float(45,616,55,620);
		  Line2D crooked4 = new Line2D.Float(55,620,45,623);
		  Line2D crooked5 = new Line2D.Float(45,623,55,626);
		  Line2D crooked6 = new Line2D.Float(55,626,50,630);
		  g2.draw(crooked1);
		  g2.draw(crooked2);
		  g2.draw(crooked3);
		  g2.draw(crooked4);
		  g2.draw(crooked5);
		  g2.draw(crooked6);
		  
		  x = 70;
		  g.setColor(Color.BLACK);
		  for(int i = 1; i <= 8; i++) {
			  String hopperiod = Integer.toString(i);
			  g.drawString(hopperiod, x, 645);
			  x = x + 40;
		  } //to print the hop periods next to the axes
		 
		  for(int i = 2; i <= 10; i++) {
			  y = ( 330 + ( ( 9 - i ) * 40 ) );
			  String carrierfreq = Integer.toString(i*100);
			  g.drawString(carrierfreq, 22, y);
		  } //to print the carrier frequencies next to the axes
		  
		  g.drawString("Hop periods", 405, 638);
		  g.drawString("Carrier Frequencies", 20, 255);
		  
		  GradientPaint redtoblue = new GradientPaint(0,0,Color.RED,100, 700,Color.BLUE);
		  g2.setPaint(redtoblue);
		  
		  //drawing the graph
		  x=50;
		  for(int i=0;i<8;i++) {
			  y=(690-((frequencySequence[i]/2)+20))+(frequencySequence[i]/10)	;	  
			  Rectangle2D rect = new Rectangle2D.Double(x,y,40,40);
			  g2.fill(rect);
			  g2.draw(rect); 
			  x=x+40;
		  }
	  }
	  public void paint(Graphics g) {  
		  this.setBackground(Color.BLACK);
		    Graphics2D g2 = (Graphics2D) g;
		    FHSSGraph graph=new FHSSGraph();
		    graph.setBackground(Color.BLACK);
		    drawgraph(g,g2);
		    
	  }    

	 /*
	  public static void main(String[] args) {
		    FHSSGraph graph = new FHSSGraph();
		    graph.setSize(500, 500);
		    
		    
		    JFrame frame = new JFrame("bleh");
		    //frame.setLayout(new GridLayout(2, 1, 10, 10));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.add(graph);
		    
	        frame.pack();
	        //frame.setLocation(150, 150);
	        frame.setVisible(true);
		    		    frame.setVisible(true);
		    frame.setSize(1000, 1000);
		   
		   
	  }
	  
	  */
}	  
	  
