package ru.grizzly_jr.level_edit.LoadSave;

import items_component.MasterItem;
import items_component.MasterItem.TypeItem;
import items_component.PhysicItem;
import items_component.Shape;
import items_component.ShapeCircle;
import items_component.ShapePolygon;
import items_component.ShelfPhysicItem;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.grizzly_jr.level_edit.InformationLevel;
import ru.grizzly_jr.level_edit.InformationModel;
import ru.grizzly_jr.level_edit.InformationModel.Element;
import ru.grizzly_jr.level_edit.ModelItem;
import ru.grizzly_jr.level_edit.PointD;
import ru.grizzly_jr.level_edit.ShelfItem;
import xmlwise.Plist;
import xmlwise.XmlParseException;

public class Load {
	public static List<MasterItem> models = null;
	
	public static InformationLevel loadLevel(String name)
	{
		try {
			InformationLevel result = new InformationLevel();
			
			Map<String, Object> map = Plist.load(new File("data/"+name+".level"));
			result.rabbit_gun = loadPointD(map.get("rabbit gun"));
			result.rabbit_bag = loadPointD(map.get("rabbit bag"));
			result.max_count_bullet = (Integer)map.get("max count bullet");
			result.max_time = (Double)map.get("max time");
			result.background = (String)map.get("background");
			result.models = loadListModelItem(map.get("items"));
			
			return result;
		} catch (XmlParseException e) {
		} catch (IOException e) {
		}
		return null;
	}
	
	private static class LoadElement
	{
		public MasterItem item = null;
		public String tabbed_name = null;
	}
	
	public static InformationModel loadModel(String name)
	{
		try {
			List<Element> elements = new ArrayList<Element>();
			Map<String, Object> map = Plist.load(new File("data/"+name+".items"));
			
			@SuppressWarnings("unchecked")
			List<Object> items = (List<Object>)map.get("items");
			for( Object obj: items)
			{
				LoadElement element = loadElement(obj);
				boolean test = true;
				for( Element iter: elements)
				{
					if( element.tabbed_name.equals(iter.name)){
						test = false;
						iter.items.add(element.item);
					}
				}
				if( test){
					Element el = new Element();
					el.name = element.tabbed_name;
					el.items.add(element.item);
					elements.add(el);
				}
			}
			
			return new InformationModel(elements);
		} catch (XmlParseException e) {
		} catch (IOException e) {
		}
		
		return null;
	}
	
	private static LoadElement loadElement(Object obj)
	{
		LoadElement result = new LoadElement();
		
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String,Object>)obj;
		result.tabbed_name = (String)map.get("tabbed name");
		String type = (String)map.get("type");
		if( type.equals("physic")){
			result.item = new MasterItem((String)map.get("id"),TypeItem.PHYSIC);
			result.item.physic = loadPhysicItem(map);
		}
		if( type.equals("shelf")){
			result.item = new MasterItem((String)map.get("id"),TypeItem.SHELF);
			result.item.shelf = loadShelfItem(map);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static PhysicItem loadPhysicItem(Object obj)
	{
		Map<String,Object> map = (Map<String,Object>)obj;
		
		PhysicItem item = new PhysicItem();
		item.linear_damping = (Double)map.get("linear damping");
		item.angular_damping = (Double)map.get("angular damping");
		item.isBullet = (Boolean)map.get("is bullet");
		List<Shape> shapes = new ArrayList<Shape>();
		for( Object iter: (List<Object>)map.get("shapes"))
		{
			shapes.add(loadShape(iter));
		}
		item.setShapes(shapes);
		return item;
	}
	
	@SuppressWarnings("unchecked")
	private static ShelfPhysicItem loadShelfItem(Object obj)
	{
		Map<String,Object> map = (Map<String,Object>)obj;
		ShelfPhysicItem item = new ShelfPhysicItem();
		item.point1 = loadPointD( map.get("thread 1"));
		item.point2 = loadPointD( map.get("thread 2"));
		item.strength1 = (Double)map.get("strength thread 1");
		item.strength2 = (Double)map.get("strength thread 2");
		
		return item;
	}
	
	@SuppressWarnings("unchecked")
	private static Shape loadShape(Object obj)
	{
		Map<String,Object> map = (Map<String,Object>)obj;
		String type = (String)map.get("type");
		Shape result = null;
		
		int r = (int)(Math.random()*255);
		int g = (int)(Math.random()*255);
		int b = (int)(Math.random()*255);
		
		if( type.equals("circle")){
			PointD pos = loadPointD(map.get("position"));
			double radius = (Double)map.get("radius");
			result = new ShapeCircle(pos, radius, new Color(r,g,b));
		}
		if( type.equals("polygon")){
			result = new ShapePolygon(new Color(r,g,b));
			for( Object iter: (List<Object>)map.get("points"))
			{
				((ShapePolygon)result).addPoint(loadPointD(iter));
			}
		}
		if( null == result)
			return null;
		
		result.setFriction((Double)map.get("friction"));
		result.setSpring((Double)map.get("restitution"));
		result.setDencity((Double)map.get("dencity"));
		return result;
	}
	
	private static PointD loadPointD(Object obj)
	{
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)obj;
	
		double x = (Double)map.get("x");
		double y = (Double)map.get("y");
		return new PointD(x, y);
	}
	
	private static List<ModelItem> loadListModelItem(Object obj)
	{
		List<ModelItem> result = new ArrayList<ModelItem>();
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>)obj;
		
		for( Object iter: list)
		{
			result.add(loadModelItem(iter));
		}
		return result;
	}
	
	private static ModelItem loadModelItem(Object obj)
	{
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)obj;
		PointD pos = loadPointD(map.get("position"));
		String id = (String)map.get("id");
		
		MasterItem master = findMaster(id);
		if(TypeItem.SHELF == master.getType())
		{
			ShelfItem result = new ShelfItem(master,pos.getX(),pos.getY());
			result.point1 = loadPointD(map.get("thread 1"));
			result.point2 = loadPointD(map.get("thread 2"));
			return result;
		}
		return new ModelItem(master,pos.getX(),pos.getY());
	}
	
	private static MasterItem findMaster(String name)
	{
		for( MasterItem master: models){
			if( name.equals(master.name)){
				return master;
			}
		}
		return null;
	}
}