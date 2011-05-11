package items_component;


import java.util.ArrayList;
import java.util.List;

public class PhysicItem {
	
	public double linear_damping = 0.0;
	public double angular_damping = 0.0;
	public boolean isBullet = false;
	
	private List<Shape> shapes = new ArrayList<Shape>();
	
	public PhysicItem() {
	}
	
	public List<Shape> getShapes() {
		return shapes;
	}
	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}
	
}
