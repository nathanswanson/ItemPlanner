package com.stthomas.swan9854.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.stthomas.swan9854.craftable.Duple;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiPlanner {
	
	private ArrayList<IGuiPart> parts;
	private static GuiPlanner instance;
		
	public static void initialize()
	{
		instance = new GuiPlanner();
		instance.parts = new ArrayList<IGuiPart>();

	}
	
	public static GuiPlanner instance()
	{
		if(instance == null)
			instance = new GuiPlanner();
		return instance;
	}
	
	public static void destroy()
	{
		instance = null;
	}
	
	public IGuiPart[] getPart()
	{
		return parts.toArray(new IGuiPart[0]);
	}
	
	public static boolean isInitialized()
	{
		return instance != null;
	}
	
	public void convertToGui(HashMap<Duple, Integer> h) {
		// TODO Auto-generated method stub
		Set<Duple> dupleSet = h.keySet();
		int max = 0; 
		for(Duple duple: dupleSet)
		{
			if(duple.getPhase() > max)
			{
				max = duple.getPhase();
			}
		}
		
		Minecraft.getMinecraft().player.sendChatMessage(""+max);
		
		for(int i = 0; i <= max; i++)
		{
			parts.add(new GuiString("Phase: " + (max - i + 1)));
			parts.add(new GuiLine(5,100,"ffffff"));
			
			GuiPart section = new GuiPart();
			for(Duple duple: dupleSet)
			{
				if(duple.getPhase() == i)
				{
					Duple itemAndCount = new Duple(duple.getStack(), h.get(duple));
					section.add(itemAndCount);		
				}
			}
			parts.add(section);
			parts.add(new GuiString(""));
		}
		
	}
	
}
