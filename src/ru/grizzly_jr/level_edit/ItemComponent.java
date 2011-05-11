package ru.grizzly_jr.level_edit;

import items_component.MasterItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ItemComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private ModelItem item;
	private MasterItem master;
	private boolean isSelect = false;
	
	public ItemComponent(ModelItem item)
	{
		this.item = item;
		this.master = item.getMaster();
		
		int width = Translate.metrsToPixel(master.getWidth());
		int height = Translate.metrsToPixel(master.getHeight());
		this.setPreferredSize(new Dimension(width,height+15));
		this.setSize(width,height+15);
	}
	
	public ItemComponent(MasterItem master)
	{
		this.master = master;
		int width = Translate.metrsToPixel(master.getWidth());
		int height = Translate.metrsToPixel(master.getHeight());
		this.setPreferredSize(new Dimension(width,height+15));
		this.setSize(width,height+15);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if( null == master.getImage())
			return;		
	
		if( isSelect){
			g.setColor(new Color(180,180,255));
		}else{
			g.setColor(new Color(255,255,255));
		}
		
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int width = Translate.metrsToPixel(master.getWidth());
		int height = Translate.metrsToPixel(master.getHeight());
		g.drawImage(master.getImage(), 0, 0, width,height, null);
		
		g.setColor(new Color(0,0,0));
		
		String name = master.name;
		g.drawChars(name.toCharArray(), 0, name.length(), 0, height+12);
		
		switch( master.getType())
		{
		case PHYSIC:
			g.drawChars("physic".toCharArray(), 0, 6, 100, height+12);
			break;
		case SHELF:
			g.drawChars("shelf".toCharArray(), 0, 5, 100, height+12);
			break;
		}
	}
	
	public void select(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
	
	public void delete()
	{
		ModelItem.executeDelete(item);
		setVisible(false);
	}
}
