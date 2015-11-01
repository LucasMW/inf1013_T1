package trens;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

// to do: move system control form TrailsPanel to here


public class TrafficLightController extends Observable 
{
	boolean greenLightLeft=false;
	boolean redLightLeft=false;
	boolean greenLightRight=false;
	boolean redLightRight=false;
	private int rightTrainsCount=0; //trains going right counted by the system
	private int leftTrainsCount=0; // trains going left counted by the system
	private Point positionLightBoxLeft;
	private Point positionLightBoxRight;
	private Point positionSensorRight;
	private Point positionSensorLeft;
	
	private static TrafficLightController instance = null;
	public List<Trem> trains = new CopyOnWriteArrayList<Trem>(); //thread safe
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
				myNotifyObservers();
				
			}
		};
		int fps = 20;
		this.timer.scheduleAtFixedRate(task, 0, (long) fps);
		this.positionSensorLeft = new Point(121,192);
		this.positionSensorRight = new Point(395,193);
	}
	static public TrafficLightController getInstance(Point leftPosition, Point rightPosition)
	{
		if(instance == null)
		{
			instance = new TrafficLightController(leftPosition, rightPosition);
		}
		return instance;
	}
	static public TrafficLightController getInstance()
	{
		if(instance == null)
		{
			instance = new TrafficLightController();
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
	public Point getRightSensorPosition()
	{
		return this.positionSensorRight;
	}
	public Point getLeftSensorPosition()
	{
		return this.positionSensorLeft;
	}
	public boolean SharedTrailOpen()
	{
		return this.greenLightLeft | this.greenLightRight;
	}
	
	//sensor section
	public void rightSensorChecked(Trem t) // a train caught on right sensor
	{	
		switch (t.sentido)
		{
		case left:
				this.leftTrainCountUp(); //train going left entered
				break;
		case right:
				this.rightTrainCountDown(); //train going right exited
				break;
		}
		this.trafficLightUpdate();
	}
	public void leftSensorChecked(Trem t) // a train caught on left sensor
	{
		switch (t.sentido)
		{
		case left:
				this.leftTrainCountDown(); //train going left exited
				break;
		case right:
				this.rightTrainCountUp(); //train going right entered
				break;
		}
		this.trafficLightUpdate();
	}
	public void rightTrainCountUp()
	{
		this.rightTrainsCount++;
	}
	public void leftTrainCountUp()
	{
		this.leftTrainsCount++;
	}
	public void rightTrainCountDown()
	{
		this.rightTrainsCount--;
	}
	public void leftTrainCountDown()
	{
		this.leftTrainsCount--;
	}
	private void trafficLightUpdate() 
	{
		if(this.rightTrainsCount == 0) //no Train going right on sharedTrail
		{
			this.setRightLight(true); // trains going right to left may pass
		}else
		{
			this.setRightLight(false); // they would collide
		}
		if(this.leftTrainsCount == 0) //no Train going left on sharedTrail
		{
			this.setLeftLight(true); // trains going left to right may pass
		}else
		{
			this.setLeftLight(false); // they would collide
		}
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
	public void deleteTrain(Trem t)
	{
		this.trains.remove(t);		
	}

}
