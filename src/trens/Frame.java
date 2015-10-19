package trens;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame 
{ 
	public TrailsPanel panel; 

	public Frame() 
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("T1 - Modelagem de software -  Lucas Menezes");
		this.setSize(500,400);
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(false);
		this.addPanels();
		this.setVisible(true);
		
		
	}

	private void addPanels() 
	{
		panel = new TrailsPanel();
		this.getContentPane().add(panel);
	
	}
}
	
