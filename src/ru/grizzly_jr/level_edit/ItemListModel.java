package ru.grizzly_jr.level_edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

public class ItemListModel extends AbstractListModel
{
	private static final long serialVersionUID = 1L;
	
	private List<ModelItem> objects = new ArrayList<ModelItem>();
	
	public void add(ModelItem obj)
	{
		objects.add(obj);
	}
	
	public void remove(ModelItem obj)
	{
		objects.remove(obj);
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