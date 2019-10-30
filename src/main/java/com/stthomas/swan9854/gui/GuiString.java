package com.stthomas.swan9854.gui;

public class GuiString implements IGuiPart {
	private String list;
	
	public GuiString(String list)
	{
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
}
