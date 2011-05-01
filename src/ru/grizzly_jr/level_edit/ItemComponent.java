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
		
		this.setPreferredSize(new Dimension(item.getWidth(),item.getHeight()));
	}

	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		
		if( null == item.getImage())
			return;
		
		g.drawImage(item.getImage(), 0, 0, item.getWidth(),item.getHeight(), null);
	}

	
}
