package ru.grizzly_jr.level_edit;

import java.awt.Point;

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
	
	public static int metrsToPixelWithZoom(double m, double zoom)
	{
		return (int) Math.round(m*SIZE*zoom);
	}
	
	public static double pixelToMetrsWithZoom(int p, double zoom)
	{
		return ((double)p)/zoom/SIZE;
	}
	
	public static Point pointMetrsToPixelWithZoom(PointD m, double zoom)
	{
		return new Point(metrsToPixelWithZoom(m.getX(), zoom), metrsToPixelWithZoom(m.getY(), zoom));
	}
	
	public static PointD pointPixelToMetrsWithZoom(Point p, double zoom)
	{
		return new PointD(pixelToMetrsWithZoom((int)p.getX(), zoom), pixelToMetrsWithZoom((int)p.getY(), zoom));
	}
	
}
