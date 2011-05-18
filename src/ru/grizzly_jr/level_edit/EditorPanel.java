package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
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
	private BufferedImage gun_image = null;
	private BufferedImage bag_image = null;
	private Point gun_p = new Point(0,0);
	private Point bag_p = new Point(0,0);
	
	private String background;
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
		try {
			gun_image = ImageIO.read(new File("data/rabbitgun.png"));
			bag_image = ImageIO.read(new File("data/rabbitbag.png"));
		} catch (IOException e1) {
		}
		
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
	
	public void setPosition(PointD gun, PointD bag)
	{
		double w_gun = Translate.pixelToMetrs(gun_image.getWidth());
		double h_gun = Translate.pixelToMetrs(gun_image.getHeight());
		
		double w_bag = Translate.pixelToMetrs(bag_image.getWidth());
		double h_bag = Translate.pixelToMetrs(bag_image.getHeight());
		
		gun_p = Translate.getPoint(gun.x, gun.y, w_gun, h_gun);
		bag_p = Translate.getPoint(gun.x, gun.y, w_bag, h_bag);
	}
	
	public PointD getPositionGun()
	{
		return Translate.getPointD(gun_p.x,	gun_p.y,gun_image.getWidth(), gun_image.getHeight());
	}
	
	public PointD getPositionBag()
	{
		return Translate.getPointD(bag_p.x,	bag_p.y,bag_image.getWidth(), bag_image.getHeight());
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
		info.background = background;
		info.models = list;
		info.rabbit_gun = getPositionGun();
		info.rabbit_bag = getPositionBag();
		return info;
	}
	
	public void setInformation(InformationLevel info)
	{
		if( null == info)
			return;
		load(info.background);
		setItems(info.models);
		setPosition(info.rabbit_gun, info.rabbit_bag);
	}
	
	public boolean load(String name)
	{
		try {
			image = ImageIO.read(new File("data/"+name+".png"));
			widthImage = Translate.pixelToMetrs( image.getWidth());
			heightImage = Translate.pixelToMetrs( image.getHeight());
			Translate.width = image.getWidth();
			Translate.height = image.getHeight();
			background = name;
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
		int w = (rec.width);
		int h = (rec.height);
		int w2 = (this.getWidth());
		int h2 = (this.getHeight());
		int x = (scrollX);
		int y = (scrollY+scrollMouse);
		
		PointD p = Translate.getPointD(x + Math.min(w,w2)/2, y + Math.min(h,h2)/2);
		item.x = p.x;
		item.y = p.y;
		item.update();
		
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
		private boolean is_gun = false;
		private boolean is_bag = false;

		@Override
		public void mouseReleased(MouseEvent e) {
			item = null;
			is_gun = false;
			is_bag = false;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			lastY = e.getY();
			lastX = e.getX();
			
			Rectangle rec = getRec();
			
			item = null;
			for( ModelItem it: list){
				if( it.collisionPoint(lastX-rec.x, lastY-rec.y+scrollMouse)){
					item = it;
				}
			}
			if( null == item){
				int x = lastX-rec.x;
				int y = lastY-rec.y+scrollMouse-10;
				if( gun_p.x < x && x < gun_p.x+ gun_image.getWidth() &&
					gun_p.y < y && y < gun_p.y+ gun_image.getHeight()){
					is_gun = true;
					return;
				}
				if( bag_p.x < x && x < bag_p.x+ bag_image.getWidth() &&
					bag_p.y < y && y < bag_p.y+ bag_image.getHeight()){
					is_bag = true;
					return;
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
			if( is_gun)
			{
				gun_p.x += x-lastX;
				gun_p.y += y-lastY;
			}
			if( is_bag)
			{
				bag_p.x += x-lastX;
				bag_p.y += y-lastY;
			}			
			
			if( resolution != Resolution.FULL && null == item && !is_gun && !is_bag)
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
			g.drawImage(bag_image,x+bag_p.x,y+bag_p.y,null);
			g.drawImage(gun_image,x+gun_p.x,y+gun_p.y,null);
			
			for( ModelItem iter: list){
				paintItem(iter,g,x,y);
			}
		}
		
		private void paintItem(ModelItem item, Graphics g,int xS, int yS)
		{
			if( null == item.getImage(isShaped))
				return;
			item.draw(g, xS, yS, isShaped);
		}
	}

	@Override
	public void delete(ModelItem model) {
		removeItem(model);
	}
	
	
	
	
}
