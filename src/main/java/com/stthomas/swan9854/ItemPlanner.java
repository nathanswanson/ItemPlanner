package com.stthomas.swan9854;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import com.stthomas.swan9854.core.Keybinds;
import com.stthomas.swan9854.core.RecipeCache;
import com.stthomas.swan9854.event.EventHandlerItem;
import com.stthomas.swan9854.event.OverlayHandler;

@Mod(modid = ItemPlanner.MODID, name = ItemPlanner.NAME, version = ItemPlanner.VERSION, clientSideOnly = true)
public class ItemPlanner
{
    public static final String MODID = "itemplanner";
    public static final String NAME = "Item Planner";
    public static final String VERSION = "1.0";
    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Keybinds.register();
        MinecraftForge.EVENT_BUS.register(new OverlayHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandlerItem());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {

    	RecipeCache.initialize();
    }

}
