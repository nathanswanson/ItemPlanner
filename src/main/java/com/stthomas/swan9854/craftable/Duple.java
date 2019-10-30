package com.stthomas.swan9854.craftable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Duple {
	private ItemStack item;
	private int phase;
	
	public Duple(ItemStack item, int phase)
	{
		this.item = item;
		this.phase = phase;
	}
	
	public ItemStack getStack()
	{
		return item;
	}
	
	public int getPhase()
	{
		return phase;
	}
	
	public void setStack(ItemStack in)
	{
		this.item = in;
	}
	
	public void setPhase(int in)
	{
		this.phase = in;
	}
	
	@Override
	public String toString()
	{
		return "name: " + this.item.getDisplayName() + " Phase: " + this.phase;
	}
	
	@Override
	public boolean equals(Object t)
	{
		Duple other = (Duple) t;
		return this.getPhase() + this.getStack().hashCode() == 
				other.getPhase() + this.getStack().hashCode();
	}
	
	@Override
	public int hashCode()
	{
		return this.getPhase() + this.getStack().hashCode();
	}
	
	
}
