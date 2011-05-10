package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EditorPanel extends JPanel implements ModelItem.isDelete {
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
	private String background_path;
	private double widthImage;
	private double heightImage;
	private List<ModelItem> list = new ArrayList<ModelItem>();
	private Resolution resolution = Resolution.IPOD;
	private int scrollX = 0;
	private int scrollY = 0;
	private int scrollMouse = 0;
	private boolean isShaped= false;
	private InformationPanel info_panel = null;

	public EditorPanel(InformationPanel panel_info)
	{
		super(new BorderLayout());
		info_panel = panel_info;
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
	    
	    ModelItem.addListenerDelete(this);
	}
	
	public void reverseShaped()
	{
		isShaped = !isShaped;
	}
	
	public void setResolution(Resolution res)
	{
		this.resolution = res;
		scrollMouse = 0;
	}
	
	public InformationLevel getInformation()
	{
		InformationLevel info = new InformationLevel();
		info.background = background_path;
		info.models = list;
		return info;
	}
	
	public void setInformation(InformationLevel info)
	{
		load(info.background);
		setItems(info.models);
	}
	
	public boolean load(String path)
	{
		try {
			image = ImageIO.read(new File(path));
			widthImage = Translate.pixelToMetrs( image.getWidth());
			heightImage = Translate.pixelToMetrs( image.getHeight());
			background_path = path;
			return true;
		} catch (IOException e) {
		}
		return false;
	}
	
	public void setItems(List<ModelItem> list)
	{
		this.list = list;
		info_panel.setItems(list);
		repaint();
	}
	
	public void addItem(ModelItem item)
	{
		list.add(item);
		info_panel.addItem(item);
		repaint();
	}
	
	public void addCenterItem(ModelItem item)
	{
		list.add(item);
		info_panel.addItem(item);
		Rectangle rec = getRec();
		double w = Translate.pixelToMetrs(rec.width);
		double h = Translate.pixelToMetrs(rec.height);
		double w2 = Translate.pixelToMetrs(this.getWidth());
		double h2 = Translate.pixelToMetrs(this.getHeight());
		double x = Translate.pixelToMetrs(scrollX);
		double y = Translate.pixelToMetrs(scrollY+scrollMouse);
		
		item.x = x + Math.min(w,w2)/2.0;
		item.y = y + Math.min(h,h2)/2.0;
		
		repaint();
	}
	
	public void removeItem(ModelItem item)
	{
		list.remove(item);
		info_panel.removeItem(item);
		repaint();
	}
	
	private Rectangle getRec()
	{
		Rectangle rec = new Rectangle();
		if( resolution == Resolution.FULL){
			rec.width = Translate.metrsToPixel(widthImage);
			rec.height = Translate.metrsToPixel(heightImage);
			rec.x = (EditorPanel.this.getWidth() - rec.width)/2;
			rec.y = 10;
		}else{
			switch( resolution){
			case IPAD: rec.width = 320; rec.height = 427; break;
			case IPOD: rec.width = 320; rec.height = 480; break;
			case IPHONE1: rec.width = 320; rec.height = 480; break;
			case IPHONE2: rec.width = 320; rec.height = 480; break;
			}
			rec.x = (EditorPanel.this.getWidth() - rec.width)/2;
			rec.y = (EditorPanel.this.getHeight() - rec.height)/2;
		}
		return rec;
	}
	
	private class Mouse extends MouseAdapter
	{
		private int lastY = 0;
		private int lastX = 0;
		private ModelItem item = null;

		@Override
		public void mouseReleased(MouseEvent e) {
			item = null;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			lastY = e.getY();
			lastX = e.getX();
			
			Rectangle rec = getRec();
			
			for( ModelItem it: list){
				if( it.collisionPoint(lastX-rec.x, lastY-rec.y+scrollMouse)){
					item = it;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {			
			int y = e.getY();
			int x = e.getX();
			
			Rectangle rec = getRec();
			if( x < rec.x || x > rec.x + rec.width ||
				y < rec.y || y > rec.y + rec.height){
						return;
				}
			

			if( null != item){
				item.move(x-lastX,y-lastY);
			}
			
			if( resolution != Resolution.FULL && null == item)
			{				
				scrollMouse += lastY - y;
				if( scrollMouse < 0)
					scrollMouse = 0;
				if(scrollMouse > Translate.metrsToPixel(heightImage)-rec.height)
					scrollMouse = Translate.metrsToPixel(heightImage)-rec.height;

			}
			
			lastY = y;
			lastX = x;
			repaint();
		}		
	}
	
	private class PanelPaint extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public PanelPaint()
		{
			super();
			Mouse mouse = new Mouse();
			this.addMouseMotionListener(mouse);
			this.addMouseListener(mouse);
		}
		
		@Override
		public void paint(Graphics g) {
			if( null == image)
				return;
			
			int w = Translate.metrsToPixel(widthImage);
			int h = Translate.metrsToPixel(heightImage);
			int x = 0;
			int y = 0;
			
			Rectangle rec = getRec();
			
			int ww = Math.max(rec.width, EditorPanel.this.getWidth());
			int hh = Math.max(rec.height, EditorPanel.this.getHeight());
			ww = Math.max(ww, w);
			hh = Math.max(hh, h);
			g.setColor(new Color(127,127,127));
			g.fillRect(0, 0, ww, hh);
			
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
				y -= scrollMouse;
				g.clipRect(rec.x, rec.y, rec.width, rec.height);
				
				ww = Math.max(rec.width, EditorPanel.this.getWidth()-20);
				hh = Math.max(rec.height, EditorPanel.this.getHeight()-20);
				
				this.setSize(ww,hh);
				this.setPreferredSize(new Dimension(rec.width,rec.height));
			}else{
				x = (EditorPanel.this.getWidth() - w)/2;
				y = 10;
				rec.x = x;
				rec.y = y;
				this.setSize(ww, hh);
				this.setPreferredSize(new Dimension(w,h));
			}
			
			g.drawImage(image,x,y,w,h,null);
			
			for( ModelItem iter: list){
				paintItem(iter,g,x,y);
			}
		}
		
		private void paintItem(ModelItem item, Graphics g,int xS, int yS)
		{
			if( null == item.getImage(isShaped))
				return;
			
			int w = Translate.metrsToPixel(item.getWidth());
			int h = Translate.metrsToPixel(item.getHeight());
			int x = Translate.metrsToPixel(item.x) + xS;
			int y = Translate.metrsToPixel(item.y) + yS;
			g.drawImage(item.getImage(isShaped),x,y,w,h,null);
		}
	}

	@Override
	public void delete(ModelItem model) {
		removeItem(model);
	}
	
	
	
	
}
