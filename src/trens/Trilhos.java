package trens;

import javax.swing.*;

public class Trilhos 
{
	JFrame frame;
	private static Trilhos instance;
	private Trilhos()
	{
		loadScreen();
	}
	public static Trilhos getInstance()
	{
		if(instance == null)
		{
			instance = new Trilhos();
		}
		return instance;
	}
	public void loadScreen()
	{
		frame = new Frame();
		
		
	}
}
