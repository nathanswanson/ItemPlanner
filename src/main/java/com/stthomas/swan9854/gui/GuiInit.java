package com.stthomas.swan9854.gui;

import java.util.HashMap;

import com.stthomas.swan9854.core.RecipeCache;
import com.stthomas.swan9854.craftable.Duple;
import com.stthomas.swan9854.filter.OreDictionaryFilter;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

/* 
 * This is a singleton class of the Planning GUI.
 * 
 *
 * 
 */
public class GuiInit {
	private static GuiInit guiInit;
	private GuiPlanner guiPlanner;
	private HashMap<Item, Integer> stackOverload;
	private HashMap<Duple, Integer> h;
	
	public static void initialize(ItemStack stack, int count) {
		GuiPlanner.initialize();
		guiInit = new GuiInit(stack, count);
	}
	
	private GuiInit(ItemStack stack, int count)
	{
		h = new HashMap<Duple, Integer>();
		stackOverload = new HashMap<Item, Integer>();
		recursiveRecipes(0, stack);
		GuiPlanner.instance().convertToGui(h);
	}

	private void recursiveRecipes(int index, ItemStack itemStack)
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

	
	public static boolean isInitialzied() {
		return guiInit != null;
	}
	
}
