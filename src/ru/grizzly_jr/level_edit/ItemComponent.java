package ru.grizzly_jr.level_edit;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ItemComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private ModelItem item;
	
	public ItemComponent(ModelItem item)
	{
		this.item = item;
		
		int width = Translate.metrsToPixel(item.getWidth());
		int height = Translate.metrsToPixel(item.getHeight());
		this.setPreferredSize(new Dimension(width,height+15));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if( null == item.getImage())
			return;
		
		int width = Translate.metrsToPixel(item.getWidth());
		int height = Translate.metrsToPixel(item.getHeight());
		g.drawImage(item.getImage(), 0, 0, width,height, null);
		g.drawChars(item.name.toCharArray(), 0, item.name.length(), 0, height+12);
	}

	
}
