package items_component;

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
	
	public ShapePolygon(Color lineColor) {
		this.lineColor = lineColor;
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
	public void draw(Graphics2D g){
		draw(g,true);
	}
	
	public void draw(Graphics2D g,boolean isFinished) {
		/*
		BasicStroke stroke = new BasicStroke((float) lineOptions.getThick(),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
				lineOptions.getDashType(), 0);
		g.setStroke(stroke);
		*/
		g.setColor(lineColor);
		Point PreviousPoint = null;
		for (PointD pd : points) {
			Point p = Translate.pointMetrsToPixelWithZoom(pd, 1);
			int radius=5;
			g.fillOval(p.x - (int) radius, p.y
					- (int) radius,
					(int) radius * 2,
					(int) radius * 2);

			if (null != PreviousPoint) {
				g.drawLine(PreviousPoint.x, PreviousPoint.y, p.x, p.y);
			}
			PreviousPoint = p;
		}
		if(isFinished)
		{
			Point fp= Translate.pointMetrsToPixelWithZoom(points.get(0), 1);
			Point ep= Translate.pointMetrsToPixelWithZoom(points.get(points.size()-1), 1);
		g.drawLine(fp.x,fp.y,ep.x,ep.y);
		}
	}
	
	@Override
	public void drawWithZoom(Graphics2D g,double zoom,Point move) {
		drawWithZoom(g, true,zoom, move);
	}
	
	public void drawWithZoom(Graphics2D g,boolean isFinished,double zoom,Point move) {
		/*
		BasicStroke stroke = new BasicStroke((float) lineOptions.getThick(),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
				lineOptions.getDashType(), 0);
		g.setStroke(stroke);
		*/
		g.setColor(lineColor);
		Point PreviousPoint = null;
		for (PointD pd : points) {
			Point p = Translate.pointMetrsToPixelWithZoom(pd, zoom);
			int radius=5;
			g.fillOval(p.x - (int) radius + move.x, p.y
					- (int) radius + move.y,
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
		g.drawLine(fp.x + move.x,fp.y + move.y,ep.x + move.x,ep.y + move.y);
		}
	}
	
	
	
}
