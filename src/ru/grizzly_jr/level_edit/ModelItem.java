package ru.grizzly_jr.level_edit;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ModelItem {
	public String name;
	private Image image = null;
	private int width = 0;
	private int height = 0;
	
	public ModelItem(String name)
	{
		this.name = name;
		
		image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
		width = 200;
		height = 100;
	}
	
	public void load(String path)
	{
		
	}

	public Image getImage() {
		return image;
		return;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
