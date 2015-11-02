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
public class Train implements Runnable
{
	Way way;
	float velocity;
	Point position;
	boolean moving;
	boolean destroyed;
	public Train(Way way , float velocity)
	{
			this.way = way;
			this.velocity = velocity;
			this.moving = true;
			switch(this.way)
			{
			case right:
				position = new Point(0,230);
				break;
			case left:
				position = new Point(499, 171);
				break;
			}
			this.destroyed=false; //this will be used for thread interruption	
	}
	
	public Color Cor()
	{
		Color cor = null;
		switch(this.way)
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
		switch(this.way)
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
			}
			else if(this.position.x > 147)
			{
				newPosition.x -= velocity;
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
				
				
			}
			else
			{
				newPosition.x += velocity;
			}
			break;
		default:
			this.position.x += velocity * ((way == Way.right)? 1 : -1);
			this.position.y += velocity;
			break;
				
		}
		// checking
		if(this.position.x >510 || this.position.x <-10) //10 units off screen
		{
			ctrl.deleteTrain(this);	//removes from list
			this.destroyed = true; //cancels thread	
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
		this.checkPassedSensor(this.position, newPosition); //verifies sensors
		
		// update position
		this.position.x = newPosition.x;
		this.position.y = newPosition.y;
	}
	private void checkPassedSensor(Point op, Point np)
	{
		TrafficLightController ctrl = TrafficLightController.getInstance();
			if(op.x <= ctrl.getRightSensorPosition().x && np.x > ctrl.getRightSensorPosition().x )
			{
				ctrl.rightSensorChecked(this);
			} else if(op.x >= ctrl.getRightSensorPosition().x && np.x < ctrl.getRightSensorPosition().x)
			{
				ctrl.rightSensorChecked(this);
			} else if(op.x <= ctrl.getLeftSensorPosition().x && np.x > ctrl.getLeftSensorPosition().x )
			{
				ctrl.leftSensorChecked(this);
			} else if(op.x >= ctrl.getLeftSensorPosition().x && np.x < ctrl.getLeftSensorPosition().x)
			{
				ctrl.leftSensorChecked(this);
			}
	}

	@Override
	public void run() 
	{
		while(!destroyed) 
		{
			this.UpdatePosition();
			try 
			{
				Thread.sleep(50);                 //1000 milliseconds is one second.
			} 
			catch(InterruptedException ex) 
			{
		   
			}
	}
		
		// TODO Auto-generated method stub
		
	}
}
