package trens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel
{
	//JButton launchButton;
	JComboBox velocityComboBox;
	JButton	launchRightTrain;
	JButton launchLeftTrain;
	Dimension size;
	TrafficLightController ctrl;
	public ButtonsPanel()
	{
		this.setLayout(null);
		new JPanel();
		this.setBackground(Color.cyan);
		System.out.println("buttons panel");
		size = new Dimension((int) (500),
				(int) (400));
		this.setPreferredSize(size);
		this.setSize(size);
		
		
		this.ctrl = TrafficLightController.getInstance(null, null);
		
		ActionListener actLisLB = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("left pressed");
				ctrl.informTrain(Way.left,  ((Float) velocityComboBox.getSelectedItem()).floatValue());
			}
		};
		ActionListener actLisRB = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("right pressed");
				ctrl.informTrain(Way.right, ((Float) velocityComboBox.getSelectedItem()).floatValue());
			}
		};
		
		this.launchLeftTrain = new JButton("LaunchLeft");
		this.launchLeftTrain.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.launchLeftTrain.addActionListener(actLisLB);
		this.launchLeftTrain.setVisible(true);
		this.launchLeftTrain.setBounds(0, 0, 150, 50);
		this.add(launchLeftTrain);
		this.launchLeftTrain.setVisible(true);
		this.launchRightTrain = new JButton("LaunchRight");
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.launchRightTrain.setBounds(0, 60, 150, 50);
		this.launchRightTrain.addActionListener(actLisRB);
		this.add(launchRightTrain);
		this.velocityComboBox = new JComboBox<Float>();
		this.velocityComboBox.addItem(new Float(30));
		this.velocityComboBox.addItem(new Float(40));
		this.velocityComboBox.addItem(new Float(60));
		this.velocityComboBox.setSelectedIndex(0);
		this.velocityComboBox.setBounds(200, 0, 100, 50);
		this.add(this.velocityComboBox);
	
		this.setVisible(true);
		this.setOpaque(true);
	}
	
}
