package com.stthomas.swan9854.gui.part;

public class GuiLine implements IGuiPart {
	private int start;
	private int end; 
	private String color;
	public GuiLine(int start, int end, String color)
	{
        if(color.length() != 6)
        	throw new IllegalArgumentException();
        this.color = color;
        this.start = start;
        this.end = end;
	}
	
	@Override
	public GuiLine getPart() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public String getColor()
	{
		return this.color;
	}
	
	public int getStart()
	{
		return this.start;
	}
	
	public int getEnd()
	{
		return this.end;
	}

}
