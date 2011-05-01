package ru.grizzly_jr.level_edit;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ItemComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private ModelItem item;
	
	
	public ItemComponent(ModelItem item)
	{
		this.item = item;
		
		this.setPreferredSize(new Dimension(100,200));
	}


	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		
		Image im = item.getImage();
		
		if( null == im)
			return;
		
		g.drawImage(im, 0, 0, im.getWidth(null), im.getHeight(null), null);
	}

	
}
