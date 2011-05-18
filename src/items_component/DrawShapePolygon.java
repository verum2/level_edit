package items_component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import ru.grizzly_jr.level_edit.PointD;
import ru.grizzly_jr.level_edit.Translate;

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

	public void draw(Graphics2D g,double zoom, Point move,Point tran) {

		shapePolygon.drawWithZoom(g,false,zoom,move,tran);
		/*
		BasicStroke stroke = new BasicStroke((float) lineOptions.getThick(),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
				lineOptions.getDashType(), 0);
		g.setStroke(stroke);
*/
		g.setColor(lineColor);
		Point fp= Translate.pointMetrsToPixelWithZoom(LastPoint, zoom);
		Point ep= Translate.pointMetrsToPixelWithZoom(ActivePoint, zoom);
		fp.x -= tran.x;
		fp.y -= tran.y;
		//ep.x -= tran.x;
		//ep.y -= tran.y;
		g.drawLine(fp.x + move.x,fp.y + move.y,ep.x + move.x,ep.y + move.y);
		}

}
