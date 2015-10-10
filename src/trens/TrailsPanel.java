package trens;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TrailsPanel extends JPanel implements Observer
{
	CardLayout layout;
	Image backgroundImage; 
	Dimension size;
	private List<Trem> trains = new LinkedList<Trem>();
	private List<Thread> trainThread = new LinkedList<Thread>();
	public TrailsPanel()
	{
		
		this.setLayout(null);
		System.out.println("puts panel");
		size = new Dimension((int) (500),
				(int) (400));
		this.setPreferredSize(size);
		this.setSize(size);
		this.setBackgroundImage("Trem.jpg");
		this.setVisible(true);
		this.setOpaque(true);
		for(int i =0; i< 3 ;i++)
		{
			Trem t = new Trem(i%2==0?Way.right :Way.left,30);
			this.trains.add(t);
			Thread thread = new Thread(t);
			this.trainThread.add(thread);
			thread.start();
			t.myReceiveObserver(this);
			
			
		}
		
	}
	public void setBackgroundImage(String path) 
	{
		try 
		{
			System.out.println("set bg");
			this.backgroundImage = new ImageIcon(path).getImage();
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return;
		}
	}
//	@Override
//	protected void paintComponent(Graphics g) 
//	{
//		
//		
//	}
	public void paint(Graphics g)
	{
		
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		for (Trem train : this.trains)
		{
			System.out.println("djnsak");
			drawTrain(g,train);
		}
	}
	void drawTrain(Graphics g, Trem t)
	{
		int radius = 10;
		g.setColor(t.Cor());
		g.fillOval(t.position.x-radius, t.position.y-radius, 2*radius, 2*radius);
		
	}
	@Override
	public void update(Observable o, Object arg) 
	{
		
			System.out.println("updated");	
			this.repaint();
		
		
	}


}
