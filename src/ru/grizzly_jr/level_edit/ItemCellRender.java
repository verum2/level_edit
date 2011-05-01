package ru.grizzly_jr.level_edit;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ItemCellRender implements ListCellRenderer
{
	@Override
	public Component getListCellRendererComponent(JList list, Object obj,
			int index, boolean selected, boolean isFocus) {
		
		if( obj instanceof ModelItem)
		{
			ModelItem model = (ModelItem)obj;
			ItemComponent item = new ItemComponent(model); 
			item.setFocusable(isFocus);
			return item;
		}
		return null;
	}
	
}