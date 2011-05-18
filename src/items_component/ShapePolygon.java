package items_component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ru.grizzly_jr.level_edit.PointD;
import ru.grizzly_jr.level_edit.Translate;

public class ShapePolygon implements Shape{
	
	private List<PointD> points = new ArrayList<PointD>();
	private Color lineColor;
	private int thick = 2;
	
	private double friction = 0.2;
	private double spring = 0.05;
	private double dencity = 2.0;
	
	public ShapePolygon(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getLineColor()
	{
		return lineColor;
	}
	
	public void addPoint(PointD point) {
		points.add(point);
	}

	
	public PointD getPoint(int i) {
		return points.get(i);
	}

	public int getPointCount() {
		return points.size();
	}

	@Override
	public String toString()
	{
		String result = "";
		for( PointD point: points){
			result += "("+point.getX()+","+point.getY()+")";
			result += "\n";
		}
		return result;
	}
	
	@Override
	public void draw(Graphics2D g, Point tran){
		draw(g,true,tran);
	}
	
	public void draw(Graphics2D g,boolean isFinished,Point tran) {
		g.setStroke(new BasicStroke(thick));
		g.setColor(lineColor);
		Point PreviousPoint = null;
		for (PointD pd : points) {
			Point p = Translate.pointMetrsToPixelWithZoom(pd, 1);
			p.x -= tran.x;
			p.y -= tran.y;
			int radius = 1;
			g.fillOval(p.x -radius, p.y - radius,
					radius * 2,
					radius * 2);

			if (null != PreviousPoint) {
				g.drawLine(PreviousPoint.x, PreviousPoint.y, p.x, p.y);
			}
			PreviousPoint = p;
		}
		if(isFinished)
		{
			Point fp= Translate.pointMetrsToPixelWithZoom(points.get(0), 1);
			Point ep= Translate.pointMetrsToPixelWithZoom(points.get(points.size()-1), 1);
			g.drawLine(fp.x - tran.x,fp.y - tran.y,ep.x - tran.x,ep.y - tran.y);
		}
		
		g.setStroke(new BasicStroke(thick));
	}
	
	@Override
	public void drawWithZoom(Graphics2D g,double zoom,Point move,Point tran) {
		drawWithZoom(g, true,zoom, move,tran);
	}
	
	public void drawWithZoom(Graphics2D g,boolean isFinished,double zoom,Point move,Point tran) {
		g.setStroke(new BasicStroke(thick));
		g.setColor(lineColor);
		Point PreviousPoint = null;
		for (PointD pd : points) {
			Point p = Translate.pointMetrsToPixelWithZoom(pd, zoom);
			p.x -= tran.x;
			p.y -= tran.y;
			int radius = 1;
			g.fillOval(p.x - (int) radius + move.x, p.y - (int) radius + move.y,
					(int) radius * 2,
					(int) radius * 2);

			if (null != PreviousPoint) {
				g.drawLine(PreviousPoint.x + move.x, PreviousPoint.y + move.y, p.x + move.x, p.y + move.y);
			}
			PreviousPoint = p;
		}
		if(isFinished)
		{
			Point fp= Translate.pointMetrsToPixelWithZoom(points.get(0), zoom);
			Point ep= Translate.pointMetrsToPixelWithZoom(points.get(points.size()-1), zoom);
			fp.x -= tran.x;
			fp.y -= tran.y;
			ep.x -= tran.x;
			ep.y -= tran.y;
		    g.drawLine(fp.x + move.x,fp.y + move.y,ep.x + move.x,ep.y + move.y);
		}
		
		g.setStroke(new BasicStroke());
	}

	public double getFriction() {
		return friction;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}


	public double getSpring() {
		return spring;
	}


	public void setSpring(double spring) {
		this.spring = spring;
	}


	public double getDencity() {
		return dencity;
	}


	public void setDencity(double dencity) {
		this.dencity = dencity;
	}
	
}
