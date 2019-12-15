package com.stthomas.swan9854.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.stthomas.swan9854.craftable.Duple;
import com.stthomas.swan9854.gui.GuiPlanner;
import com.stthomas.swan9854.gui.part.GuiLine;
import com.stthomas.swan9854.gui.part.GuiPart;
import com.stthomas.swan9854.gui.part.GuiRect;
import com.stthomas.swan9854.gui.part.GuiString;
import com.stthomas.swan9854.gui.part.IGuiPart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OverlayHandler extends Gui {
	
	 
	    @SubscribeEvent
		public void render(RenderGameOverlayEvent.Post e)
		{
	    	 //This is ONLY used for showing the actual items on screen after it is planned
	    	 if(GuiPlanner.instance().isReady())
	    	 {
	    		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    	     GlStateManager.disableDepth();
		    	 ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());

	    		 int width = scaled.getScaledWidth();
			     int height = scaled.getScaledHeight();
			     IGuiPart[] parts = GuiPlanner.instance().getPart();
	    		 for(int i = 0; i < parts.length; i++)
	    		 {
	    			 if(parts[i] instanceof GuiString)
	    			 {
	    				 drawString(Minecraft.getMinecraft().fontRenderer, ((GuiString)parts[i]).getPart().getString(), 5, 5 + (i * 10), Integer.parseInt(((GuiString) parts[i]).getColor() , 16));
	    			 }
	    			 else if(parts[i] instanceof GuiLine)
	    			 {
	    				 GuiLine line = (GuiLine) parts[i];
	    				 drawHorizontalLine(line.getStart(),line.getEnd(),5+ (i * 10),-1) ;
	    			 }
	    			 else if(parts[i] instanceof GuiPart)
	    			 {
	    				renderIcon((GuiPart)parts[i], i); 
	    			 }	
	    			 else if(parts[i] instanceof GuiRect)
	    			 {
	    				 System.out.println("test");
	    				 GuiRect rect = (GuiRect) parts[i];
	    				 drawRect(width /2 -50, 50 ,width /2 + 50,  0, Integer.parseInt("ffAA00", 16));
	    			 }
	    			 
	    			
	    		  }
	    	  }
	    	 //generate GUI
	    	 
		}
	    
		public void renderIcon(GuiPart iGuiPart, int i)
		{
			RenderHelper.disableStandardItemLighting();
			RenderHelper.enableGUIStandardItemLighting();
			Minecraft mc = Minecraft.getMinecraft();
			Duple[] stacks = iGuiPart.getItem();
			for(int f = 0; f < stacks.length; f++)
			{
				 Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stacks[f].getStack(), 5 + (16 * f), 5 + (i * 10));
				 drawString(Minecraft.getMinecraft().fontRenderer, stacks[f].getPhase() + "", 15 + (16 * f), 15 + (i * 10) , -1);
			}
		}
}

