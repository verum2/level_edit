package ru.grizzly_jr.level_edit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import ru.grizzly_jr.level_edit.EditorPanel.Resolution;
import ru.grizzly_jr.level_edit.InformationModel.Element;
import ru.grizzly_jr.level_edit.LoadSave.Save;

public class Form extends JFrame {
	private class ListenerAddOnEdit implements ComponentsPanel.ListenerAdd
	{
		@Override
		public void add(MasterItem model) {
			addModel(model);
		}
	}
	
	private static final long serialVersionUID = 1L;
	private EditorPanel editor_panel = null;
	private InformationPanel info_panel = null;
	private ComponentsPanel components_panel = null;
	private JMenuBar menubar = new JMenuBar();
	
	public Form()
	{
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(800, 600);
		
		initMenuBar();
		this.setJMenuBar(menubar);
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
		JMenuItem saveMI = new JMenuItem("Save model");
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
		saveMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSaveModel();
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
		file.add(saveMI);
		file.add(loadI);
		file.add(new JSeparator());
		file.add(exitI);
		
		//setting
		JMenuItem fullI = new JMenuItem("FULL");
		JMenuItem ipadI = new JMenuItem("IPAD");
		JMenuItem ipodI = new JMenuItem("IPOD");
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
		
		JMenuItem endiI = new JMenuItem("enable/disable shape");
		endiI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.setShaped(!editor_panel.isShaped());
			}});
		
		setting.add(fullI);
		setting.add(ipadI);
		setting.add(ipodI);
		setting.add(endiI);
	}
	
	private void onNew()
	{
		editor_panel = new EditorPanel();
		info_panel = new InformationPanel();
		components_panel = new ComponentsPanel(this);
		
		editor_panel.load("data/back.png");
		addModel(new ModelItem("apple",0.5,3.0));
		addModel(new ModelItem("can",0,0));
		addModel(new ModelItem("can",0.1,2.3));
		addModel(new ModelItem("apple",1.5,5.0));
		
		List<Element> elements = new ArrayList<Element>();
		
		Element el1 = new Element();
		el1.name = "frutis";
		el1.items = new ArrayList<MasterItem>();
		el1.items.add(new MasterItem("apple"));
		
		Element el2 = new Element();
		el2.name = "bottle";
		el2.items = new ArrayList<MasterItem>();
		el2.items.add(new MasterItem("can"));
		
		elements.add(el1);
		elements.add(el2);
	
		components_panel.addListener(new ListenerAddOnEdit());
		components_panel.set(new InformationModel(elements));
		
		this.add(info_panel,BorderLayout.WEST);
		this.add(editor_panel,BorderLayout.CENTER);
		this.add(components_panel,BorderLayout.EAST);
		this.validate();
	}
	
	private void onSave()
	{
		InformationLevel info = editor_panel.getInfo();
		Save.save("data/test.level", info);
	}
	
	private void onSaveModel()
	{
		InformationModel info = components_panel.getInfo();
		Save.save("data/test.model", info);
	}
	
	private void onLoad()
	{
	}
	
	private void onExit()
	{
		System.exit(0);
	}
	
}
