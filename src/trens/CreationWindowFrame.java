package trens;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreationWindowFrame extends JFrame 
{
	public JPanel buttonsPanel;
	
	public CreationWindowFrame()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("T1 - TrainCreationWindow");
		this.setSize(500,400);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(true);
		
		this.setLocation(500, 10);
		this.addPanel();
		this.setVisible(true);
	}
	private void addPanel()
	{
		this.buttonsPanel = new ButtonsPanel();
		this.getContentPane().add(this.buttonsPanel);
		//this.add(this.buttonsPanel);
		//this.repaint();
	}

}
