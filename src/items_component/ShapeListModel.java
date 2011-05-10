package items_component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

public class ShapeListModel extends AbstractListModel
{
	private static final long serialVersionUID = 1L;
	
	private List<Shape> objects = new ArrayList<Shape>();
	private List<Boolean> select = new ArrayList<Boolean>();
	
	public void add(Shape obj)
	{
		objects.add(obj);
		select.add(false);
	}
	
	public void remove(Shape obj)
	{
		for( int i = 0; i < objects.size(); i++){
			if( obj == objects.get(i)){
				objects.remove(i);
				select.remove(i);
				break;
			}
		}
	}
	
	public List<Shape> getSelectItem()
	{
		List<Shape> result = new ArrayList<Shape>();
		for( int i =0; i< select.size(); i++){
			if( true == select.get(i)){
				result.add(objects.get(i));
			}
		}
		
		return result;
	}
	
	public void clear()
	{
		objects.clear();
		select.clear();
	}
	
	public void select(int index,boolean isSelect)
	{
		select.set(index, isSelect);
	}
	
	public void onRemove()
	{
		boolean exit = false;
		do{
			exit = true;
			for( int i =0; i< select.size(); i++){
				if( true == select.get(i)){
					remove(objects.get(i));
					exit = false;
					break;
				}
			}
		}while(!exit);
	}
	
	@Override
	public Object getElementAt(int index) {
		if( index < 0 || index >= objects.size())
			return null;
		return objects.get(index);
	}

	@Override
	public int getSize() {
		return objects.size();
	}
	
}