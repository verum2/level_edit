package ru.grizzly_jr.level_edit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MasterItem {
	private static final String path = "data/";
	public String name;
	
	public PhysicItem physic = new PhysicItem();
	
	private BufferedImage image = null;
	private BufferedImage imageWithShapes = null;
	private double width = 0;
	private double height = 0;
	private double width_shape = 0;
	private double height_shape = 0;
	
	public MasterItem(String name)
	{
		loadImage(name);
	}
	
	public void loadImage(String name)
	{
		try {
			image = ImageIO.read(new File(path+name+".png"));
			width = Translate.pixelToMetrs( image.getWidth());
			height = Translate.pixelToMetrs( image.getHeight());
			width_shape = width;
			height_shape = height;
			this.name = name;
			redrawImageWithShapes();
		} catch (IOException e) {
		}
	
	}

	public void redrawImageWithShapes()
	{
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		imageWithShapes = new BufferedImage(cm, raster,
				isAlphaPremultiplied, null);
		
		for (ShapePolygon shapePolygon : physic.getPolygonList()) {
			shapePolygon.setRadius(0);
			shapePolygon.draw(imageWithShapes.createGraphics(),true);
		}
		for (ShapeCircle shapeCircle : physic.getCircleList()) {
			shapeCircle.draw(imageWithShapes.createGraphics());
		}
		
		width_shape = Translate.pixelToMetrs( imageWithShapes.getWidth());
		height_shape = Translate.pixelToMetrs( imageWithShapes.getHeight());
			
	}
	
	public BufferedImage getImage(boolean isShaped) {
		if(isShaped)
		{
			return imageWithShapes;
		}
		return image;
	}

	public Image getImage() {
		return image;
	}
	
	public double getWidth(boolean isShaped) {
		if( isShaped){
			return width_shape;
		}
		return width;
	}

	public double getHeight(boolean isShaped) {
		if( isShaped){
			return height_shape;
		}
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
