package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class ObjectForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private PaintSheet sheet_panel;
	private JMenuBar menubar = new JMenuBar();
	private Box shapeInfo;
	public MasterItem masterItem;
	JPanel info_panel;
	
	public ObjectForm(JFrame parent)
	{
		super(parent);
		this.setLayout(new BorderLayout());
		this.setSize(600, 400);
		
		initMenuBar();
		this.setJMenuBar(menubar);
		
		info_panel = new JPanel();
		info_panel.setBorder(BorderFactory.createTitledBorder("Options"));
		info_panel.setPreferredSize(new Dimension(200, 200));
		shapeInfo = Box.createVerticalBox();
		
		info_panel.add(shapeInfo,BorderLayout.WEST);
		
		masterItem= new MasterItem("apple");
		sheet_panel = new PaintSheet(masterItem);
		
		sheet_panel.addListener(new PaintSheet.ListenerActiveLine() {
			
			@Override
			public void NotActive() {
				shapeInfo.removeAll();
				Integer shapeIndex = 1;
				for (ShapePolygon shapePolygon : masterItem.physic.getPolygonList()) {
					JLabel jLab = new JLabel("Shape "+shapeIndex.toString());
					shapeInfo.add(jLab);
					int pointCount = shapePolygon.getPointCount();
					
					for(int i=0;i<pointCount;i++)
					{
						Double x = shapePolygon.getPoint(i).getX();
						Double y = shapePolygon.getPoint(i).getY();
						String points = "("+x.toString()+","+y.toString()+")";
						jLab = new JLabel(points);
						shapeInfo.add(jLab);
					}
					shapeIndex++;
				}
				ObjectForm.this.info_panel.updateUI();
			}
			
			@Override
			public void Active() {
				// TODO Auto-generated method stub
			}
		});

		this.add(sheet_panel,BorderLayout.CENTER);
		this.add(info_panel,BorderLayout.EAST);	
	}
	
	private void initMenuBar()
	{
		JMenu file = new JMenu("File");
		JMenu setting = new JMenu("Setting");
		JMenu help = new JMenu("Help");
		menubar.add(file);
		menubar.add(setting);
		menubar.add(help);
		
		//file
		JMenuItem newI = new JMenuItem("New");
		JMenuItem saveI = new JMenuItem("Save");
		JMenuItem loadI = new JMenuItem("Load");
		JMenuItem exitI = new JMenuItem("Exit");
		newI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onNew();
			}});
		saveI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSave();
			}});
		loadI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onLoad();
			}});
		exitI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onExit();
			}});
		
		file.add(newI);
		file.add(saveI);
		file.add(loadI);
		file.add(new JSeparator());
		file.add(exitI);
	}
	
	private void onNew()
	{
	}
	
	private void onSave()
	{
	}
	
	private void onLoad()
	{
	}
	
	private void onExit()
	{
		setVisible(false);
	}
	
}
