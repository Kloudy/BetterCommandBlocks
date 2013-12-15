package kloudy.mc.bettercommandblocks.events;

import java.util.ArrayList;

import kloudy.mc.bettercommandblocks.SelectionDisplay;
import kloudy.mc.bettercommandblocks.util.DataManager;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class MyBlockBreakEvent implements Listener
{
	DataManager dm = DataManager.getInstance();
	
	@EventHandler
	public int blockBreakListener(BlockBreakEvent event)
	{
		if(event.isCancelled()){
			return 0;
		}
		
		Block b = event.getBlock();
		
		ArrayList<SelectionDisplay> arr = new ArrayList<SelectionDisplay>();
		
		for(SelectionDisplay d : dm.displays.values()){
			arr.add(d);
		}
				
		boolean found = false;
		
		for(int a = 0; a < dm.displays.size(); a++)
		{
			SelectionDisplay display = arr.get(a);
						
			int maxX = display.getMaxX();
			int maxY = display.getMaxY();
			int maxZ = display.getMaxZ();
			int minX = display.getMinX();
			int minY = display.getMinY();
			int minZ = display.getMinZ();
			
			for(int i = minX; i <= maxX && !found; i++)
			{
				for(int j = maxY; j >= minY && !found; j--)
				{
					for(int k = minZ; k <= maxZ && !found; k++)
					{
						//block is within display region
						if(i == b.getX() && j == b.getY() && k == b.getZ()){
							found = true;
						}
					}
				}
			}
		}
		
		if(found){
			event.setCancelled(true);
		}
		
		return 0;
	}
}
