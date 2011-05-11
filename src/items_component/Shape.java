package items_component;

import java.awt.Graphics2D;
import java.awt.Point;

public interface Shape {
	public String toString();
	public void draw(Graphics2D g);
	public void drawWithZoom(Graphics2D g,double zoom,Point move);
	
	public void setFriction(double friction);
	public void setSpring(double spring);
	public void setDencity(double dencity);
	
	public double getFriction();
	public double getSpring();
	public double getDencity();
}
