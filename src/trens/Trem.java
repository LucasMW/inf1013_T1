package trens;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

enum Way
{
	right,
	left
};
public class Trem extends Observable implements Runnable
{
	Way sentido;
	float velocity;
	Point position;
	Observer obs;
	
	public Trem(Way sentido , float velocity)
	{
			this.sentido = sentido;
			this.velocity = velocity;
			switch(this.sentido)
			{
			case right:
				position = new Point(0,90);
				break;
			case left:
				position = new Point(500,180);
				break;
			}
	}
	
	public Color Cor()
	{
		Color cor = null;
		switch(this.sentido)
		{
		case right:
			cor =  Color.red;
			break;
		case left:
			cor = Color.black;
			break;
		}
		return cor;
	}
	public void UpdatePosition()
	{
		this.position.x += velocity * ((sentido == Way.right)? 1 : -1);
		this.position.y += velocity;
		
		System.out.println(this.countObservers());
		//notifyObservers();
		this.myNotifyObserver();
		
	}
	public void myReceiveObserver(Object x)
	{
		this.obs  = (Observer) x ;
	}
	public void myNotifyObserver()
	{
		this.obs.update(this, null);
	}
	@Override
	public void run() 
	{
		while(true)
		{
		this.UpdatePosition();
		System.out.printf("%d,%d\n",position.x, position.y);
		
		try {
		    Thread.sleep(900);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		}
		
		// TODO Auto-generated method stub
		
	}
}
