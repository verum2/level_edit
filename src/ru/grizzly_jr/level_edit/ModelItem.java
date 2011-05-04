package ru.grizzly_jr.level_edit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ModelItem {
	private static final String path = "data/";
	public String name;
	public double x = 0;
	public double y = 0;
	public PhysicItem physic = new PhysicItem();
	
	private BufferedImage image = null;
	private double width = 0;
	private double height = 0;
	
	public ModelItem clone()
	{
		ModelItem clon = new ModelItem(name,x,y);
		clon.image = image;
		clon.width = width;
		clon.height = height;
		clon.physic = physic;
		return clon;
	}
	
	public ModelItem(String name)
	{
		loadImage(name);
	}
	
	public ModelItem(String name,double x,double y)
	{
		this.x = x;
		this.y = y;
		loadImage(name);
	}
	
	public void loadImage(String name)
	{
		try {
			//TODO: jpg png
			image = ImageIO.read(new File(path+name+".png"));
			width = Translate.pixelToMetrs( image.getWidth());
			height = Translate.pixelToMetrs( image.getHeight());
			this.name = name;
		} catch (IOException e) {
		}
	}

	public Image getImage() {
		return image;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
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
		if( x > pxd || pxd > x+width ||
			y > pyd || pyd > y+height)
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
