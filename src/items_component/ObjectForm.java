package items_component;

import items_component.PaintSheet.DrawShapeType;
import items_component.ShapeComponent.RemoveListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class ObjectForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private PaintSheet sheet_panel;
	private JMenuBar menubar = new JMenuBar();
	
	private JPanel shapeInfo = new JPanel(new GridBagLayout());
	private MasterItem masterItem;
	
	private ComponentsPanel parent;
	
	private JTextField linear = null;
	private JTextField angular = null;
	private JTextField isBullet = null;
	
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
		
		shapeInfo.setBorder(BorderFactory.createTitledBorder("Options"));
		
		if( null != sheet_panel){
			this.remove(sheet_panel);
		}
		
		sheet_panel = new PaintSheet(masterItem);
		
		sheet_panel.addListener(new PaintSheet.ListenerActiveLine() {
			
			@Override
			public void NotActive() {
				reSizeShapeInfo();
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
		scrollpane.setPreferredSize(new Dimension(150,100));
		
		this.add(scrollpane,BorderLayout.EAST);
		
		JPanel south = new JPanel(new GridLayout(1,6));
		linear = new JTextField( String.valueOf(masterItem.physic.linear_damping) );
		angular = new JTextField( String.valueOf(masterItem.physic.angular_damping) );
		isBullet = new JTextField( String.valueOf(masterItem.physic.isBullet) );
		south.add(new JLabel("linear damping:"));
		south.add(linear);
		south.add(new JLabel("angular damping:"));
		south.add(angular);
		south.add(new JLabel("is bullet:"));
		south.add(isBullet);
		
		this.add(south,BorderLayout.SOUTH);
		
		reSizeShapeInfo();
	}
	
	private void reSizeShapeInfo()
	{
		shapeInfo.removeAll();
		
		shapeInfo.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		int i =0;
		for(Shape shape: masterItem.physic.getShapes())
		{
			c.gridy = i+1;
			final ShapeComponent comp = new ShapeComponent(shape);
			comp.addListener(new RemoveListener() {
				@Override
				public void remove() {
					for(Shape iter: masterItem.physic.getShapes()){
						if( comp.getShape() == iter){
							masterItem.physic.getShapes().remove(iter);
							break;
						}
					}
					sheet_panel.repaintImage();
					reSizeShapeInfo();
					ObjectForm.this.repaint();
				}
			});
			
			shapeInfo.add(comp,c);
			i++;
		}
		ObjectForm.this.validate();
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
		for(Component comp: shapeInfo.getComponents())
		{
			if( comp instanceof ShapeComponent)
			{
				((ShapeComponent)comp).recalculation();
			}
		}
		masterItem.physic.linear_damping = Double.valueOf(linear.getText());
		masterItem.physic.angular_damping = Double.valueOf(angular.getText());
		masterItem.physic.isBullet = Boolean.valueOf(isBullet.getText());
		
		parent.addMaster(this.masterItem);
		setVisible(false);
	}
	
	private void onExit()
	{
		setVisible(false);
	}
	
}
