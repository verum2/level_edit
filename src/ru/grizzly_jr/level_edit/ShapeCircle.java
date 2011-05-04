package ru.grizzly_jr.level_edit;

import java.awt.Color;
import java.awt.Graphics2D;

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
}
