package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EditorPanel extends JPanel {
	public enum Resolution
	{
		IPAD,
		IPOD,
		IPHONE1,
		IPHONE2,
		FULL
	}
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;
	private double width;
	private double height;
	private List<ModelItem> list = new ArrayList<ModelItem>();
	private Resolution resolution = Resolution.FULL;
	private int scrollX = 0;
	private int scrollY = 0;

	public EditorPanel()
	{
		super(new BorderLayout());
		setMaximumSize(new Dimension(320,10000));
		setMinimumSize(new Dimension(320,20));
		setPreferredSize(new Dimension(320,480));
		
		JScrollPane scrollpane = new JScrollPane(new PanelPaint());
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				scrollY = e.getValue();	
			}});
		scrollpane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				scrollX = e.getValue();	
			}});
		
	    add(scrollpane, BorderLayout.CENTER);
	}
	
	public boolean load(String path)
	{
		try {
			image = ImageIO.read(new File(path));
			width = Translate.pixelToMetrs( image.getWidth());
			height = Translate.pixelToMetrs( image.getHeight());
			return true;
		} catch (IOException e) {
		}
		return false;
	}
	
	public void setItems(List<ModelItem> list)
	{
		this.list = list;
		repaint();
	}
	
	public void addItem(ModelItem item)
	{
		list.add(item);
		repaint();
	}
	
	public void removeItem(ModelItem item)
	{
		list.remove(item);
		repaint();
	}
	
	private class PanelPaint extends JPanel
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			if( null == image)
				return;
			
			int w = Translate.metrsToPixel(width);
			int h = Translate.metrsToPixel(height);
			int x = 0;
			int y = 0;
			
			Rectangle rec = new Rectangle();
			switch( resolution){
			case IPAD: rec.width = 1024; rec.height = 768; break;
			case IPOD: rec.width = 320; rec.height = 480; break;
			case IPHONE1: rec.width = 320; rec.height = 480; break;
			case IPHONE2: rec.width = 640; rec.height = 960; break;
			}
			
			if( resolution != Resolution.FULL)
			{
				x = (EditorPanel.this.getWidth() - rec.width)/2;
				y = (EditorPanel.this.getHeight() - rec.height)/2;
				rec.y = y+scrollY;
				rec.x = x+scrollX;
				if( x < 0)
					x = 0;
				if( y < 0)
					y = 0;
				
				g.clipRect(rec.x, rec.y, rec.width, rec.height);
				this.setSize(Math.max(rec.width,w),Math.max(rec.height,h));
				this.setPreferredSize(new Dimension(Math.max(rec.width,w),Math.max(rec.height,h)));
			}else{
				x = (EditorPanel.this.getWidth() - w)/2;
				y = 10;
				rec.x = x;
				rec.y = y;
				this.setSize(w, h);
				this.setPreferredSize(new Dimension(w,h));
			}
			
			g.drawImage(image,x,y,w,h,null);
			
			for( ModelItem iter: list){
				paintItem(iter,g,x,y);
			}
		}
		
		private void paintItem(ModelItem item, Graphics g,int xS, int yS)
		{
			if( null == item.getImage())
				return;
			
			int w = Translate.metrsToPixel(item.getWidth());
			int h = Translate.metrsToPixel(item.getHeight());
			int x = Translate.metrsToPixel(item.x) + xS;
			int y = Translate.metrsToPixel(item.y) + yS;
			g.drawImage(item.getImage(),x,y,w,h,null);
		}
	}
	
	
	
	
}
