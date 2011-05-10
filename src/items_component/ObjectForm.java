package items_component;

import items_component.PaintSheet.DrawShapeType;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;


public class ObjectForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private PaintSheet sheet_panel;
	private JMenuBar menubar = new JMenuBar();
	
	private ShapeListModel list_model = new ShapeListModel();
	private JList shapeInfo = new JList(list_model);
	private MasterItem masterItem;
	
	private ComponentsPanel parent;
	
	public ObjectForm(ComponentsPanel parent,String name)
	{
		super();
		this.parent = parent;
		
		masterItem = new MasterItem(name);
		init();
	}
	
	public ObjectForm(ComponentsPanel parent, MasterItem master)
	{
		super();
		this.parent = parent;
		
		this.masterItem = master;
		init();
	}
	
	private void init()
	{
		this.setLayout(new BorderLayout());
		this.setSize(600, 400);
		
		initMenuBar();
		this.setJMenuBar(menubar);
		
		JPanel east = new JPanel(new BorderLayout());
		
		shapeInfo.setBorder(BorderFactory.createTitledBorder("Options"));
		shapeInfo.setCellRenderer(new ShapeCellRender());
		
		if( null != sheet_panel){
			this.remove(sheet_panel);
		}
		
		sheet_panel = new PaintSheet(masterItem);
		
		sheet_panel.addListener(new PaintSheet.ListenerActiveLine() {
			
			@Override
			public void NotActive() {
				shapeInfo.removeAll();
				list_model.clear();
				
				for(Shape shape: masterItem.physic.getShapes())
				{
					list_model.add(shape);
				}
				
				shapeInfo.updateUI();
			}
			
			@Override
			public void Active() {
				// TODO Auto-generated method stub
			}
		});
		
		this.add(sheet_panel,BorderLayout.CENTER);
		
		JScrollPane scrollpane = new JScrollPane(shapeInfo);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		JButton delete = new JButton("remove");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				list_model.onRemove();
				shapeInfo.setCellRenderer(new ShapeCellRender());
			}
		});
		
		east.add(scrollpane,  BorderLayout.CENTER);
		east.add(delete,  BorderLayout.PAGE_END);
		
		this.add(east,BorderLayout.EAST);
	}
	
	private void initMenuBar()
	{
		JMenu file = new JMenu("File");
		JMenu setting = new JMenu("Setting");
		menubar.add(file);
		menubar.add(setting);
		
		//file
		JMenuItem saveI = new JMenuItem("Save");
		JMenuItem exitI = new JMenuItem("Exit");
		saveI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSave();
			}});
		exitI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onExit();
			}});
		
		file.add(saveI);
		file.add(new JSeparator());
		file.add(exitI);
		
		JMenuItem circleI = new JMenuItem("Circle");
		JMenuItem polygonI = new JMenuItem("Polygon");
		circleI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sheet_panel.setDrawShapeType(DrawShapeType.Circle);
			}});
		polygonI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sheet_panel.setDrawShapeType(DrawShapeType.Polygon);
			}});
		
		setting.add(circleI);
		setting.add(polygonI);
	}

	private void onSave()
	{
		parent.addMaster(this.masterItem);
		setVisible(false);
	}
	
	private void onExit()
	{
		setVisible(false);
	}
	
}
