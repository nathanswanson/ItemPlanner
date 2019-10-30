package com.stthomas.swan9854.core;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
/********************
 *
 * @author Nathan
 * 
 * Sum: this singleton class is used to cache every available recipe in the game
 * 
 * Run: during post init and called in eventHandlerItem
 *
 ********************/
public class RecipeCache {

	private static RecipeCache instance;
	private static Logger logger;
	private static ArrayList<IRecipe> recipes;
	
	public static RecipeCache instance()
	{
		if(instance == null)
		{
			instance = new RecipeCache();
		}
		
		return instance;
	}

	private void scan()
		{
			
			// RegistryNamespacecd does not have a size method so we must iterate till its finished with an index
			for (IRecipe recipe : CraftingManager.REGISTRY)
			{
				recipes.add(recipe);
			}
			
			System.out.println(recipes.toString());
		}

	public static void initialize()
	{

		if(instance == null)
		{
			recipes = new ArrayList<IRecipe>();
			instance = new RecipeCache();
			instance.scan();
		}
		else
			logger.warn("WARNING: A method is trying to initialize RecipeCache but its already initialized");
	}

	private static ArrayList<IRecipe> getRecipes() {
		// TODO Auto-generated method stub
		return recipes;
	}
	
	public static IRecipe[] matchListRecipes()
	{
		return getRecipes().toArray(new IRecipe[0]);
	}
		
}
