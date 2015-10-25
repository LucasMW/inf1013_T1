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
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		this.setResizable(true);
		this.setVisible(true);
		this.setLocation(500, 0);
		this.addPanel();
	}
	private void addPanel()
	{
		this.buttonsPanel = new ButtonsPanel();
		this.add(this.buttonsPanel);
	}

}
