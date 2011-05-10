package items_component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import ru.grizzly_jr.level_edit.PointD;

public class ShapeComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private Shape shape;
	private boolean isSelect = false;
	private int height = 0;
	
	public ShapeComponent(Shape shape)
	{
		this.shape = shape;
		if( shape instanceof ShapeCircle){
			height = 18;
		}
		if( shape instanceof ShapePolygon){
			int count = ((ShapePolygon)shape).getPointCount();
			height = count*15;
		}
		
		this.setPreferredSize(new Dimension(200,height));
		this.setSize(200,height);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if( null == shape)
			return;		
	
		if( isSelect){
			g.setColor(new Color(180,180,255));
		}else{
			g.setColor(new Color(255,255,255));
		}
		
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(new Color(0,0,0));
		
		if( shape instanceof ShapeCircle){
			String text = "Circle: "+shape.toString();
			g.drawChars(text.toCharArray(), 0, text.length(), 0, 10);
		}
		if( shape instanceof ShapePolygon){
			ShapePolygon poly = ((ShapePolygon)shape);
			int count = poly.getPointCount();
			
			String text = "Polygon: ";
			g.drawChars(text.toCharArray(), 0, text.length(), 0, 10);
			for( int i = 0; i < count; i++)
			{
				PointD p = poly.getPoint(i);
				text = "("+p.getX()+" , "+p.getY() +")";
				g.drawChars(text.toCharArray(), 0, text.length(), 20, 15*(i+1)+10);
			}
		}
		
	}
	
	public void select(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
}
