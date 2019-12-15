package com.stthomas.swan9854.gui.part;

public class GuiString implements IGuiPart {
	private String list;
	private String color;
	
	public GuiString(String list, String color)
	{
		this.color = color;
		this.list = list;
	}
	
	public GuiString(String list)
	{
		this.color = "ffffff";
		this.list = list;
	}
	
	public GuiString getPart()
	{
		return this;
	}
	
	public String getString()
	{
		return this.list;
	}
	
	public String getColor()
	{
		return this.color;
	}
}
