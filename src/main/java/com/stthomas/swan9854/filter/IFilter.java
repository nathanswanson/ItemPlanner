package com.stthomas.swan9854.filter;

import net.minecraft.item.ItemStack;

public interface IFilter {
	//TODO: accept furnace and modded crafts
	public boolean accept(ItemStack stack);
}
