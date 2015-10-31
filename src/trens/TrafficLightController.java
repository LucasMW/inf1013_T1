package trens;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

// to do: move system control form TrailsPanel to here


public class TrafficLightController extends Observable 
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
	public List<Trem> trains = new LinkedList<Trem>();
	public List<Thread> trainThread = new LinkedList<Thread>();
	private List<Observer> observers = new LinkedList<Observer>();
	private Timer timer;
	
	private TrafficLightController(Point leftPosition, Point rightPosition)
	{
		this();
		this.positionLightBoxLeft = leftPosition;
		this.positionLightBoxRight = rightPosition;
		
	}
	
	private TrafficLightController()
	{
		this.setLeftLight(true);
		this.setRightLight(true);
		this.timer = new Timer(); 
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				System.out.println("as");
				myNotifyObservers();
				
			}
			
		};
		int fps = 20;
		this.timer.scheduleAtFixedRate(task, 0, (long) fps);
		
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
	
	//sensor section
	public void trainEnteredThroughRight()
	{
		System.out.println("Entered Right");
		this.decrementTrainsOnRight();
		this.setLeftLight(false);
		
	}
	public void trainEnteredThroughLeft()
	{
		System.out.println("Entered Left");
		this.decrementTrainsOnLeft();
		this.setRightLight(false);
	}
	public void trainExitedThroughLeft()
	{
		System.out.println("Exited Left");
		this.incrementTrainsOnLeft();
		if(this.trainsOnRight == 0)
		{
			System.out.println("left set true");
			this.setLeftLight(true);
		}
	}
	public void trainExitedThroughRight()
	{
		System.out.println("Exited Right");
		this.incrementTrainsOnRight();
		if(this.trainsOnLeft == 0)
		{
			System.out.println("right set true");
			this.setRightLight(true);
		}
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
	
	public boolean collisionRiskOnMovement(Point p)
	{
		for (Trem train: this.trains)
		{
			float hor = train.position.x - p.x; 
			float ver = train.position.y - p.y;
			float r =  (float) 2 ;
			if( hor*hor + ver*ver <= r*r) //circle equation
			{
				return true;
			}
		}
		return false;
	}
	public void informTrain(Way way, float velocity)
	{
		System.out.println(velocity);
		Trem t = new Trem(way,velocity/5);
		Thread thread = new Thread(t);
		this.trains.add(t);
		
		this.trainThread.add(thread);
		thread.start();
		if(t.sentido == Way.right)
			this.incrementTrainsOnLeft();
		else
			this.incrementTrainsOnRight();
		
		
	}
	public void myReceiveObserver(Object x)
	{
		this.observers.add((Observer) x );
	}
	public void myNotifyObservers()
	{
		for (Observer obs : this.observers)
		{
			obs.update(this, null);
		}
	}

}
