package ru.grizzly_jr.level_edit;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class ModelItem {
	public double x = 0;
	public double y = 0;

	private MasterItem master = null;
	
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
	
	public ModelItem(String name)
	{
		master = new MasterItem(name);
	}
	
	public ModelItem(String name,double x,double y)
	{
		this.x = x;
		this.y = y;
		master = new MasterItem(name);
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
		double pxd = Translate.pixelToMetrs(px);
		double pyd = Translate.pixelToMetrs(py);
		if( x > pxd || pxd > x+master.getWidth() ||
			y > pyd || pyd > y+master.getHeight())
			return false;
		return true;
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
