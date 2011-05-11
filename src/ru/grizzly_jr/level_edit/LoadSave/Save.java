package ru.grizzly_jr.level_edit.LoadSave;

import items_component.MasterItem;
import items_component.PhysicItem;
import items_component.Shape;
import items_component.ShapeCircle;
import items_component.ShapePolygon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.grizzly_jr.level_edit.InformationLevel;
import ru.grizzly_jr.level_edit.InformationModel;
import ru.grizzly_jr.level_edit.InformationModel.Element;
import ru.grizzly_jr.level_edit.ModelItem;
import ru.grizzly_jr.level_edit.PointD;
import xmlwise.*;

public class Save {
	
	public static void save(String name,InformationLevel info)
	{
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("rabbit gun", save(info.rabbit_gun));
		map.put("rabbit bag", save(info.rabbit_bag));
		map.put("max count bullet", info.max_count_bullet);
		map.put("max time", info.max_time);
		map.put("background", info.background);
		map.put("items", save(info.models));
		
		try {
			Plist.store(map, new File("data/"+name+".level"));
		} catch (IOException e) {
		}
	}
	
	public static void save(String name,InformationModel info)
	{
		info.update();
		if( null == info.getElement())
			return;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("items", save(info));
		
		try {
			Plist.store(map, new File("data/"+name+".items"));
		} catch (IOException e) {
		}
	}
	
	private static Object save(PointD p)
	{
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("x", p.getX());
		result.put("y", p.getY());
		return result;
	}
	private static List<Object> save(List<ModelItem> list)
	{
		List<Object> result = new ArrayList<Object>();
		for(ModelItem iter: list){
			result.add(save(iter));
		}
		return result;
	}
	
	private static Object save(ModelItem item)
	{
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("position", save(new PointD(item.x,item.y)));
		result.put("id",item.getMaster().name);
		
		return result;
	}
	
	private static Object save(InformationModel info)
	{
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		do{
			Element element = info.getElement();
			for( MasterItem iter: element.items)
			{
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("tabbed name", element.name);
				map.put("id", iter.name);
				map.put("physic", save(iter.physic));
				
				result.add(map);
			}
		}while(info.next());
		
		return result;
	}
	
	private static Object save(PhysicItem physic)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("linear damping", physic.linear_damping);
		map.put("angular damping", physic.angular_damping);
		map.put("is bullet", physic.isBullet);
		
		List<Object> shapes = new ArrayList<Object>();
		for( Shape iter: physic.getShapes())
		{
			shapes.add(save(iter));
		}
		map.put("shapes", shapes);
		return map;
	}
	
	private static Object save(Shape shape)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("friction", shape.getFriction());
		map.put("restitution", shape.getSpring());
		map.put("dencity", shape.getDencity());
		if( shape instanceof ShapeCircle)
		{
			map.put("type", "circle");
			ShapeCircle circle = (ShapeCircle)shape;
			map.put("radius", circle.getRadius());
			map.put("position", save(circle.getCenter()));
		}
		if( shape instanceof ShapePolygon)
		{
			map.put("type", "polygon");
			ShapePolygon poly = (ShapePolygon)shape;
			List<Object> points = new ArrayList<Object>();
			for( int i = 0; i < poly.getPointCount(); i++)
			{
				points.add(save(poly.getPoint(i)));
			}
			
			map.put("points", points);
		}
		
		return map;
	}
}
