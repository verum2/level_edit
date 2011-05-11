package items_component;

import items_component.MasterItem.TypeItem;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import ru.grizzly_jr.level_edit.PointD;
import ru.grizzly_jr.level_edit.Translate;


/**
 * Класс реализует панель для отрисовки листа
 * 
 * @author Александр
 * 
 */
public class PaintSheet extends JPanel {

	/**
	 * Листенер позволяющий отслеживать начало и окончания рисования полилинии
	 * 
	 * @author Александр
	 * 
	 */
	public interface ListenerActiveLine {
		/**
		 * Вызывается при начале рисования линии
		 */
		public void Active();
		/**
		 * Вызывается при окончании рисования линии
		 */
		public void NotActive();
	}
	
	public enum DrawShapeType
	{
		PHYSIC_CIRCLE,
		PHYSIC_POLYGON,
		NONE,
		SHELF_THREAD_1_DOWN,
		SHELF_THREAD_2_DOWN
	};
	
	Color ShapeColor[]={new Color(255,0,0),new Color(0,255,0),new Color(0,0,255),new Color(255,255,0),new Color(255,0,255)};
	
	private List<ListenerActiveLine> listeners = new ArrayList<ListenerActiveLine>();
	private static final long serialVersionUID = 1L;
	private BufferedImage bufferImage = null;
	private Point imagePos;
	private DrawShapePolygon lastPolygon = null;
	private ShapeCircle lastCircle = null;
	private MasterItem masterItem = null;
	private Timer getMousePositionTimer;
	private DrawShapeType currentShapeType = DrawShapeType.PHYSIC_CIRCLE;
	private double zoom = 2;
	
	/**
	 * Конструктор
	 * 
	 * @param _sheetOptions
	 *            настройки листа
	 * @param _lineOptions
	 *            настройки линии
	 * @param _polyLines
	 *            список полилиний
	 */
	public PaintSheet(MasterItem master) {
		super();
		this.masterItem = master;
		correctSize();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mousePress(e.getX(), e.getY(),e.getButton());
			}
		});
		
		getMousePositionTimer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TimeRepaint();
			}
		});
		getMousePositionTimer.start();
	}
	
	private void TimeRepaint()
	{
		if ( null == lastPolygon && null == lastCircle )
			return;
		
		Container parent = this;
		Point position = new Point(0, 0);
				
		while( null != parent)
		{
			position.x += parent.getLocation().x;
			position.y += parent.getLocation().y;
			parent = parent.getParent();
		}
		
		Point location = MouseInfo.getPointerInfo().getLocation();
		
		Point mousePos =  new Point(location.x - position.x - imagePos.x,location.y - position.y - imagePos.y);
		if (null != lastPolygon)
		{
			lastPolygon.move(Translate.pointPixelToMetrsWithZoom(mousePos, zoom));
		}
		
		if (null != lastCircle)
		{
			Point ccentr = Translate.pointMetrsToPixelWithZoom(lastCircle.getCenter(),zoom);
			double rx = mousePos.getX() - ccentr.getX();
			double ry = mousePos.getY() - ccentr.getY();
			double newRadius= Math.sqrt(rx*rx + ry*ry);
			lastCircle.setRadius(Translate.pixelToMetrsWithZoom((int)newRadius, zoom));
		}
		
		PaintSheet.this.repaint();
	}
	
	private void mousePress(int mouseX, int mouseY,int button)
	{
		Point point = new Point(mouseX - imagePos.x, mouseY - imagePos.y);
		PointD value = Translate.pointPixelToMetrsWithZoom(point, zoom);
		
		if(DrawShapeType.SHELF_THREAD_1_DOWN == currentShapeType && MouseEvent.BUTTON1 == button)
		{
			masterItem.shelf.point1 = value;
			currentShapeType = DrawShapeType.NONE;
			repaintImage();
		}
		if(DrawShapeType.SHELF_THREAD_2_DOWN == currentShapeType && MouseEvent.BUTTON1 == button)
		{
			masterItem.shelf.point2 = value;
			currentShapeType = DrawShapeType.NONE;
			repaintImage();
		}
		
		if(DrawShapeType.PHYSIC_POLYGON == currentShapeType)
		{
			if (MouseEvent.BUTTON1 == button) 
			{				
				if (null == lastPolygon) {
					int ShapeCount = masterItem.physic.getShapes().size();
					lastPolygon = new DrawShapePolygon(value ,ShapeColor[ShapeCount%ShapeColor.length]);
					for (ListenerActiveLine iter : listeners) {
						iter.Active();
					}
				} else {
					lastPolygon.addPoint(value);
				}
				repaint();
			
			}
			if (MouseEvent.BUTTON3 == button) 
			{
				if (null != lastPolygon) {
					lastPolygon.addPoint(value);
					masterItem.physic.getShapes().add(lastPolygon.getShapePolygon());
					lastPolygon = null;
					for (ListenerActiveLine iter : listeners) {
						iter.NotActive();
					}
					repaintImage();
				}
			}
		}
		if(DrawShapeType.PHYSIC_CIRCLE == currentShapeType)
		{
			if (MouseEvent.BUTTON1 == button) 
			{
				if(null == lastCircle)
				{
					int ShapeCount = masterItem.physic.getShapes().size();
					lastCircle = new ShapeCircle(value,0,ShapeColor[ShapeCount%ShapeColor.length]);
					repaint();
					for (ListenerActiveLine iter : listeners) {
						iter.Active();
					}
				}else{
					masterItem.physic.getShapes().add(lastCircle);
					lastCircle = null;
					
					for (ListenerActiveLine iter : listeners) {
						iter.NotActive();
					}
					repaintImage();
				}
					
			}
		}
	}
	
	public void setDrawShapeType(DrawShapeType type)
	{
		currentShapeType = type;
	}

	/**
	 * Добавляет листенер
	 * 
	 * @param listener
	 *            листенер
	 */
	public void addListener(ListenerActiveLine listener) {
		listeners.add(listener);
	}

	public void correctSize()
	{
		int width = Translate.metrsToPixel(masterItem.getWidth());
		int height = Translate.metrsToPixel(masterItem.getHeight());
		setPreferredSize(new Dimension(width,height));
		setSize(new Dimension(width,height));
	}
	
	
	public List<Shape> getShapes() {
		return masterItem.physic.getShapes();
	}

	/**
	 * Перерисоввывает изображение в буфере и панель
	 */
	public void repaintImage() {

		Dimension Size = getSize();
		
		int center_x = Translate.metrsToPixelWithZoom(masterItem.getWidth(), zoom);
		int center_y = Translate.metrsToPixelWithZoom(masterItem.getHeight(),zoom);
		imagePos = new Point((Size.width - center_x)/2,(Size.height - center_y)/2);
		bufferImage = new BufferedImage(Size.width, Size.height,BufferedImage.TYPE_INT_ARGB);
		
		masterItem.redrawImageWithShapes();
		
		Graphics2D g = bufferImage.createGraphics();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, Size.width, Size.height);
		
		BufferedImage image = masterItem.getImage(false);
		g.drawImage(image, imagePos.x, imagePos.y, 
		                   (int)(image.getWidth()*zoom), (int)(image.getHeight()*zoom), this);
		
		g.setColor(new Color(0,0,0));
		
		g.drawRect( imagePos.x-1, imagePos.y-1, 
		            (int)(image.getWidth()*zoom)+2, (int)(image.getHeight()*zoom)+2);
		
		if( TypeItem.PHYSIC == masterItem.getType() || TypeItem.SHELF == masterItem.getType()){
			for (Shape shape :  masterItem.physic.getShapes()) {
				shape.drawWithZoom(g,zoom,imagePos);
			}
			
			if(TypeItem.SHELF == masterItem.getType()){
				g.setColor(Color.red);
				ShelfPhysicItem shelf = masterItem.shelf;
				int radius = 4;
				int p1_x = Translate.metrsToPixelWithZoom(shelf.point1.getX(),zoom) + imagePos.x;
				int p1_y =Translate.metrsToPixelWithZoom(shelf.point1.getY(),zoom) + imagePos.y;
				int p2_x = Translate.metrsToPixelWithZoom(shelf.point2.getX(),zoom) + imagePos.x;
				int p2_y = Translate.metrsToPixelWithZoom(shelf.point2.getY(),zoom) + imagePos.y;
					
				g.fillOval(p1_x - radius, p1_y - radius, radius * 2,radius * 2);
				g.fillOval(p2_x - radius, p2_y - radius, radius * 2,radius * 2);
			}
		}
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if (null == bufferImage) {
			repaintImage();
		}
		g.drawImage(bufferImage, 0, 0, this);
		if (null != lastPolygon) {
			lastPolygon.draw((Graphics2D) g,zoom, imagePos);
		}
		if (null != lastCircle) {
			lastCircle.drawWithZoom((Graphics2D) g,zoom,imagePos);
		}
		
		/*if(TypeItem.SHELF == masterItem.getType()){
			masterItem.drawShelf((Graphics2D) g);
		}*/
	}
}
