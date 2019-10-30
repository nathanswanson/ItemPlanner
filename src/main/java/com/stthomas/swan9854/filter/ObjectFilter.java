package com.stthomas.swan9854.filter;

import net.minecraft.item.ItemStack;

public class ObjectFilter implements IFilter {

	@Override
	public boolean accept(ItemStack stack) {
		return true;
	}

}
