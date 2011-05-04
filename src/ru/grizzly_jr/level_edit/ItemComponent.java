package ru.grizzly_jr.level_edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ItemComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private ModelItem item;
	private boolean isSelect = false;
	
	public ItemComponent(ModelItem item)
	{
		this.item = item;
		
		int width = Translate.metrsToPixel(item.getWidth());
		int height = Translate.metrsToPixel(item.getHeight());
		this.setPreferredSize(new Dimension(width,height+15));
		this.setSize(width,height+15);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if( null == item.getImage())
			return;		
	
		if( isSelect){
			g.setColor(new Color(100,100,255));
		}else{
			g.setColor(new Color(255,255,255));
		}
		
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int width = Translate.metrsToPixel(item.getWidth());
		int height = Translate.metrsToPixel(item.getHeight());
		g.drawImage(item.getImage(), 0, 0, width,height, null);
		
		g.setColor(new Color(0,0,0));
		g.drawChars(item.name.toCharArray(), 0, item.name.length(), 0, height+12);
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
