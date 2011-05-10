package items_component;


import java.util.ArrayList;
import java.util.List;

public class PhysicItem {

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
