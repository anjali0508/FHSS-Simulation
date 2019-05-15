package signals;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
 
//Implements NRZL scheme.
public class DigitalSignal extends JPanel implements Runnable{
	int[] code;
	int counter = 0;
	
	public void setCode(int[] code) {
		this.code = new int[code.length];
		this.code = code.clone();
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(1000);
				counter = (counter+1) % code.length;
                repaint();
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
   
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//Clears component before repainting.
        Graphics2D g2 = (Graphics2D)g;
        
        //Drawing time intervals.
        g2.setColor(Color.white);
        for(int i = 0; i <= code.length; i++) {
        	Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0); 
            g2.setStroke(dashed);
			g2.drawLine(i*50, 50, i*50, 200);
		}
		
		//Drawing the digital signal.
		g2.setColor(Color.orange); //The color is dark green.
		int x1 = 0, y1 = 175;			
		int previousState = -1;
		for(int i = counter, count = 0; count < code.length; count++, i = (i + 1) % code.length) {
			Stroke stroke = new BasicStroke(2f);
			g2.setStroke(stroke);
			
			if(code[i] == 0) {
				if (previousState == 1) {
					g2.drawLine(x1, y1, x1, y1 + 100);
					y1=y1+100;
					g2.drawLine(x1, y1, x1 + 50, y1);
					x1 = x1 + 50;
					previousState=-1;
				}
				else {
					g2.drawLine(x1, y1, x1 + 50, y1);
					x1 = x1 + 50;
				}	
			}
			else {
				if (previousState == -1) {
					g2.drawLine(x1, y1, x1, y1 - 100);
					y1 = y1 - 100;
					g2.drawLine(x1, y1, x1 + 50, y1);
					x1 = x1 + 50;
					previousState = 1;
				}
				else {
					g2.drawLine(x1, y1, x1 + 50, y1);
					x1 = x1 + 50;
			
				}
			}
		}
     }
    
    /*
    public static void main(String a[]) {
    	DigitalSignal obj = new DigitalSignal();

 	   //obj.animateSignal();
        obj.setSize(720, 440);

         JFrame f = new JFrame();
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         f.add(obj);
         obj.start();
         f.setSize(400,400);
         f.setLocation(200,200);
         f.setVisible(true);
    }
    
    */
  
}
