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
		//error
		
		Duple other = (Duple) t;
		return ItemStack.areItemStacksEqual(this.getStack(), other.getStack()) && other.getPhase() == this.getPhase();
	}
	
	@Override
	public int hashCode()
	{
		return this.getPhase() + this.getStack().getItem().hashCode();
	}
	
	
}
