package trens;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

enum Way
{
	right,
	left
};
public class Trem implements Runnable
{
	Way sentido;
	float velocity;
	Point position;
	boolean moving;
	
	public Trem(Way sentido , float velocity)
	{
			this.sentido = sentido;
			this.velocity = velocity;
			this.moving = true;
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
			cor =  Color.black;
			break;
		case left:
			cor = Color.red;
			break;
		}
		return cor;
	}
	public void UpdatePosition()
	{
		Point newPosition = new Point(this.position.x,this.position.y);
		TrafficLightController ctrl = TrafficLightController.getInstance();
		switch(this.sentido)
		{
		case left:
			if(ctrl.greenLightRight == true)
			{
				moving = true;
			}
			
			
			if(this.position.x > 436)
				newPosition.x -= velocity;
			else if(this.position.x > 371)
			{
				//System.out.println("case");
				Vector2D vector = new Vector2D(new Point(436,171),new Point(370,190));
				vector.setModule(this.velocity);
				newPosition.x += vector.x;
				newPosition.y += vector.y;
				if(ctrl.redLightRight == true)
				{
					System.out.println("LEFT can`t move");
				this.moving = false;
				break;
				}
				if(newPosition.x < 371)
				{
					ctrl.trainEnteredThroughRight();
				}
				
			}
			else if(this.position.x > 147)
			{
				newPosition.x -= velocity;
				if(newPosition.x < 147)
				{
					ctrl.trainExitedThroughLeft();
				}
			}
			else if(this.position.x > 66)
			{
				 
				//System.out.println("case left 2");
				Vector2D vector = new Vector2D(new Point(147,190),new Point(66,169));
				vector.setModule(this.velocity);
				newPosition.x += vector.x;
				newPosition.y += vector.y;	
			}
			else
			{
				newPosition.x -= velocity;
			}
			break;
		case right:
			if(ctrl.greenLightLeft == true)
			{
				moving = true;
				//System.out.println("move again");
			}
			
			
			if(this.position.x > 370 && this.position.x < 439) //segment 
			{
				
				Vector2D vector = new Vector2D(new Point(370,191),new Point(439,230));
				vector.setModule(this.velocity);
				newPosition.x += vector.x;
				newPosition.y += vector.y;
				
				if(newPosition.x > 439)
				{
					ctrl.trainExitedThroughRight();
				}
			}
			else if(this.position.x > 62 && this.position.x < 146 ) //segment
			{
				//System.out.println("case");
				Vector2D vector = new Vector2D(new Point(62,230),new Point(146,191));
				vector.setModule(this.velocity);
				
					if(ctrl.redLightLeft == true)
					{
						System.out.println("RIGHT can`t move");
					this.moving = false;
					break;
					}
					

				
				newPosition.x += vector.x;
				newPosition.y += vector.y;
				if(newPosition.x > 146)
				{
					TrafficLightController.getInstance(null, null).trainEnteredThroughLeft();
				}
				
			}
			else
			{
				
				newPosition.x += velocity;
			}
			break;
		default:
			this.position.x += velocity * ((sentido == Way.right)? 1 : -1);
			this.position.y += velocity;
			break;
				
		}
		
		if (!moving)
		{
			return;
		} else if(ctrl.collisionRiskOnMovement(newPosition))
		{
			System.out.println(newPosition);
			System.out.println("stop");
			return;
		}
		// update position
		this.position.x = newPosition.x;
		this.position.y = newPosition.y;
	}

	@Override
	public void run() 
	{
		while(true)
		{
		this.UpdatePosition();
		//System.out.printf("%d,%d\n",position.x, position.y);
		
		try {
		    Thread.sleep(50);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    //Thread.currentThread().interrupt();
		}
		}
		
		// TODO Auto-generated method stub
		
	}
}
