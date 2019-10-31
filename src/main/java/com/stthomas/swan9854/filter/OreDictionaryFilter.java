package com.stthomas.swan9854.filter;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryFilter extends ObjectFilter {
	
	public static File file = Minecraft.getMinecraft().mcDataDir;
	@Override
	public boolean accept(ItemStack stack)
	{
		//TODO: implementation of text file that lists filters (editable)
		return OreDictionary.getOreName(OreDictionary.getOreIDs(stack)[0]).equals("forgeHammer");
	}
}
