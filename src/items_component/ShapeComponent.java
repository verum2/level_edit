package items_component;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ShapeComponent extends JPanel {
	public interface RemoveListener
	{
		public void remove();
	};	
	
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private JTextField friction = null;
	private JTextField spring = null;
	private JTextField dencity = null;
	private List<RemoveListener> listeners = new ArrayList<RemoveListener>();
	
	public ShapeComponent(Shape shape)
	{
		super(new GridBagLayout());
		this.shape = shape;
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 2;
		
		int yS = 1;
		if( shape instanceof ShapeCircle){
			JLabel label = new JLabel("Circle:",JLabel.LEFT);
			label.setForeground(((ShapeCircle)shape).getLineColor());
			
			c.gridy = 0;
			add(label,c);
			/*c.gridy = 1;
			add(new JLabel("   "+shape.toString(),JLabel.RIGHT),c);
			yS = 2;*/
		}
		if( shape instanceof ShapePolygon){			
			ShapePolygon poly = ((ShapePolygon)shape);
			
			JLabel label = new JLabel("Polygon:",JLabel.LEFT);
			label.setForeground(poly.getLineColor());
			c.gridy = 0;
			add(label,c);
			
			/*int count = poly.getPointCount();
			for( int i = 0; i < count; i++)
			{
				c.gridy = i+1;
				PointD p = poly.getPoint(i);
				add(new JLabel("   ("+p.getX()+" , "+p.getY() +")",JLabel.RIGHT),c);
			}
			yS = count+1;*/
		}
		
		c.gridwidth = 1;
		
		c.gridy = yS;
		add(new JLabel("Friction:"),c);
		friction = new JTextField(Double.toString(shape.getFriction()));
		
		friction.setPreferredSize(new Dimension(50,20));
		add(friction,c);
		
		c.gridy = yS+1;
		add(new JLabel("Spring:"),c);
		spring = new JTextField(Double.toString(shape.getSpring()));
		spring.setPreferredSize(new Dimension(50,20));
		add(spring,c);
		
		c.gridy = yS+2;
		add(new JLabel("Dencity:"),c);
		dencity = new JTextField(Double.toString(shape.getDencity()));
		dencity.setPreferredSize(new Dimension(50,20));
		add(dencity,c);
		
		JButton delete = new JButton("delete");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for( RemoveListener listener: listeners){
					listener.remove();
				}
			}
		});
		
		c.gridy = yS+3;
		add(delete,c);
	}
	
	public void addListener(RemoveListener listener)
	{
		listeners.add(listener);
	}
	
	public void recalculation()
	{
		shape.setFriction(Double.valueOf(friction.getText()));
		shape.setDencity(Double.valueOf(dencity.getText()));
		shape.setSpring(Double.valueOf(spring.getText()));
	}
	
	public Shape getShape()
	{
		return shape;
	}
}
