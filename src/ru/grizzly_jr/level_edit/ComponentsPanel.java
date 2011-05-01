package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class ComponentsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static class Element
	{
		public ItemListModel list_model = new ItemListModel();
		public JList list = new JList(list_model);
		public String name;
		public Element(String name){
			this.name = name;
		} 
	}
	
	private List<Element> elements = new ArrayList<Element>();
	
	private JTabbedPane tabbed = new JTabbedPane();
	private JButton add_button = new JButton("add");
	private JButton edit_button = new JButton("edit");
	private JButton remove_button = new JButton("delete");
	
	public ComponentsPanel()
	{
		super(new BorderLayout());
		setPreferredSize(new Dimension(240,480));
		
		elements.add(new Element("box"));
		elements.add(new Element("test2"));
		elements.add(new Element("gg"));
		
		for( Element iter: elements){
			JScrollPane scrollpane = new JScrollPane(iter.list);
			scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			tabbed.addTab(iter.name, scrollpane);
			iter.list.setCellRenderer(new ItemCellRender());
			
			iter.list_model.add(new ModelItem("test1"));
			iter.list_model.add(new ModelItem("test2"));
			iter.list_model.add(new ModelItem("test3"));
			iter.list_model.add(new ModelItem("test4"));
		}
		
		add_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onAdd();
			}});
		edit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onEdit();
			}});
		remove_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onRemove();
			}});
		JPanel panel = new JPanel(new GridLayout(1,3));
		panel.add(add_button);
		panel.add(edit_button);
		panel.add(remove_button);
		
		this.add(tabbed, BorderLayout.CENTER);
		this.add(panel,BorderLayout.SOUTH);
	}
	
	private void onAdd()
	{
		
	}
	
	private void onEdit()
	{
		
	}
	
	private void onRemove()
	{
		
	}
}
