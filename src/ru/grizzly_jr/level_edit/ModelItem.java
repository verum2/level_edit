package ru.grizzly_jr.level_edit;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ModelItem {
	public String name;
	private Image image = null;
	
	public ModelItem(String name)
	{
		this.name = name;
		
		image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
	}
	
	public void load(String path)
	{
		
	}

	public Image getImage() {
		return image;
	}
}
