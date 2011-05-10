package items_component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import ru.grizzly_jr.level_edit.InformationModel;
import ru.grizzly_jr.level_edit.ItemCellRender;

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
	
	public ComponentsPanel()
	{
		super(new BorderLayout());
		setPreferredSize(new Dimension(240,480));
		
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
	
	public void addMaster(MasterItem master)
	{
		for( Element iter: elements){
			JScrollPane comp = (JScrollPane)tabbed.getSelectedComponent();
			if( comp.getViewport().getView() != iter.list)
				continue;
			
			for( MasterItem item: iter.list_model.getItem()){
				if( item == master)
					return;
			}
			
			iter.list_model.add(master);
			iter.list.setCellRenderer(new ItemCellRender());
			break;
		}
		repaint();
	}
	
	public void setInformation(InformationModel info)
	{
		info.update();
		if( null == info.getElement())
			return;
		
		elements.clear();
		do{
			InformationModel.Element iter = info.getElement();
			Element element = new Element(iter.name);
			elements.add(element);
			for( MasterItem item: iter.items){
				element.list_model.add(item);
			}
			
		}while(info.next());
		
		setViewComponent();
	}
	
	public InformationModel getInformation()
	{
		List<InformationModel.Element> list = new ArrayList<InformationModel.Element>();
		for( Element iter: elements)
		{
			InformationModel.Element element = new InformationModel.Element();
			element.name = iter.name;
			element.items = iter.list_model.getItem();
		}
		
		return new InformationModel(list);
	}
	
	public List<MasterItem> getMasterItems()
	{
		List<MasterItem> items = new ArrayList<MasterItem>();
		for( Element iter: elements)
		{
			for(MasterItem item: iter.list_model.getItem() )
			{
				items.add(item);
			}
		}
		return items;
	}
	
	private void setViewComponent()
	{
		tabbed.removeAll();
		for( Element iter: elements){
			JScrollPane scrollpane = new JScrollPane(iter.list);
			scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			tabbed.addTab(iter.name, scrollpane);
			iter.list.setCellRenderer(new ItemCellRender());
		}
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
		String name = (String)JOptionPane.showInputDialog(
		                    this,
		                    "Write name(id):\n",
		                    "Create item",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,null,null);
		
		if( null == name)
			return;
		
		ObjectForm form = new ObjectForm(this,name);
		form.setVisible(true);
	}
	
	private void onEdit()
	{
		for( Element el: elements){
			JScrollPane comp = (JScrollPane)tabbed.getSelectedComponent();
			if( comp.getViewport().getView() != el.list)
				continue;
			
			for(MasterItem model: el.list_model.getSelectItem()){
				ObjectForm form = new ObjectForm(this,model);
				form.setVisible(true);
				return;
			}
		}
	}
	
	private void onRemove()
	{
		String name = (String)JOptionPane.showInputDialog(
                this,
                "Write 'YES' if you delete items:\n",
                "delete items",
                JOptionPane.PLAIN_MESSAGE,
                null,null,null);

		if( null == name || !name.equals("YES"))
			return;
		
		for( Element el: elements){
			JScrollPane comp = (JScrollPane)tabbed.getSelectedComponent();
			if( comp.getViewport().getView() != el.list)
				continue;
			
			List<MasterItem> selected = el.list_model.getSelectItem(); 
			for(MasterItem model: selected){
				el.list_model.remove(model);
			}
			el.list.setCellRenderer(new ItemCellRender());
		}
	}
	
	private void onAddEdit()
	{
		for( Element el: elements){
			JScrollPane comp = (JScrollPane)tabbed.getSelectedComponent();
			if( comp.getViewport().getView() != el.list)
				continue;
			
			for(MasterItem model: el.list_model.getSelectItem()){
				for(ListenerAdd listener: listenersAdd){
					listener.add(model);
				}
			}
		}
	}
}
