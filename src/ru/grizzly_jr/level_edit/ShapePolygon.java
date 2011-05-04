package ru.grizzly_jr.level_edit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ShapePolygon {
	
	private List<PointD> points = null;
	private Color lineColor;
	
	public ShapePolygon(Color lineColor) {
		super();
		this.lineColor=lineColor;
		points = new ArrayList<PointD>();
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
	public void drawWithZoom(Graphics2D g,boolean isFinished,double zoom) {
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
			Point fp= Translate.pointMetrsToPixelWithZoom(points.get(0), zoom);
			Point ep= Translate.pointMetrsToPixelWithZoom(points.get(points.size()-1), zoom);
		g.drawLine(fp.x,fp.y,ep.x,ep.y);
		}
	}
	
	
	
}
