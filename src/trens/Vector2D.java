package trens;

import java.awt.Point;

public class Vector2D 
{
	int x;
	int y;
	
	public Vector2D(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	public Vector2D(Point start, Point end)
	{
		this.x = end.x - start.x;
		this.y = end.y - start.y;
		
	}
	public double Abs()
	{
		return Math.sqrt((double)(x*x + y *y));
	}
	public void setModule(double newAbs)
	{
		double vectorAbs = this.Abs();
		this.x =(int) (newAbs / vectorAbs * this.x);
		this.y =(int) (newAbs / vectorAbs * this.y);
	}
}
