package items_component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import ru.grizzly_jr.level_edit.PointD;
import ru.grizzly_jr.level_edit.Translate;

public class ShapeCircle implements Shape {
	
	private PointD center;
	private double radius;
	private Color lineColor;
	
	private double friction = 0.1;
	private double spring = 0.1;
	private double dencity = 1.1;
	
	public ShapeCircle(PointD center, double radius,Color lineColor) {
		super();
		this.lineColor = lineColor;
		this.center = center;
		this.radius = radius;
	}
	
	public Color getLineColor()
	{
		return lineColor;
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
	
	@Override
	public String toString()
	{
		return "("+center.getX()+","+center.getY()+") r: "+radius;
	}
	
	@Override
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
	
	public double getFriction() {
		return friction;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}

	public double getSpring() {
		return spring;
	}

	public void setSpring(double spring) {
		this.spring = spring;
	}

	public double getDencity() {
		return dencity;
	}

	public void setDencity(double dencity) {
		this.dencity = dencity;
	}
}
