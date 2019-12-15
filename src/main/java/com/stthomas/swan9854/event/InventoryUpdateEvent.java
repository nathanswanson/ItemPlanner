package com.stthomas.swan9854.event;

import com.stthomas.swan9854.gui.GuiPlanner;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InventoryUpdateEvent
{
	@SubscribeEvent
	public void invChange(LivingUpdateEvent e)
	{
		if(GuiPlanner.isInitialized())
		{
			if(e.getEntity() instanceof EntityPlayer)
			{
				GuiPlanner.updateInventory();
			}
		}
	}
}
