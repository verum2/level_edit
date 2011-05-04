package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InformationPanel extends JPanel implements ModelItem.isDelete {

	private static final long serialVersionUID = 1L;
	
	private ItemListModel list_model = new ItemListModel();
	private JList list = new JList(list_model);
	private JButton edit_button = new JButton("edit");
	private JButton remove_button = new JButton("delete");
	
	public InformationPanel()
	{
		super(new BorderLayout());
		setPreferredSize(new Dimension(180,480));
		
		JScrollPane scrollpane = new JScrollPane(list);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollpane, BorderLayout.CENTER);
		list.setCellRenderer(new ItemCellRender());
		
		ModelItem.addListenerDelete(this);
		
		edit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				list_model.onEdit();
			}});
		remove_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				list_model.onRemove();
			}});
		
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(edit_button);
		panel.add(remove_button);
		this.add(panel,BorderLayout.SOUTH);
	}
	
	public void setItems(List<ModelItem> list)
	{
		list_model.clear();
		for( ModelItem iter: list){
			list_model.add(iter);
		}
		this.list.setCellRenderer(new ItemCellRender());
		repaint();
	}
	
	public void addItem(ModelItem item)
	{
		list_model.add(item);
		list.setCellRenderer(new ItemCellRender());
		repaint();
	}
	
	public void removeItem(ModelItem item)
	{
		list_model.remove(item);
		list.setCellRenderer(new ItemCellRender());
		repaint();
	}

	@Override
	public void delete(ModelItem model) {
		removeItem(model);
	}
}
