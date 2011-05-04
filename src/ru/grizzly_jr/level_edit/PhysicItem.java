package ru.grizzly_jr.level_edit;

import java.util.ArrayList;
import java.util.List;

public class PhysicItem {

private List<ShapePolygon> polygonList;
private List<ShapeCircle> circleList;





public PhysicItem() {
	super();
	polygonList= new ArrayList<ShapePolygon>();
	circleList = new ArrayList<ShapeCircle>();
}

public List<ShapePolygon> getPolygonList() {
	return polygonList;
}
public void setPolygonList(List<ShapePolygon> polygonList) {
	this.polygonList = polygonList;
}
public List<ShapeCircle> getCircleList() {
	return circleList;
}
public void setCircleList(List<ShapeCircle> circleList) {
	this.circleList = circleList;
}
	

	
}
