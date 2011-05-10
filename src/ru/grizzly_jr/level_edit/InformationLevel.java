package ru.grizzly_jr.level_edit;

import java.util.ArrayList;
import java.util.List;


public class InformationLevel {
	public String background;
	public List<ModelItem> models = new ArrayList<ModelItem>();
	public int max_count_bullet = 0;
	public double max_time = 0;
	public PointD rabbit_gun = new PointD(0,0);
	public PointD rabbit_bag = new PointD(0,0);
}
