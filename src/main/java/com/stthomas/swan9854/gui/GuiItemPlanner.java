package com.stthomas.swan9854.gui;

import java.io.IOException;
import java.util.HashMap;

import com.stthomas.swan9854.craftable.Duple;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class GuiItemPlanner extends GuiScreen {
	
	private GuiTextField text;
	private ItemStack item;
	
	public GuiItemPlanner(ItemStack item)
	{
		this.item = item;
	}
	
	@Override
	public void initGui()
	{
		this.allowUserInput = true;
		this.text = new GuiTextField(50, Minecraft.getMinecraft().fontRenderer, this.width / 2 - 88, this.height/2 - 20, 137, 20);
	    text.setMaxStringLength(23);
	    text.setText("Count");
	    this.text.setFocused(true);
		this.buttonList.add(new GuiButton(259, width / 2 - 90, height /2 + 10, "Confirm"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		  if (button.id == 259)
	      {
		  
			  String newText = text.getText().replaceAll("[^0-9]+", "");
			  if(!newText.isEmpty())
			  {
				  GuiPlanner.initialize(this.item, Integer.parseInt(newText));
				  this.mc.player.closeScreen();			   	   	   
			  }
		    
	      }
	}
	
	@Override
	public void onGuiClosed()
	{
		//GuiPlanner.destroy();
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		this.text.updateCursorCounter();
	}
	
	@Override
    protected void mouseClicked(int x, int y, int btn) throws IOException {
        super.mouseClicked(x, y, btn);
        this.text.mouseClicked(x, y, btn);
    }
	
	protected void keyTyped(char par1, int par2) throws IOException
    {
        super.keyTyped(par1, par2);
        this.text.textboxKeyTyped(par1, par2);
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		renderIcon(this.item, width / 2 + 70, height /2 - 20);
		drawCenteredString(Minecraft.getMinecraft().fontRenderer, this.item.getDisplayName(), width / 2, height / 2 - 35, Integer.parseInt("FFAA00", 16));
		this.text.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	public void renderIcon(ItemStack stack,int posX, int posY)
	{
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();		
		Minecraft mc = Minecraft.getMinecraft();	
	    Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, posX, posY);
	}
}
