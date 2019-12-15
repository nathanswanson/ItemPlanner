package com.stthomas.swan9854.gui.part;

import java.util.ArrayList;

import com.stthomas.swan9854.craftable.Duple;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;

public class GuiPart implements IGuiPart {
	private ArrayList<Duple> list;
    
	public GuiPart(ArrayList<Duple> list)
	{
		this.list = list;
	}
	
	public GuiPart() {
		list = new ArrayList<Duple>();
	}

	public Duple[] getItem()
	{
		return list.toArray(new Duple[0]);
	}
	
	public GuiPart getPart()
	{
		return this;
	}
	
	public void add(Duple in)
	{
		list.add(in);
	}
}
