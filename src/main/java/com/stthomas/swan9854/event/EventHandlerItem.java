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
		private HashMap<Item, Integer> stackOverload;
		private static HashMap<Duple, Integer> h;
	    @SubscribeEvent
	    public void onKeyInput(GuiScreenEvent.KeyboardInputEvent.Pre event)
	    {
	    	
	        if (!pressed & Keybinds.useINV.getKeyCode() == Keyboard.getEventKey() & Minecraft.getMinecraft().currentScreen instanceof GuiContainer)
	        {
	        	//clears the guiBuffer after every 'keybind' press
	        	GuiPlanner.initialize();
	        	h = new HashMap<Duple, Integer>();
	        	stackOverload = new HashMap<Item, Integer>();
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
            				
            				
                			recursiveRecipes(0, slot.getStack());
                			if(h != null)
                				GuiPlanner.instance().convertToGui(h);
            			}
	            	}
	            		
	            }
	        }
	        
	        //removed pressed restriction
	        if(!Keyboard.getEventKeyState())
	    		pressed = false;
	        	//GuiPlanner.destroy();
	    }

		
		private void recursiveRecipes(int index, ItemStack itemStack)
		{
			//TODO: check if recipe is in infinite loop
			//step one find recipe
			IRecipe[] recipes = RecipeCache.instance().matchListRecipes();
			 
			for(IRecipe recipe: recipes)
			{
				if(index > 4)
					break;
				//if recipe is found
				if(itemStack == null)
					break;
					
				if(recipe.getRecipeOutput().getDisplayName().equals(itemStack.getDisplayName()))
		    	{
					ItemStack[] stacks = new ItemStack[9];
		    		for(int l = 0; l < recipe.getIngredients().size(); l++)
		    		{
		    			if(recipe.getIngredients().get(l).getMatchingStacks().length > 0)
		    				stacks[l] = recipe.getIngredients().get(l).getMatchingStacks()[0];
		    		}
					if(stackNeedsFill(recipe, itemStack))
					{
						for(int i = 0; i < 9;i++)
						{
							if(itemStack.getMaxItemUseDuration() != 0)
								break;
							
							if(OreDictionary.getOreIDs(itemStack).length > 0 && new OreDictionaryFilter().accept(itemStack))
								break;
							
						
						
							recursiveRecipes(index+1, stacks[i]);
						}
		    		}
		    	}
			}
			
			if(itemStack != null)
			{
				Duple input = new Duple(itemStack, index); 
				if(itemStack != null && h.containsKey(new Duple(itemStack, index)))
				{
					h.put(input, h.get(input) + 1);
				}
				else if(itemStack != null)
				{
					h.put(input, 1);
				}
			}
			
		}


		private boolean stackNeedsFill(IRecipe recipe, ItemStack itemStack) {
			// if it does not exist yet it needs to be filled and created
			
			if(!stackOverload.containsKey(itemStack.getItem()))
			{
				stackOverload.put(itemStack.getItem(), recipe.getRecipeOutput().getCount());
				return true;
			}
			else if(stackOverload.get(itemStack.getItem()) == 0)
			{
				stackOverload.put(itemStack.getItem(), recipe.getRecipeOutput().getCount());
				return true;
			}
			else
			{
				stackOverload.put(itemStack.getItem(), stackOverload.get(itemStack.getItem()) - 1);
				return false;
			}
		}
		
		public static HashMap<Duple, Integer> getStack()
		{
			return h;
		}
		
}
