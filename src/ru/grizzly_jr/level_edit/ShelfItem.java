package ru.grizzly_jr.level_edit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import items_component.MasterItem;

public class ShelfItem extends ModelItem{
	public PointD point1 = new PointD(0,0);
	public PointD point2 = new PointD(0,0);
	
	private int last_collision = 0;
	private int global_radius = 8;

	public ShelfItem(MasterItem master) {
		super(master);
	}
	
	public ShelfItem(MasterItem master,double x,double y)
	{
		super(master,x,y);
		update();
	}
	
	public ShelfItem(String name,List<MasterItem> masters)
	{
		super(name,masters);
	}
	
	public ShelfItem(String name,List<MasterItem> masters,double x,double y)
	{
		super(name,masters,x,y);
		update();
	}
	
	public void update()
	{
		point1.x = x - getWidth()/2;
		point1.y = y;
		
		point2.x = x + getWidth()/2;
		point2.y = y;
	}
	
	public void move(int mx,int my)
	{
		if( 1 == last_collision){
			x += Translate.pixelToMetrs(mx);
			y += Translate.pixelToMetrs(my);
		}
		if( 2 == last_collision){
			point1.x += Translate.pixelToMetrs(mx);
			point1.y += Translate.pixelToMetrs(my);
		}
		if( 3 == last_collision){
			point2.x += Translate.pixelToMetrs(mx);
			point2.y += Translate.pixelToMetrs(my);
		}
	}
	
	@Override
	public void draw(Graphics g,int xS, int yS,boolean isShaped)
	{
		super.draw(g, xS, yS, isShaped);
		
		Point p1 =  Translate.getPoint(point1.x,point1.y, 0,0);
		Point p2 =  Translate.getPoint(point2.x,point2.y, 0,0);
		p1.x += xS; p1.y += yS;
		p2.x += xS; p2.y += yS;
		
		int radius = global_radius;
		g.setColor(Color.black);
		g.fillOval(p1.x-radius, p1.y-radius, radius*2, radius*2);
		g.fillOval(p2.x-radius, p2.y-radius, radius*2, radius*2);
		
		Point p = Translate.getPoint(this.x, this.y, 0,0);
		int x3 = Translate.metrsToPixel(master.shelf.point1.x) + xS + p.x;
		int y3 = Translate.metrsToPixel(master.shelf.point1.y) + yS + p.y;
		int x4 = Translate.metrsToPixel(master.shelf.point2.x) + xS + p.x;
		int y4 = Translate.metrsToPixel(master.shelf.point2.y) + yS + p.y;
		
		g.drawLine(p1.x, p1.y, x3, y3);
		g.drawLine(p2.x	, p2.y, x4, y4);
	}
	
	public boolean collisionPoint(int px,int py)
	{
		PointD p = Translate.getPointD(px, py);
		
		double radius = Translate.pixelToMetrs(global_radius);
		
		if( point1.getX() - radius < p.x && p.x < point1.getX() + radius &&
			point1.getY() - radius < p.y && p.y < point1.getY() + radius){
			last_collision = 2;
			return true;
		}
		
		if( point2.getX() - radius < p.x && p.x < point2.getX() + radius &&
			point2.getY() - radius < p.y && p.y < point2.getY() + radius){
			last_collision = 3;
			return true;
		}
		
		p.x += getWidth()/2.0;
		p.y += getHeight()/2.0;
		
		if( x < p.x && p.x < x+getWidth() &&
			y < p.y && p.y < y+getHeight()){
			last_collision = 1;
			return true;
		}
		
		return false;
	}

}
