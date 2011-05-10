package ru.grizzly_jr.level_edit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class ShapeCircle {
	
	private PointD center;
	private double radius;
	private Color lineColor;
	
	public ShapeCircle(PointD center, double radius,Color lineColor) {
		super();
		this.lineColor=lineColor;
		this.center = center;
		this.radius = radius;
	}
	
	public PointD getCenter() {
		return center;
	}
	
	public void setCenter(PointD center) {
		this.center = center;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public void draw(Graphics2D g) {
		/*
		BasicStroke stroke = new BasicStroke((float) lineOptions.getThick(),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
				lineOptions.getDashType(), 0);
		g.setStroke(stroke);
		*/
		g.setColor(lineColor);
		g.drawOval( Translate.metrsToPixel(center.getX()) - Translate.metrsToPixel(radius), 
				    Translate.metrsToPixel(center.getY()) - Translate.metrsToPixel(radius),
				    Translate.metrsToPixel( radius * 2),
				    Translate.metrsToPixel( radius * 2));
	}	
	public void drawWithZoom(Graphics2D g,double zoom,Point move) {
		/*
		BasicStroke stroke = new BasicStroke((float) lineOptions.getThick(),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
				lineOptions.getDashType(), 0);
		g.setStroke(stroke);
		*/
		g.setColor(lineColor);
		Point ccentr = Translate.pointMetrsToPixelWithZoom(center,zoom);
		g.drawOval( ccentr.x - Translate.metrsToPixelWithZoom(radius,zoom)+move.x, 
					ccentr.y - Translate.metrsToPixelWithZoom(radius,zoom)+move.y,
				    Translate.metrsToPixelWithZoom( radius * 2, zoom),
				    Translate.metrsToPixelWithZoom( radius * 2, zoom));
	}	
}
