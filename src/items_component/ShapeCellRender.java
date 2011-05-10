package items_component;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class ShapeCellRender  implements ListCellRenderer
{
	@Override
	public Component getListCellRendererComponent(JList list, Object obj,
			int index, boolean selected, boolean isFocus) {
		ListModel list_model = list.getModel();
		if( list_model instanceof ShapeListModel){
			((ShapeListModel) list_model).select(index,selected);
		}
		
		if( obj instanceof Shape)
		{
			Shape shape = (Shape)obj;
			ShapeComponent comp = new ShapeComponent(shape); 
			comp.setFocusable(isFocus);
			comp.select(selected);
			return comp;
		}
		
		return null;
	}
	
}