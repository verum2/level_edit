package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class InformationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ItemListModel list_model = new ItemListModel();
	private JList list = new JList(list_model);
	
	public InformationPanel()
	{
		super(new BorderLayout());
		setPreferredSize(new Dimension(180-5,480));
		
		JScrollPane scrollpane = new JScrollPane(list);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollpane, BorderLayout.CENTER);
		list.setCellRenderer(new ItemCellRender());
		
		list_model.add(new ModelItem("test1"));
		list_model.add(new ModelItem("test2"));
		list_model.add(new ModelItem("test3"));
		list_model.add(new ModelItem("test4"));
	}
	
	public void addItem(ModelItem item)
	{
		list_model.add(item);
		repaint();
	}
	
	public void removeItem(ModelItem item)
	{
		list_model.remove(item);
		repaint();
	}
}
