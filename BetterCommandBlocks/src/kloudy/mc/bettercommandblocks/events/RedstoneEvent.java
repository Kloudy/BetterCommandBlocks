package kloudy.mc.bettercommandblocks.events;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Logger;

import kloudy.mc.bettercommandblocks.BetterCommandBlocks;
import kloudy.mc.bettercommandblocks.SelectionDisplay;
import kloudy.mc.bettercommandblocks.SerialBlock;
import kloudy.mc.bettercommandblocks.Variable;
import kloudy.mc.bettercommandblocks.util.CommandParser;
import kloudy.mc.bettercommandblocks.util.DataManager;
import kloudy.mc.bettercommandblocks.util.DataType;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CommandBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;


public class RedstoneEvent implements Listener
{
	Logger logger = BetterCommandBlocks.logger;
	DataManager dm = DataManager.getInstance();
	HashMap<String, Long> recentVariables = dm.recentVariables;
	HashMap<String, Long> recentDisplays = dm.recentDisplays;
	
	@EventHandler
	public void RedstoneListener(BlockRedstoneEvent event)
	{	
		Block block = event.getBlock();

		Calendar cal = Calendar.getInstance();
		CommandParser cmdparse = new CommandParser();
		long now = cal.getTimeInMillis();
		
		if(block.getType() == Material.COMMAND && (block.isBlockPowered() || block.isBlockIndirectlyPowered()))
		{			
			CommandBlock cmdBlock = (CommandBlock) block.getState();
			String cmd = cmdBlock.getCommand();
			String[] args = cmd.split(" ");
			
			if(args.length >= 5 && args[0].equalsIgnoreCase("/showdisplay") || args[0].equalsIgnoreCase("showdisplay"))
			{
				//look up display by name
				SelectionDisplay display = dm.displays.get(args[4]);
				
				if(display != null && ((recentDisplays.get(display.getName()) != null && now - recentDisplays.get(display.getName()) > 50) || recentDisplays.get(display.getName()) == null))
				{
					recentDisplays.put(display.getName(), now);//add to displays list recently triggered
					
					Location loc = cmdBlock.getLocation();

					try
					{
						//check for relative coords vs absolute
						if(args[1].charAt(0) == '~' && args[2].charAt(0) == '~' && args[3].charAt(0) == '~')
						{
							args[1] = args[1].replace("~", "");
							args[2] = args[2].replace("~", "");
							args[3] = args[3].replace("~", "");
							
							if(args[1].length() == 0){
								args[1] = "0";
							}
							if(args[2].length() == 0){
								args[2] = "0";
							}
							if(args[3].length() == 0){
								args[3] = "0";
							}
							
							//arguments are variables
							
							if(dm.variables.get(args[1]) != null)
							{
								//variable must be an int
								if(dm.variables.get(args[1]).getDataType() == DataType.INT){
									args[1] = Integer.toString((Integer)(dm.variables.get(args[1]).getValue()));
								}
							}
							
							if(dm.variables.get(args[2]) != null)
							{
								//variable must be an int
								if(dm.variables.get(args[2]).getDataType() == DataType.INT){
									args[2] = Integer.toString((Integer)(dm.variables.get(args[2]).getValue()));
								}
							}
							
							if(dm.variables.get(args[3]) != null)
							{
								//variable must be an int
								if(dm.variables.get(args[3]).getDataType() == DataType.INT){
									args[3] = Integer.toString((Integer)(dm.variables.get(args[3]).getValue()));
								}
							}
							
							loc.setX(Integer.parseInt(args[1]) + loc.getBlockX());
							loc.setY(Integer.parseInt(args[2]) + loc.getBlockY());
							loc.setZ(Integer.parseInt(args[3]) + loc.getBlockZ());
						}
						else if(args[1].charAt(0) != '~' && args[2].charAt(0) != '~' && args[3].charAt(0) != '~')
						{					
							loc.setX(Integer.parseInt(args[1]));
							loc.setY(Integer.parseInt(args[2]));
							loc.setZ(Integer.parseInt(args[3]));
						}
					}
					catch(NumberFormatException e){}
					
					//TODO place blocks with thread
					int maxX = display.getMaxX();
					int maxY = display.getMaxY();
					int maxZ = display.getMaxZ();
					int minX = display.getMinX();
					int minY = display.getMinY();
					int minZ = display.getMinZ();
					
					ArrayList<SerialBlock> blocks = display.getBlocks();					
					World world = block.getWorld();
					
					int counter = 0;
					
					for(int i = minX; i <= maxX; i++)
					{
						for(int j = maxY; j >= minY; j--)
						{
							for(int k = minZ; k <= maxZ; k++)
							{
								Block currBlock = world.getBlockAt(loc.getBlockX() + (i - minX), loc.getBlockY() + (j - maxY), loc.getBlockZ() + (k - minZ));
								currBlock.setTypeIdAndData(blocks.get(counter).getId(), blocks.get(counter).getData(), false);
								counter++;
							}
						}
					}
				}	
			}
			
			//player editing the value of a variable
			else if((args[0].equalsIgnoreCase("var") || args[0].equalsIgnoreCase("/var")) && args.length == 3)
			{
				Variable var = dm.variables.get(args[1]);				
				
				if(var != null && ((recentVariables.get(var.getName()) != null && now - recentVariables.get(var.getName()) > 50) || recentVariables.get(var.getName()) == null))
				{		
					recentVariables.put(var.getName(), now);
					
					if(var.getDataType() == DataType.STRING){
						var.setValue((String)(cmdparse.parseCommand(args[2], var.getDataType())));
					}
					else if(var.getDataType() == DataType.INT){
						var.setValue((Integer)(cmdparse.parseCommand(args[2], var.getDataType())));
					}
					else if(var.getDataType() == DataType.DOUBLE){
						var.setValue((Double)(cmdparse.parseCommand(args[2], var.getDataType())));
					}
					else if(var.getDataType() == DataType.BOOLEAN){
						var.setValue((Boolean)(cmdparse.parseCommand(args[2], var.getDataType())));
					}
				}			
			}
			
			//player testing for variable case
			else if(args[0].equals("if") && args.length == 2)
			{
				if(cmdparse.parseTestCase(args[1], DataType.INT))
				{
					Block b = block.getRelative(BlockFace.EAST, 1);
					if(b.getType() == Material.REDSTONE_COMPARATOR_OFF){
						b.setType(Material.REDSTONE_COMPARATOR_ON);
					}
					
					b = block.getRelative(BlockFace.WEST, 1);
					if(b.getType() == Material.REDSTONE_COMPARATOR_OFF){
						b.setType(Material.REDSTONE_COMPARATOR_ON);
					}
					
					b = block.getRelative(BlockFace.NORTH, 1);
					if(b.getType() == Material.REDSTONE_COMPARATOR_OFF){
						b.setType(Material.REDSTONE_COMPARATOR_ON);
					}
					
					b = block.getRelative(BlockFace.SOUTH, 1);
					if(b.getType() == Material.REDSTONE_COMPARATOR_OFF){
						b.setType(Material.REDSTONE_COMPARATOR_ON);
					}
				}
				
				else
				{
					Block b = block.getRelative(BlockFace.EAST, 1);
					if(b.getType() == Material.REDSTONE_COMPARATOR_ON){
						b.setType(Material.REDSTONE_COMPARATOR_OFF);
					}
					
					b = block.getRelative(BlockFace.WEST, 1);
					if(b.getType() == Material.REDSTONE_COMPARATOR_ON){
						b.setType(Material.REDSTONE_COMPARATOR_OFF);
					}
					
					b = block.getRelative(BlockFace.NORTH, 1);
					if(b.getType() == Material.REDSTONE_COMPARATOR_ON){
						b.setType(Material.REDSTONE_COMPARATOR_OFF);
					}
					
					b = block.getRelative(BlockFace.SOUTH, 1);
					if(b.getType() == Material.REDSTONE_COMPARATOR_ON){
						b.setType(Material.REDSTONE_COMPARATOR_OFF);
					}
				}
			}
		}
	}
}
