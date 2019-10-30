package com.stthomas.swan9854.core;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybinds {
	public static KeyBinding useINV;
	
	public static void register()
	{
		useINV = new KeyBinding("key.Recipe Array", Keyboard.KEY_G, "key.categories.inventory");
		
        ClientRegistry.registerKeyBinding(useINV);
	}
}
