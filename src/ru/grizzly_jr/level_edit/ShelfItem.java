package ru.grizzly_jr.level_edit;

import java.awt.Color;
import java.awt.Graphics;
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
		point1.x = x;
		point1.y = y + getHeight()/2;
		
		point2.x = x + getWidth();
		point2.y = y + getHeight()/2;
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
		int x1 = Translate.metrsToPixel(point1.x) + xS;
		int y1 = Translate.metrsToPixel(point1.y) + yS;
		int x2 = Translate.metrsToPixel(point2.x) + xS;
		int y2 = Translate.metrsToPixel(point2.y) + yS;
		
		int radius = global_radius;
		g.setColor(Color.black);
		g.fillOval(x1-radius, y1-radius, radius*2, radius*2);
		g.fillOval(x2-radius, y2-radius, radius*2, radius*2);
		
		xS += Translate.metrsToPixel(this.x);
		yS += Translate.metrsToPixel(this.y);
		int x3 = Translate.metrsToPixel(master.shelf.point1.x) + xS;
		int y3 = Translate.metrsToPixel(master.shelf.point1.y) + yS;
		int x4 = Translate.metrsToPixel(master.shelf.point2.x) + xS;
		int y4 = Translate.metrsToPixel(master.shelf.point2.y) + yS;
		
		g.drawLine(x1, y1, x3, y3);
		g.drawLine(x2, y2, x4, y4);
	}
	
	public boolean collisionPoint(int px,int py)
	{
		double pxd = Translate.pixelToMetrs(px);
		double pyd = Translate.pixelToMetrs(py);
		
		double radius = Translate.pixelToMetrs(global_radius);
		
		if( point1.getX() - radius < pxd && pxd < point1.getX() + radius &&
			point1.getY() - radius < pyd && pyd < point1.getY() + radius){
			last_collision = 2;
			return true;
		}
		
		if( point2.getX() - radius < pxd && pxd < point2.getX() + radius &&
			point2.getY() - radius < pyd && pyd < point2.getY() + radius){
			last_collision = 3;
			return true;
		}
		
		if( x < pxd && pxd < x+master.getWidth() &&
			y < pyd && pyd < y+master.getHeight()){
			last_collision = 1;
			return true;
		}
		
		return false;
	}

}
