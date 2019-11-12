package com.stthomas.swan9854.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.stthomas.swan9854.core.Keybinds;
import com.stthomas.swan9854.core.RecipeCache;
import com.stthomas.swan9854.craftable.Duple;
import com.stthomas.swan9854.filter.OreDictionaryFilter;
import com.stthomas.swan9854.gui.GuiInit;
import com.stthomas.swan9854.gui.GuiPlanner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;
import scala.collection.mutable.HashTable;

public class EventHandlerItem {
		private boolean pressed;
		private int count;
	    @SubscribeEvent
	    public void onKeyInput(GuiScreenEvent.KeyboardInputEvent.Pre event)
	    {	
	        if (!pressed & Keybinds.useINV.getKeyCode() == Keyboard.getEventKey() & Minecraft.getMinecraft().currentScreen instanceof GuiContainer)
	        {
	        	//clears the guiBuffer after every 'keybind' press
	        	//TODO: remove hardcoded implementations in this method
	        	pressed = true;
	            GuiScreen screen = Minecraft.getMinecraft().currentScreen;
	
	            Container container = ((GuiContainer) screen).inventorySlots;
	            List<Slot> slots = container.inventorySlots;
	            
	            Minecraft mc = Minecraft.getMinecraft();
        		
        		ScaledResolution sr = new ScaledResolution(mc);
	            
	            final int width = sr.getScaledWidth();
				final int height = sr.getScaledHeight();
				
				final int guiLeft = (width - 176) / 2;
				final int guiTop = (height - 166) / 2;
				
        		int mouseX = (Mouse.getX() * width / mc.displayWidth) - guiLeft;
			    int mouseY = (height - Mouse.getY() * height / mc.displayHeight - 1) - guiTop;

    			
	            for(int i = 0; slots.size() > i; i++)
	            {
	            	if(!slots.get(i).getStack().getDisplayName().equals("Air"))
	            	{
	            		Slot slot = slots.get(i);
            			if(mouseX > slot.xPos & mouseY > slot.yPos & mouseX < slot.xPos + 18 & mouseY < slot.yPos + 18)
            			{      			
                			GuiInit.initialize(slot.getStack(), 1);            			
            			}
	            	}	            		
	            }
	        }	    
	        //removed pressed restriction
	        if(!Keyboard.getEventKeyState())
	    		pressed = false;
	    }		
}


