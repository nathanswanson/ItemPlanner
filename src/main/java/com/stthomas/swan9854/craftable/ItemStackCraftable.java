package com.stthomas.swan9854.craftable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class ItemStackCraftable {
	private ItemStack stack;
	private int index;
	private IRecipe recipe;
	
	public ItemStackCraftable(ItemStack stack, int index, IRecipe recipe)
	{
		this.recipe = recipe;
		this.index = index;
		this.recipe = recipe;
	}
	
}
