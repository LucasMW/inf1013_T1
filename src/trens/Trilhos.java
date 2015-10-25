package trens;

import javax.swing.*;

public class Trilhos 
{
	Frame frame;
	CreationWindowFrame creationFrame;
	private static Trilhos instance;
	private Trilhos()
	{
		loadScreen();
		loadCreationWindow();
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
	public void loadCreationWindow()
	{
		this.creationFrame = new CreationWindowFrame();
	}
	public TrailsPanel getTrailsPanel()
	{
		return this.frame.panel;
	}
}
