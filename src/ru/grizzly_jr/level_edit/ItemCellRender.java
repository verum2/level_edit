package ru.grizzly_jr.level_edit;

import items_component.ItemListMasterModel;
import items_component.MasterItem;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class ItemCellRender implements ListCellRenderer
{
	@Override
	public Component getListCellRendererComponent(JList list, Object obj,
			int index, boolean selected, boolean isFocus) {
		ListModel list_model = list.getModel();
		if( list_model instanceof ItemListModel){
			((ItemListModel) list_model).select(index,selected);
		}
		if( list_model instanceof ItemListMasterModel){
			((ItemListMasterModel) list_model).select(index,selected);
		}
		
		if( obj instanceof ModelItem)
		{
			ModelItem model = (ModelItem)obj;
			ItemComponent item = new ItemComponent(model); 
			item.setFocusable(isFocus);
			item.select(selected);
			return item;
		}
		
		if( obj instanceof MasterItem)
		{
			MasterItem master = (MasterItem)obj;
			ItemComponent item = new ItemComponent(master); 
			item.setFocusable(isFocus);
			item.select(selected);
			return item;
		}
		return null;
	}
	
}