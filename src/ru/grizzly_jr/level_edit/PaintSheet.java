package ru.grizzly_jr.level_edit;

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
		Circle,
		Polygon
	};
	
	
	Color ShapeColor[]={new Color(255,0,0),new Color(0,255,0),new Color(0,0,255),new Color(255,255,0),new Color(255,0,255)};
	
	
	
	private List<ListenerActiveLine> listeners = new ArrayList<ListenerActiveLine>();
	private static final long serialVersionUID = 1L;
	private BufferedImage bufferImage = null;
	//private List<PolyLine> polyLines = null;
	private DrawShapePolygon lastPolygon = null;
	private ShapeCircle lastCircle = null;
	private MasterItem masterItem = null;
	private Timer getMousePositionTimer;
	private DrawShapeType currentShapeType = DrawShapeType.Polygon;
	private double zoom=2;
	
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
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(DrawShapeType.Polygon==currentShapeType)
				{
				if (MouseEvent.BUTTON1 == e.getButton()) {
					
					if (null == PaintSheet.this.lastPolygon) {
						int ShapeCount = PaintSheet.this.masterItem.physic.getPolygonList().size()+PaintSheet.this.masterItem.physic.getCircleList().size();
						PaintSheet.this.lastPolygon = new DrawShapePolygon(Translate.pointPixelToMetrsWithZoom(new Point(e.getX(), e.getY()), zoom) ,ShapeColor[ShapeCount%ShapeColor.length]);
						PaintSheet.this.repaint();
						for (ListenerActiveLine iter : listeners) {
							iter.Active();
						}
					} else {
						PaintSheet.this.lastPolygon.addPoint(Translate.pointPixelToMetrsWithZoom(new Point(e
								.getX(), e.getY()),zoom));
						PaintSheet.this.repaint();
					}
					
				}
				if (MouseEvent.BUTTON3 == e.getButton()) {
					if (null != PaintSheet.this.lastPolygon) {
						PaintSheet.this.lastPolygon.addPoint(Translate.pointPixelToMetrsWithZoom(new Point(e
								.getX(), e.getY()),zoom));
						PaintSheet.this.masterItem.physic.getPolygonList().add(PaintSheet.this.lastPolygon.getShapePolygon());
						PaintSheet.this.lastPolygon = null;
						for (ListenerActiveLine iter : listeners) {
							iter.NotActive();
						}
						PaintSheet.this.repaintImage();
					}
				}
			}
				if(DrawShapeType.Circle==currentShapeType)
				{
					if (MouseEvent.BUTTON1 == e.getButton()) {
					if(null==PaintSheet.this.lastCircle)
					{
						int ShapeCount = PaintSheet.this.masterItem.physic.getPolygonList().size()+PaintSheet.this.masterItem.physic.getCircleList().size();
						PaintSheet.this.lastCircle = new ShapeCircle(Translate.pointPixelToMetrsWithZoom(new Point(e
								.getX(), e.getY()),zoom),0,ShapeColor[ShapeCount%ShapeColor.length]);
						PaintSheet.this.repaint();
						for (ListenerActiveLine iter : listeners) {
							iter.Active();
						}
					}
						
					}
					if (MouseEvent.BUTTON3 == e.getButton()) {
						if(null!=PaintSheet.this.lastCircle)
						{
							PaintSheet.this.masterItem.physic.getCircleList().add(PaintSheet.this.lastCircle);
							
							PaintSheet.this.lastCircle = null;
							
							for (ListenerActiveLine iter : listeners) {
								iter.NotActive();
							}
							PaintSheet.this.repaintImage();
						}
						
					}
					
				}
			}
		});

		
		getMousePositionTimer = new Timer(20, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				

				if ((null != PaintSheet.this.lastPolygon)||(null!=PaintSheet.this.lastCircle)) {
					Container par = PaintSheet.this;
					Point position = new Point(0, 0);
							
					while(null!=par)
					{
						position.x+= par.getLocation().x;
						position.y+= par.getLocation().y;
						par = par.getParent();
					}
					
					Point mouseClickPos =  new Point((int)MouseInfo.getPointerInfo().getLocation().getX()-position.x,(int)MouseInfo.getPointerInfo().getLocation().getY()-position.y);
					if (null != PaintSheet.this.lastPolygon)
					{
					PaintSheet.this.lastPolygon.move(Translate.pointPixelToMetrsWithZoom(mouseClickPos, zoom));
					}
					
					if (null != PaintSheet.this.lastCircle)
					{
						Point ccentr = Translate.pointMetrsToPixelWithZoom(PaintSheet.this.lastCircle.getCenter(),zoom) ;
						double newRadius= Math.sqrt((mouseClickPos.getX()-ccentr.getX())*(mouseClickPos.getX()-ccentr.getX())+(mouseClickPos.getY()-ccentr.getY())*(mouseClickPos.getY()-ccentr.getY()));
						PaintSheet.this.lastCircle.setRadius(newRadius);
					}
					
					PaintSheet.this.repaint();
				}
			}
		});
		getMousePositionTimer.start();
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

	/**
	 * Устанавливает новый список полилиний
	 * 
	 * @param polyLines
	 *            список полилиний
	 */
/*
	public void setPolyLines(List<PolyLine> polyLines) {
		this.polyLines = polyLines;
	}
*/
	
	/**
	 * Возвращает текущий список полилиний
	 * 
	 * @return текущий список полилиний
	 */
	public List<ShapePolygon> getShapePolygon() {
		return masterItem.physic.getPolygonList();
	}

	/**
	 * Перерисоввывает изображение в буфере и панель
	 */
	public void repaintImage() {

		Dimension Size = getSize();
		masterItem.redrawImageWithShapes();
		bufferImage = new BufferedImage(Size.width, Size.height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferImage.createGraphics();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, Size.width, Size.height);
		g.drawImage(masterItem.getImage(false), 1, 1, (int)(masterItem.getImage(false).getWidth()*zoom),
				(int)(masterItem.getImage(false).getHeight()*zoom), this);
		for (ShapePolygon shapePolygon : masterItem.physic.getPolygonList()) {
			shapePolygon.drawWithZoom(bufferImage.createGraphics(),true,zoom);
		}
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Graphics2D g2d=(Graphics2D)g;
		if (null == bufferImage) {
			repaintImage();
		}
		g.drawImage(bufferImage, 0, 0, this);
		if (null != lastPolygon) {
			lastPolygon.draw((Graphics2D) g,zoom);
		}
		if (null != lastCircle) {
			lastCircle.draw((Graphics2D) g);
		}

	}
}
