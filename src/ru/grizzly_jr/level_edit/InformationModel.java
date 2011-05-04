package ru.grizzly_jr.level_edit;

import java.util.List;

public class InformationModel {
	public static class Element
	{
		public String name;
		public List<MasterItem> items;
	};
	
	private List<Element> elements;
	private int index = 0;
	
	public InformationModel(List<Element> elements){
		this.elements = elements;
	}
	
	public void update()
	{
		index = 0;
	}
	
	public Element getElement()
	{
		return elements.get(index);
	}
	
	public boolean next(){
		index++;
		if( index >= elements.size()){
			index = elements.size()-1;
			return false;
		}
		return true;
	}
	
	public boolean prev(){
		index--;
		if( index < 0){
			index = 0;
			return false;
		}
		return true;
	}
}
