package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class ComponentsPanel extends JPanel {
	public interface ListenerAdd
	{
		public void add(MasterItem model);
	};

	private static final long serialVersionUID = 1L;
	
	private static class Element
	{
		public ItemListMasterModel list_model = new ItemListMasterModel();
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
	private JButton add_on_edit_button = new JButton("add on edit");
	
	private List<ListenerAdd> listenersAdd = new ArrayList<ListenerAdd>();
	
	private JFrame parent;
	
	public ComponentsPanel(JFrame parent)
	{
		super(new BorderLayout());
		this.parent = parent;
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
			

			iter.list_model.add(new MasterItem("apple"));
			iter.list_model.add(new MasterItem("can"));
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
		
		add_on_edit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onAddEdit();
			}});

		GridBagConstraints c = new GridBagConstraints();
		JPanel panel = new JPanel(new GridBagLayout());
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(add_button,c);
		
		c.gridx = 1;
		c.gridy = 0;
		panel.add(edit_button,c);
		
		c.gridx = 2;
		c.gridy = 0;
		panel.add(remove_button,c);
		
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(add_on_edit_button,c);
		
		this.add(tabbed, BorderLayout.CENTER);
		this.add(panel,BorderLayout.SOUTH);
	}
	
	public void addItem(MasterItem item,int index)
	{
		if( index < 0 || index >= elements.size())
			return;
		
		Element el = elements.get(index);
		el.list_model.add(item);
		el.list.setCellRenderer(new ItemCellRender());
		repaint();
	}
	
	public void removeItem(MasterItem item,int index)
	{
		if( index < 0 || index >= elements.size())
			return;
		
		Element el = elements.get(index);
		el.list_model.remove(item);
		el.list.setCellRenderer(new ItemCellRender());
		repaint();
	}
	
	public void set(InformationModel model)
	{
		elements.clear();
		tabbed.removeAll();
		do{
			InformationModel.Element it = model.getElement();
			Element el = new Element(it.name);
			elements.add(el);
			
			JScrollPane scrollpane = new JScrollPane(el.list);
			scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			tabbed.addTab(el.name, scrollpane);
			el.list.setCellRenderer(new ItemCellRender());
			for( MasterItem item: it.items){
				el.list_model.add(item);
			}
			
		}while(model.next());
	}
	
	public InformationModel getInfo()
	{
		List<InformationModel.Element> list = new ArrayList<InformationModel.Element>();
		for( Element el:elements)
		{
			InformationModel.Element element = new InformationModel.Element();
			element.name = el.name;
			element.items = el.list_model.getItems();
			list.add(element);
		}
		return new InformationModel(list);
	}
	
	public void addListener(ListenerAdd listener)
	{
		listenersAdd.add(listener);
	}
	
	public void removeListener(ListenerAdd listener)
	{
		listenersAdd.remove(listener);
	}
	
	private void onAdd()
	{
		ObjectForm form = new ObjectForm(parent);
		form.setVisible(true);
		addItem(form.masterItem,0);
	}
	
	private void onEdit()
	{
		
	}
	
	private void onRemove()
	{
		for( Element el: elements){
			el.list_model.onRemove();
		}
		for( Element el: elements){
			el.list.setCellRenderer(new ItemCellRender());
		}
		repaint();
	}
	
	private void onAddEdit()
	{
		for( Element el: elements){
			for(MasterItem model: el.list_model.getSelectItem()){
				for(ListenerAdd listener: listenersAdd){
					listener.add(model);
				}
			}
		}
	}
}
