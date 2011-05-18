package items_component;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ru.grizzly_jr.level_edit.Translate;

public class MasterItem {
	public enum TypeItem
	{
		PHYSIC,
		SHELF
	}
	
	private static final String path = "data/";
	public String name;
	
	public PhysicItem physic = new PhysicItem();
	public ShelfPhysicItem shelf = new ShelfPhysicItem();
	
	private BufferedImage image = null;
	private BufferedImage imageWithShapes = null;
	private double width = 0;
	private double height = 0;
	private TypeItem type;
	
	public MasterItem(String name,TypeItem type)
	{
		this.type = type;
		loadImage(name);
	}
	
	public TypeItem getType()
	{
		return type;
	}
	
	public void loadImage(String name)
	{
		try {
			image = ImageIO.read(new File(path+name+".png"));
			width = Translate.pixelToMetrs( image.getWidth());
			height = Translate.pixelToMetrs( image.getHeight());
			imageWithShapes = image;
			this.name = name;
			redrawImageWithShapes();
		} catch (IOException e) {
		}
	
	}

	public void redrawImageWithShapes()
	{
		if( TypeItem.PHYSIC == type || TypeItem.SHELF == type){
			ColorModel cm = image.getColorModel();
			boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
			WritableRaster raster = image.copyData(null);
			imageWithShapes = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
			
			int x = -Translate.metrsToPixel(getWidth()/2.0);
			int y = -Translate.metrsToPixel(getHeight()/2.0);
			for (Shape shape : physic.getShapes()) {
				shape.draw(imageWithShapes.createGraphics(),new Point(x,y));
			}
			
			if( TypeItem.SHELF == type){
				drawShelf(imageWithShapes.createGraphics());
			}
		}
	}
	
	public void drawShelf(Graphics2D g)
	{
		g.setColor(Color.red);
		
		int x = -Translate.metrsToPixel(getWidth()/2.0);
		int y = -Translate.metrsToPixel(getHeight()/2.0);
		
		int radius = 3;
		int p1_x = Translate.metrsToPixel(shelf.point1.getX())-x;
		int p1_y = Translate.metrsToPixel(shelf.point1.getY())-y;
		int p2_x = Translate.metrsToPixel(shelf.point2.getX())-x;
		int p2_y = Translate.metrsToPixel(shelf.point2.getY())-y;
			
		g.fillOval(p1_x - radius, p1_y - radius, radius * 2,radius * 2);
		g.fillOval(p2_x - radius, p2_y - radius, radius * 2,radius * 2);
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

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
