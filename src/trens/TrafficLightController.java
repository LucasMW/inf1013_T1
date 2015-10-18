package trens;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

// to do: move system control form TrailsPanel to here


public class TrafficLightController implements Observer
{
	boolean greenLightLeft=false;
	boolean redLightLeft=false;
	boolean greenLightRight=false;
	boolean redLightRight=false;
	private int trainsOnRight=0;
	private int trainsOnLeft=0;
	private Point positionLightBoxLeft;
	private Point positionLightBoxRight;
	
	private static TrafficLightController instance = null;
	
	private TrafficLightController(Point leftPosition, Point rightPosition)
	{
		this.positionLightBoxLeft = leftPosition;
		this.positionLightBoxRight = rightPosition;
		this.setLeftLight(true);
		this.setRightLight(true);
		
		
	}
	static public TrafficLightController getInstance(Point leftPosition, Point rightPosition)
	{
		
		if(instance == null)
		{
			instance = new TrafficLightController(leftPosition, rightPosition);
		}
		return instance;
	}
	public Point getLightBoxLeftPosition()
	{
		return this.positionLightBoxLeft;
	}
	public Point getLightBoxRightPosition()
	{
		return this.positionLightBoxRight;
	}
	public void setLeftLight(boolean open)
	{
		this.greenLightLeft = open;
		this.redLightLeft =!open;
	}
	public void setRightLight(boolean open)
	{
		this.greenLightRight = open;
		this.redLightRight =!open;
	}
	public Color getColorOfTrafficLightLeft()
	{
		return this.greenLightLeft ? Color.green: Color.red;
	}
	public Color getColorOfTrafficLightRight()
	{
		return this.greenLightRight ? Color.green: Color.red;
	}
	public boolean SharedTrailOpen()
	{
		return this.greenLightLeft | this.greenLightRight;
	}
	@Override
	public void update(Observable o, Object arg) 
	{
		Trem t = (Trem) o;
		if(t.position.x > 146  && t.position.x < 370) //in road
		{
			if(t.sentido == Way.right)
			{
				this.setLeftLight(false);
			}
			else
			{
				this.setRightLight(false);
			}
		}
//		if(this.trainsOnLeft == 0)
//		{
//			this.setRightLight(true);
//		}
//		if(this.trainsOnRight == 0)
//		{
//			this.setLeftLight(true);
//		}
		
		
	}
	//sensor section
	public void trainEnteredThroughRight()
	{
		System.out.println("Entered Right");
		this.decrementTrainsOnRight();
		this.setLeftLight(false);
		
	}
	public void trainEnteredThoughLeft()
	{
		System.out.println("Entered Left");
		this.decrementTrainsOnLeft();
		this.setRightLight(false);
	}
	public void trainExitedThoughLeft()
	{
		System.out.println("Exited Left");
		
	}
	public void trainExitedThoughRight()
	{
		System.out.println("Entered Right");
		
	}
	public void incrementTrainsOnRight()
	{
		
		this.trainsOnRight++;
	}
	public void incrementTrainsOnLeft()
	{
		this.trainsOnLeft++;
	}
	public void decrementTrainsOnRight()
	{
		this.trainsOnRight--;
	}
	public void decrementTrainsOnLeft()
	{
		this.trainsOnLeft--;
	}

}
