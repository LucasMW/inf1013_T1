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
				position = new Point(0,230);
				break;
			case left:
				position = new Point(499, 171);
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
		switch(this.sentido)
		{
		case left:
			if(this.position.x > 436)
				this.position.x -= velocity;
			else if(this.position.x > 371)
			{
				System.out.println("case");
				Vector2D vector = new Vector2D(new Point(436,171),new Point(370,190));
				vector.setModule(this.velocity);
				this.position.x += vector.x;
				this.position.y += vector.y;	
			}
			else if(this.position.x > 147)
			{
				this.position.x -= velocity;
			}
			else if(this.position.x > 66)
			{
				System.out.println("case left 2");
				Vector2D vector = new Vector2D(new Point(147,190),new Point(66,169));
				vector.setModule(this.velocity);
				this.position.x += vector.x;
				this.position.y += vector.y;	
			}
			else
			{
				this.position.x -= velocity;
			}
			break;
		case right:
			
			if(this.position.x > 370 && this.position.x < 439)
			{
				Vector2D vector = new Vector2D(new Point(370,191),new Point(439,230));
				vector.setModule(this.velocity);
				this.position.x += vector.x;
				this.position.y += vector.y;
			}
			else if(this.position.x > 62 && this.position.x < 146 )
			{
				System.out.println("case");
				Vector2D vector = new Vector2D(new Point(62,230),new Point(146,191));
				vector.setModule(this.velocity);
				this.position.x += vector.x;
				this.position.y += vector.y;	
			}
			else
			{
				this.position.x += velocity;
			}
			break;
		default:
			this.position.x += velocity * ((sentido == Way.right)? 1 : -1);
			this.position.y += velocity;
			break;
				
		}
		
		
		
		//System.out.println(this.countObservers());
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
		    Thread.sleep(50);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		}
		
		// TODO Auto-generated method stub
		
	}
}
