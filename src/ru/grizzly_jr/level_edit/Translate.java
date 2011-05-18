package ru.grizzly_jr.level_edit;

import java.awt.Point;

public class Translate {
	private static final double SIZE = 200.0;
	public static int height = 0;
	public static int width = 0;
	
	public static Point getPoint(double x,double y, double width, double height)
	{
		Point p = new Point();
		p.x =  metrsToPixel(x) - metrsToPixel(width/2.0);
		p.y = Translate.height + metrsToPixel(y) - metrsToPixel(height/2.0);
		
		return p;
	}
	
	public static PointD getPointD(int x, int y)
	{
		PointD p = new PointD(0,0);
		p.x = pixelToMetrs(x);
		p.y = pixelToMetrs(y - height);
		return p;
	}
	
	public static PointD getPointD(int x, int y,int w, int h)
	{
		PointD p = new PointD(0,0);
		p.x = pixelToMetrs(x + w/2);
		p.y = pixelToMetrs(y - height + h/2);
		return p;
	}
	
	
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
