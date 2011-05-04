package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import ru.grizzly_jr.level_edit.EditorPanel.Resolution;

public class Form extends JFrame {
	private class ListenerAddOnEdit implements ComponentsPanel.ListenerAdd
	{
		@Override
		public void add(MasterItem model) {
			addModel(model);
		}
	}
	
	private static final long serialVersionUID = 1L;
	private EditorPanel editor_panel = new EditorPanel();
	private InformationPanel info_panel = new InformationPanel();
	private ComponentsPanel components_panel = new ComponentsPanel();
	private JMenuBar menubar = new JMenuBar();
	
	public Form()
	{
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(800, 600);
		
		addModel(new ModelItem("apple",0.5,3.0));
		addModel(new ModelItem("can",0,0));
		addModel(new ModelItem("can",0.1,2.3));
		addModel(new ModelItem("apple",1.5,5.0));
		
		editor_panel.load("data/back.png");

		components_panel.addListener(new ListenerAddOnEdit());
		
		initMenuBar();
		this.setJMenuBar(menubar);
		
		this.add(info_panel,BorderLayout.WEST);
		this.add(editor_panel,BorderLayout.CENTER);
		this.add(components_panel,BorderLayout.EAST);
	}
	
	private void addModel(MasterItem master)
	{
		ModelItem model = new ModelItem(master);
		editor_panel.addCenterItem(model);
		info_panel.addItem(model);
	}
	
	private void addModel(ModelItem model)
	{
		editor_panel.addItem(model);
		info_panel.addItem(model);
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
		
		//setting
		JMenuItem fullI = new JMenuItem("FULL");
		JMenuItem ipadI = new JMenuItem("IPAD");
		JMenuItem ipodI = new JMenuItem("IPOD");
		JMenuItem iphoneI = new JMenuItem("IPHONE");
		fullI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.setResolution(Resolution.FULL);
			}});
		ipadI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.setResolution(Resolution.IPAD);
			}});
		ipodI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.setResolution(Resolution.IPOD);
			}});
		iphoneI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.setResolution(Resolution.IPHONE2);
			}});
		
		setting.add(fullI);
		setting.add(ipadI);
		setting.add(ipodI);
		setting.add(iphoneI);
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
		System.exit(0);
	}
	
}
