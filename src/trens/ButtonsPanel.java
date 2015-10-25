package trens;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel
{
	//JButton launchButton;
	JComboBox velocityComboBox;
	JButton	launchRightTrain;
	JButton launchLeftTrain;
	Dimension size;
	public ButtonsPanel()
	{
		//this.setLayout(null);
		System.out.println("buttons panel");
		size = new Dimension((int) (500),
				(int) (400));
		this.setPreferredSize(size);
		this.setSize(size);
		this.setOpaque(true);
		this.launchLeftTrain = new JButton("LaunchLeft");
		this.add(launchLeftTrain);
		this.launchLeftTrain.setVisible(true);
		this.launchRightTrain = new JButton("LaunchRight");
		this.add(launchRightTrain);
		this.velocityComboBox = new JComboBox<Float>();
		this.add(this.velocityComboBox);
	}
}
