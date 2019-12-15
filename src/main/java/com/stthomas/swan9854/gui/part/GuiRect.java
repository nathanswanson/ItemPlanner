package com.stthomas.swan9854.gui.part;

public class GuiRect implements IGuiPart {
	private int left;
	private int right;
	private int thickness;
	
	public GuiRect(int left, int right, int thickness)
	{
		this.left = left;
		this.right = right;
		this.thickness = thickness;
	}
	@Override
	public GuiRect getPart() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public int getLeft()
	{
		return this.left;
	}
	

	
	public int getRight()
	{
		return this.right;
	}
	
	public int getThickness()
	{
		return this.thickness;
	}

}
