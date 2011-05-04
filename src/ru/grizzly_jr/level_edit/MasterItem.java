package ru.grizzly_jr.level_edit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MasterItem {
	private static final String path = "data/";
	public String name;
	public PhysicItem physic = new PhysicItem();
	
	private BufferedImage image = null;
	private double width = 0;
	private double height = 0;
	
	public MasterItem(String name)
	{
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
}
