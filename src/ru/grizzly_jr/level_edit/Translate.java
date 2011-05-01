package ru.grizzly_jr.level_edit;

public class Translate {
	private static final double SIZE = 100.0;
	public static int metrsToPixel(double m)
	{
		return (int) Math.round(m*SIZE);
	}
	
	public static double pixelToMetrs(int p)
	{
		return ((double)p)/SIZE;
	}
}
