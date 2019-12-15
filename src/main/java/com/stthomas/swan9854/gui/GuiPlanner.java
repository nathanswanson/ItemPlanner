package com.stthomas.swan9854.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.stthomas.swan9854.core.RecipeCache;
import com.stthomas.swan9854.craftable.Duple;
import com.stthomas.swan9854.filter.OreDictionaryFilter;
import com.stthomas.swan9854.gui.part.GuiLine;
import com.stthomas.swan9854.gui.part.GuiPart;
import com.stthomas.swan9854.gui.part.GuiRect;
import com.stthomas.swan9854.gui.part.GuiString;
import com.stthomas.swan9854.gui.part.IGuiPart;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

public class GuiPlanner {
	
	private ArrayList<IGuiPart> parts;
	private static GuiPlanner instance;
	private HashMap<Duple, Integer> h;
	private HashMap<String, Integer> stackOverload;
	private HashMap<ItemStack, Integer> rawCount;
	
	private boolean ready;
	
	public static void initialize(ItemStack stack, int count)
	{
		instance = new GuiPlanner();
		instance.ready = false;
		instance.rawCount = new HashMap<ItemStack, Integer>();
		instance.parts = new ArrayList<IGuiPart>();
		instance.stackOverload = new HashMap<String, Integer>();
		instance.h = new HashMap<Duple, Integer>();
		
		for(int i = 0; i < count; i++)
			instance.recursiveRecipes(stack, 0);
		
		System.out.print(instance.rawCount);
		instance.convertToGui(instance.h);
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
	
	private void convertToGui(HashMap<Duple, Integer> h) {
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
		
		
		for(int i = 0; i <= max; i++)
		{
			parts.add(new GuiString("Phase: " + (max - i + 1), "FFAA00"));
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
			parts.add(new GuiString("","ffffff"));
		}
		
		parts.add(new GuiString("Materials","ff0000"));
		parts.add(new GuiLine(5,100, "ffffff"));
		GuiPart raw = new GuiPart();
		Set<ItemStack> keys = rawCount.keySet();
		for(ItemStack sets: keys)
		{
			Duple itemAndCount = new Duple(sets, rawCount.get(sets));
			raw.add(itemAndCount);
		}
		parts.add(raw);
		instance.ready = true;
	}
	
	private void recursiveRecipes(ItemStack itemStack, int index)
	{

		IRecipe recipe = grabRecipe(itemStack);
		if(itemStack != null & recipe != null)
		{
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
						
					    if(itemIsRecursive(itemStack))
					    	break;
					
						instance.recursiveRecipes(stacks[i], index+1);
					}
	    		}
	    	}
		}
		
		if(itemStack != null)
		{
			Duple input = new Duple(itemStack, index);
			
			if(itemStack != null)
			{
				if(recipe == null || itemIsRecursive(itemStack))
					rawCount.put(itemStack,  rawCount.get(itemStack) == null ? 1 : rawCount.get(itemStack) + 1);
				
				if(h.containsKey(input))
				{
					h.put(input, h.get(input) + 1);
				}
				else
				{
					h.put(input, 1);
				}
			}
		}	
	}
	
	private IRecipe grabRecipe(ItemStack itemStack) {
		IRecipe[] recipes = RecipeCache.instance().matchListRecipes();
		if(itemStack != null)
		{

			for(IRecipe recipe: recipes)
			{
				if(recipe.getRecipeOutput().getDisplayName().equals(itemStack.getDisplayName()))				{
					return recipe;
				}
			}
		}
		return null;
	}
	
	private boolean itemIsRecursive(ItemStack itemStack) {
		IRecipe recipe = grabRecipe(itemStack);
		if(itemStack != null & recipe != null)
		{
			if(recipe.getIngredients().size() > 0)
			{
				for(ItemStack stack: recipe.getIngredients().get(0).getMatchingStacks())
				{
					IRecipe recipe2 = grabRecipe(stack);
					if(recipe2 != null && recipe2.getIngredients().size() > 0)
					{
						for(ItemStack stack2: recipe2.getIngredients().get(0).getMatchingStacks())
						{
							if(itemStack.getDisplayName().equals(stack2.getDisplayName()))
							{
								return true;
							}
						}
					}
				}
			}
		}	
		return false;
	}

	private boolean stackNeedsFill(IRecipe recipe, ItemStack itemStack) {
		// if it does not exist yet it needs to be filled and created
		if(!stackOverload.containsKey(itemStack.getDisplayName()))
		{
			stackOverload.put(itemStack.getDisplayName(), recipe.getRecipeOutput().getCount() - 1);
			return true;
		}
		else if(stackOverload.get(itemStack.getDisplayName()) == 0)
		{
			stackOverload.put(itemStack.getDisplayName(), recipe.getRecipeOutput().getCount() - 1);
			return true;
		}
		else
		{
			stackOverload.put(itemStack.getDisplayName(), stackOverload.get(itemStack.getDisplayName()) - 1);
			return false;
		}
	}
	
	public boolean isReady()
	{
		return ready;
	}

	public static void updateInventory() {
		// TODO Auto-generated method stub
		if(instance.rawCount != null)
		{
		Set<ItemStack> sets = instance.rawCount.keySet();
			InventoryPlayer inv = Minecraft.getMinecraft().player.inventory;
			for(ItemStack stacks: sets)
			{
				if(inv.hasItemStack(stacks))
				{
					instance.rawCount.put(stacks,instance.rawCount.get(stacks) - inv.getStackInSlot(inv.getSlotFor(stacks)).getCount());
				}
			}
		}
	}
}
