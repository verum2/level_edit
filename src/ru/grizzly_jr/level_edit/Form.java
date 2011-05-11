package ru.grizzly_jr.level_edit;

import items_component.ComponentsPanel;
import items_component.MasterItem;
import items_component.MasterItem.TypeItem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import ru.grizzly_jr.level_edit.EditorPanel.Resolution;
import ru.grizzly_jr.level_edit.LoadSave.Load;
import ru.grizzly_jr.level_edit.LoadSave.Save;

public class Form extends JFrame {
	private class ListenerAddOnEdit implements ComponentsPanel.ListenerAdd
	{
		@Override
		public void add(MasterItem master) {
			ModelItem model = null;
			if( TypeItem.SHELF == master.getType()){
				model = new ShelfItem(master);
			}
			if( TypeItem.PHYSIC == master.getType()){
				model = new ModelItem(master);
			}
			editor_panel.addCenterItem(model);
		}
	}
	
	private static final long serialVersionUID = 1L;
	private InformationPanel info_panel = new InformationPanel();
	private EditorPanel editor_panel = new EditorPanel(info_panel);
	private ComponentsPanel components_panel = new ComponentsPanel();
	private JMenuBar menubar = new JMenuBar();
	private JTextField max_count_bullet = new JTextField("0");
	private JTextField max_time = new JTextField("0.0");
	
	public Form()
	{
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(800, 600);

		components_panel.addListener(new ListenerAddOnEdit());
		
		onLoadModel();		
		
		initMenuBar();
		this.setJMenuBar(menubar);
		
		JPanel south = new JPanel(new GridLayout(2, 2));
		south.add(new JLabel("max count bullet:"));
		south.add(max_count_bullet);
		
		south.add(new JLabel("max time:"));
		south.add(max_time);
		
		this.add(info_panel,BorderLayout.WEST);
		this.add(editor_panel,BorderLayout.CENTER);
		this.add(components_panel,BorderLayout.EAST);
		this.add(south,BorderLayout.SOUTH);
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
		JMenuItem saveLI = new JMenuItem("Save level");
		JMenuItem saveMI = new JMenuItem("Save model");
		JMenuItem loadI = new JMenuItem("Load level");
		JMenuItem exitI = new JMenuItem("Exit");
		newI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onNew();
			}});
		saveLI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSaveLevel();
			}});
		saveMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSaveModel();
			}});
		loadI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onLoadLevel();
			}});
		exitI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onExit();
			}});
		
		file.add(newI);
		file.add(saveLI);
		file.add(saveMI);
		file.add(loadI);
		file.add(new JSeparator());
		file.add(exitI);
		
		//setting
		JMenuItem fullI = new JMenuItem("FULL");
		JMenuItem ipadI = new JMenuItem("IPAD");
		JMenuItem iphoneI = new JMenuItem("IPHONE");
		JMenuItem enable_disable_shaped = new JMenuItem("enable/disable shaped");
		fullI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.setResolution(Resolution.FULL);
			}});
		ipadI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.setResolution(Resolution.IPAD);
			}});
		iphoneI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.setResolution(Resolution.IPHONE1);
			}});
		enable_disable_shaped.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editor_panel.reverseShaped();
			}});
		
		setting.add(fullI);
		setting.add(ipadI);
		setting.add(iphoneI);
		setting.add(enable_disable_shaped);
	}
	
	private void onNew()
	{		
		String name = (String)JOptionPane.showInputDialog(
                this,
                "Write name background:\n",
                "Create level",
                JOptionPane.PLAIN_MESSAGE,
                null,null,null);

		if( null == name)
			return;
		
		InformationLevel info_level = new InformationLevel();
		info_level.background = "data/"+name+".png";
		editor_panel.setInformation(info_level);
		
		repaint();
	}
	
	private void onSaveLevel()
	{
		String name = (String)JOptionPane.showInputDialog(
                this,
                "Write name level:\n",
                "save level",
                JOptionPane.PLAIN_MESSAGE,
                null,null,null);

		if( null == name)
			return;
		
		Save.save(name, editor_panel.getInformation());
	}
	
	private void onSaveModel()
	{
		Save.save("items", components_panel.getInformation());
	}
	
	private void onLoadLevel()
	{
		String name = (String)JOptionPane.showInputDialog(
                this,
                "Write name level:\n",
                "load level",
                JOptionPane.PLAIN_MESSAGE,
                null,null,null);

		if( null == name)
			return;
		
		/*InformationLevel info_level = new InformationLevel();
		info_level.background = "data/back.png";
		
		List<MasterItem> ms = components_panel.getMasterItems();
		info_level.models.add(new ModelItem("apple",ms,0.5,3.0));
		info_level.models.add(new ModelItem("can",ms,0,0));
		info_level.models.add(new ModelItem("can",ms,0.1,2.3));
		info_level.models.add(new ModelItem("apple",ms,1.5,5.0));*/
		
		editor_panel.setInformation(Load.loadLevel(name));
		repaint();
	}
	
	private void onLoadModel()
	{
		/*List<InformationModel.Element> list = new ArrayList<InformationModel.Element>();
		InformationModel.Element element1 = new InformationModel.Element();
		InformationModel.Element element2 = new InformationModel.Element();
		element1.name = "fruit";
		element2.name = "box";
		element1.items.add( new MasterItem("apple"));
		element2.items.add( new MasterItem("can"));
		list.add(element1);
		list.add(element2);
		
		components_panel.setInformation(new InformationModel(list));*/
		components_panel.setInformation(Load.loadModel("items"));
	}
	
	private void onExit()
	{
		System.exit(0);
	}
	
}
