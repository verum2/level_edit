package ru.grizzly_jr.level_edit;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *  ласс хранит данные о рисующейс€ в данный момент полилинии и способен ее
 * отрисовать
 * 
 * @author јлександр
 * 
 */
public class DrawShapePolygon {
	private ShapePolygon shapePolygon;
	
	private Color lineColor;
	private PointD ActivePoint = null;
	private PointD LastPoint = null;
	
	public DrawShapePolygon(PointD p,Color lineColor) {
		super();
		this.lineColor=lineColor;
		shapePolygon = new ShapePolygon(lineColor);
		addPoint(p);
	}
	
	public void addPoint(PointD p) {
		shapePolygon.addPoint(p);
		LastPoint = p;
		ActivePoint = p;
	}
	
	public void move(PointD p) {
		ActivePoint = p;
	}	

	public ShapePolygon getShapePolygon() {
		return shapePolygon;
	}

	public void draw(Graphics2D g,double zoom) {

		shapePolygon.drawWithZoom(g,false,zoom);
		/*
		BasicStroke stroke = new BasicStroke((float) lineOptions.getThick(),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
				lineOptions.getDashType(), 0);
		g.setStroke(stroke);
*/
		g.setColor(lineColor);

		g.drawLine(Translate.metrsToPixelWithZoom(LastPoint.getX(), zoom), Translate.metrsToPixelWithZoom(LastPoint.getY(), zoom), Translate.metrsToPixelWithZoom(ActivePoint.getX(), zoom), Translate.metrsToPixelWithZoom(ActivePoint.getY(), zoom));
	}

}
