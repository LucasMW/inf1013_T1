package trens;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class TrailsPanel extends JPanel implements Observer
{
	CardLayout layout;
	Image backgroundImage; 
	Dimension size;
	boolean activated = false;
	
	private TrafficLightController controller;

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
		
		this.controller = TrafficLightController.getInstance(new Point(109, 241),new Point(397, 146));
		this.addMouseListener(new TrailsPanelMouseListener());
		this.controller.myReceiveObserver(this);
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
		for (Train train : this.controller.trains)
		{
			
			drawTrain(g,train);
		}
		drawTrafficLights(g, this.controller);
		
	}
	void drawTrain(Graphics g, Train t)
	{
		int radius = 10;
		g.setColor(t.Cor());
		g.fillOval(t.position.x-radius, t.position.y-radius, 2*radius, 2*radius);
		
	}
	void drawTrafficLights(Graphics g, TrafficLightController controller)
	{
		int width=30;
		int height=40;
		int radius = 10;
		Point p = controller.getLightBoxLeftPosition();
		g.setColor(Color.black);
		g.fillRect(p.x - width/2 , p.y - height/2 , width, height);
		g.setColor(controller.getColorOfTrafficLightLeft());
		g.fillOval(p.x-radius, p.y-radius, 2*radius, 2*radius);
		
	    p = controller.getLightBoxRightPosition();
		g.setColor(Color.black);
		g.fillRect(p.x -width/2, p.y - height/2, width, height);
		g.setColor(controller.getColorOfTrafficLightRight());
		g.fillOval(p.x-radius, p.y-radius, 2*radius, 2*radius);
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{
			this.repaint();	
	}
	
	private class TrailsPanelMouseListener implements MouseListener 
	{

		@Override
		public void mouseClicked(MouseEvent me) 
		{
			System.out.printf("%d, %d\n",me.getX(),me.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			// TODO Auto-generated method stub
			
		}
	
	}
	

}
