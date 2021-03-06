package ru.grizzly_jr.level_edit;

import items_component.MasterItem;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ModelItem {
	public double x = 0;
	public double y = 0;

	protected MasterItem master = null;
	
	public ModelItem clone()
	{
		ModelItem clon = new ModelItem(master,x,y);
		return clon;
	}
	
	public ModelItem(MasterItem master)
	{
		this.master = master;
	}
	
	public ModelItem(MasterItem master,double x,double y)
	{
		this.master = master;
		this.x = x;
		this.y = y;
	}
	
	public ModelItem(String name,List<MasterItem> masters)
	{
		for( MasterItem master: masters){
			if( name.equals(master.name)){
				this.master = master;
				return;
			}
		}
	}
	
	public ModelItem(String name,List<MasterItem> masters,double x,double y)
	{
		this.x = x;
		this.y = y;
		for( MasterItem master: masters){
			if( name.equals(master.name)){
				this.master = master;
				return;
			}
		}
	}
	
	public void update()
	{
		
	}

	public Image getImage() {
		return master.getImage();
	}
	
	public Image getImage(boolean isShape) {
		return master.getImage(isShape);
	}

	public double getWidth() {
		return master.getWidth();
	}

	public double getHeight() {
		return master.getHeight();
	}
	
	public MasterItem getMaster()
	{
		return master;
	}
	
	public void move(int mx,int my)
	{
		x += Translate.pixelToMetrs(mx);
		y += Translate.pixelToMetrs(my);
	}
	
	public boolean collisionPoint(int px,int py)
	{
		PointD p = Translate.getPointD(px, py);
		p.x += getWidth()/2.0;
		p.y += getHeight()/2.0;
		if( x > p.x || p.x > x + getWidth() ||
			y > p.y || p.y > y + getHeight())
			return false;
		return true;
	}
	
	public void draw(Graphics g,int xS, int yS,boolean isShaped)
	{
		int w = Translate.metrsToPixel(getWidth());
		int h = Translate.metrsToPixel(getHeight());
		Point p = Translate.getPoint(x, y, getWidth(), getHeight());
		g.drawImage(getImage(isShaped),p.x+xS,p.y+yS,w,h,null);
	}
	
	public static interface isDelete
	{
		public void delete(ModelItem model);
	};
	private static List<isDelete> listenerDelete = new ArrayList<isDelete>();
	
	public static void addListenerDelete(isDelete del){
		listenerDelete.add(del);
	}
	
	public static void removeListenerDelete(isDelete del){
		listenerDelete.remove(del);
	}
	
	public static void executeDelete( ModelItem model){
		for( isDelete listener: listenerDelete){
			listener.delete(model);
		}
	}
}
