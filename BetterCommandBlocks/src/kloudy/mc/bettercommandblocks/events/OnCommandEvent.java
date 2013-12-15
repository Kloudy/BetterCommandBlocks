package kloudy.mc.bettercommandblocks.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

import kloudy.mc.bettercommandblocks.BetterCommandBlocks;
import kloudy.mc.bettercommandblocks.SelectionDisplay;
import kloudy.mc.bettercommandblocks.SerialBlock;
import kloudy.mc.bettercommandblocks.Variable;
import kloudy.mc.bettercommandblocks.util.DataManager;
import kloudy.mc.bettercommandblocks.util.DataType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.*;

/*
 * Command Format:
 * 
 * Command Block Commands
 * setDisplay ~ ~ ~ <display_name>
 * var <var_name> <value>
 * 
 * Player Commands:
 * createVar <datatype> <var_name> <initial_value>
 * setdisplay <display_name>
 */

public class OnCommandEvent implements CommandExecutor
{
	Logger logger = BetterCommandBlocks.logger;
	DataManager dm = DataManager.getInstance();
	
	private static Comparator<SelectionDisplay> ALPHABETICAL_ORDER = new Comparator<SelectionDisplay>() {
	    public int compare(SelectionDisplay dis1, SelectionDisplay dis2) {
	        int res = String.CASE_INSENSITIVE_ORDER.compare(dis1.getName(), dis2.getName());
	        if (res == 0) {
	            res = dis1.getName().compareTo(dis2.getName());
	        }
	        return res;
	    }
	};
	
	private static Comparator<Variable> ALPHABETICAL_ORDER_VAR = new Comparator<Variable>() {
	    public int compare(Variable var1, Variable var2) {
	        int res = String.CASE_INSENSITIVE_ORDER.compare(var1.getName(), var2.getName());
	        if (res == 0) {
	            res = var1.getName().compareTo(var2.getName());
	        }
	        return res;
	    }
	};
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("bcb"))
		{
			Player player = Bukkit.getServer().getPlayer(sender.getName());
			WorldEditPlugin we = BetterCommandBlocks.we;
			Selection selection = we.getSelection(player);
			
			if(args.length == 0){
				player.sendMessage(ChatColor.GOLD + "[Better Command Blocks] v 1.0");
				player.sendMessage(ChatColor.GOLD + "www.antarescraft.com");
				player.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.RED + "Kloudy");			
			}
			
			//Player setting up WE selection to be display
			else if(args[0].equalsIgnoreCase("setdisplay") && args.length == 2 && player.hasPermission("bcb.display"))
			{				
				if(selection != null && selection instanceof CuboidSelection)
				{		
					if(dm.displays.get(args[1]) == null)
					{				
						CuboidSelection cube = (CuboidSelection) selection;
						Location max = cube.getMaximumPoint();
						Location min = cube.getMinimumPoint();
						World world = max.getWorld();
						
						int minX = min.getBlockX();
						int minY = min.getBlockY();
						int minZ = min.getBlockZ();
						int maxX = max.getBlockX();
						int maxY = max.getBlockY();
						int maxZ = max.getBlockZ();
						
						ArrayList<SerialBlock> blocks = new ArrayList<SerialBlock>();
						
						for(int i = minX; i <= maxX; i++)
						{
							for(int j = maxY; j >= minY; j--)
							{
								for(int k = minZ; k <= maxZ; k++)
								{
									Block b = world.getBlockAt(i, j, k);
									blocks.add(new SerialBlock(b.getTypeId(), b.getData()));
								}
							}
						}
						
						//add selection display to HashMap
						dm.displays.put(args[1], new SelectionDisplay(blocks, args[1], player.getName(), maxX, maxY, maxZ, minX, minY, minZ));
						player.sendMessage(ChatColor.AQUA + "Successfully created display");
					}
					else
					{
						String s = dm.displays.get(args[1]).getName();
						player.sendMessage(ChatColor.RED + "Display " + ChatColor.AQUA + "'" + s + "'" + ChatColor.RED + "already exists.");
					}
				}			
				else
				{
					player.sendMessage(ChatColor.RED + "Must first make a selection");
				}
			}
			
			//Player setting up new variable
			else if(args[0].equalsIgnoreCase("createVar") && args.length >= 4)
			{
				if(dm.variables.get(args[2]) == null && player.hasPermission("bcb.var"))
				{
					if(args[1].equalsIgnoreCase("String"))
					{
						String s = new String();
						
						for(int i = 3; i < args.length; i++){
							s += args[i];
						}
						
						//variable name must start with an alphabetical letter
						if(args[2].matches("[a-zA-Z]\\w*") && args[2].length() <= 15)
						{						
							dm.variables.put(args[2], new Variable(DataType.STRING, args[2], player.getName(), s));
							
							player.sendMessage(ChatColor.GREEN + "Successfully created variable " + ChatColor.AQUA + "'" + args[2] + "'");
						}
						else{
							player.sendMessage(ChatColor.RED + "Variable name must start with alphabetical an letter (a-z,A-Z) and must be no larger than 15 characters");
						}
					}
					
					else if(args[1].equalsIgnoreCase("int"))
					{
						//variable name must start with an alphabetical letter
						if(args[2].matches("[a-zA-Z]\\w*") && args[2].length() <= 15)
						{
							try
							{
								int value = Integer.parseInt(args[3]);
								dm.variables.put(args[2], new Variable(DataType.INT, args[2], player.getName(), value));
								player.sendMessage(ChatColor.AQUA + "Successfully created variable");
							}
							catch(NumberFormatException e){
								player.sendMessage(ChatColor.RED + "Value must be an integer");
							}
						}
						else{
							player.sendMessage(ChatColor.RED + "Variable name must start with alphabetical an letter (a-z,A-Z) and must be no larger than 15 characters");
						}
					}
					
					else if(args[1].equalsIgnoreCase("double"))
					{
						//variable name must start with an alphabetical letter
						if(args[2].matches("[a-zA-Z]\\w*") && args[2].length() <= 15)
						{
							try
							{
								double value = Double.parseDouble(args[3]);
								dm.variables.put(args[2], new Variable(DataType.DOUBLE, args[2], player.getName(), value));
								player.sendMessage(ChatColor.AQUA + "Successfully created variable");
							}
							catch(NumberFormatException e){
								player.sendMessage(ChatColor.RED + "Value must be an double");
							}
						}
						else{
							player.sendMessage(ChatColor.RED + "Variable name must start with alphabetical an letter (a-z,A-Z) and must be no larger than 15 characters");
						}
					}
					
					else if(args[1].equalsIgnoreCase("boolean"))
					{
						//variable name must start with an alphabetical letter
						if(args[2].matches("[a-zA-Z]\\w*") && args[2].length() <= 15)
						{
							if(args[3].equalsIgnoreCase("true")){
								dm.variables.put(args[2], new Variable(DataType.BOOLEAN, args[2], player.getName(), true));
								player.sendMessage(ChatColor.AQUA + "Successfully created variable");
							}
							else if(args[3].equalsIgnoreCase("false")){
								dm.variables.put(args[2], new Variable(DataType.BOOLEAN, args[2], player.getName(), false));
								player.sendMessage(ChatColor.AQUA + "Successfully created variable");
							}
							else{
								player.sendMessage(ChatColor.RED + "Value must be a boolean");
							}
						}
						else{
							player.sendMessage(ChatColor.RED + "Variable name must start with alphabetical an letter (a-z,A-Z) and must be no larger than 15 characters");
						}
					}
					
					else{
						player.sendMessage(ChatColor.RED + "Invalid command or arguments");
					}
				}
				else
				{
					String s = dm.variables.get(args[2]).getName();
					player.sendMessage(ChatColor.RED + "Variable " + ChatColor.AQUA + "'" + s + "' " + ChatColor.RED + "already exists.");
				}
			}
			
			//Player showing display list
			else if(args[0].equalsIgnoreCase("displaylist") && player.hasPermission("bcb.lists"))
			{
				ArrayList<SelectionDisplay> arr = new ArrayList<SelectionDisplay>();
				
				for(SelectionDisplay d : dm.displays.values()){
					arr.add(d);
				}
				
				Collections.sort(arr, ALPHABETICAL_ORDER);
							
				int pageNum = 0;
				
				if(args.length == 2)
				{
					try{
						pageNum = Integer.parseInt(args[1]) - 1;
						
						if(pageNum < 0){
							pageNum = 0;
						}
					}
					catch(NumberFormatException e){
						player.sendMessage(ChatColor.RED + "Invalid arguements.\nUsage: /bcb displaylist <page_number>");
					}
				}
				
				String str = new String();
				
				str += ChatColor.GOLD + "[Better Command Blocks] Display List - Page " + (pageNum + 1) + "\n";
				str += ChatColor.GOLD + "=============================================\n";
								
				int index = pageNum * 10;
				
				for(int i = index; (arr.size() - i) > 0 && i < index + 10; i++){
					str += ChatColor.GREEN + arr.get(i).toString() + "\n";
				}
				
				str += ChatColor.GOLD + "=============================================";
				
				player.sendMessage(str);
			}
			
			//Player removing a display
			else if(args[0].equalsIgnoreCase("removedisplay") && args.length == 2)
			{
				SelectionDisplay display = dm.displays.get(args[1]);
				
				if(display != null)
				{
					if(display.getPlayerName().equals(player.getName()) || player.hasPermission("bcb.removeoverride"))
					{
						String s = dm.displays.get(args[1]).getName();
						dm.displays.remove(args[1]);
						player.sendMessage(ChatColor.GREEN + "Successfully removed display " + ChatColor.AQUA + "'" + s + "'");
					}
					else{
						player.sendMessage(ChatColor.RED + "Display removal failed. You are not the owner of the display.");
					}
				}
				else{
					player.sendMessage(ChatColor.RED + "Display removal failed. Display does not exist");
				}
			}
			
			//Player removing a variable
			else if(args[0].equalsIgnoreCase("removevar") && args.length == 2)
			{
				Variable var = dm.variables.get(args[1]);
				
				if(var != null)
				{
					if(var.getPlayerName().equals(player.getName()) || player.hasPermission("bcb.removeoverride"))
					{
						String s = dm.variables.get(args[1]).getName();
						dm.variables.remove(args[1]);
						player.sendMessage(ChatColor.GREEN + "Successfully removed variable " + ChatColor.AQUA + "'" + s + "'");
					}
					else{
						player.sendMessage(ChatColor.RED + "Variable removal failed. You are not the owner of the variable");
					}
				}
				else{
					player.sendMessage(ChatColor.RED + "Variable removal failed. Variable does not exist");
				}
			}
			
			//Player showing variable list
			else if(args[0].equalsIgnoreCase("variablelist") && args.length <= 2 && player.hasPermission("bcblists"))
			{
				ArrayList<Variable> arr = new ArrayList<Variable>();
				
				for(Variable v : dm.variables.values()){
					arr.add(v);
				}
				
				Collections.sort(arr, ALPHABETICAL_ORDER_VAR);
							
				int pageNum = 0;
				
				if(args.length == 2)
				{
					try{
						pageNum = Integer.parseInt(args[1]) - 1;
						
						if(pageNum < 0){
							pageNum = 0;
						}
					}
					catch(NumberFormatException e){
						player.sendMessage(ChatColor.RED + "Invalid arguements.\nUsage: /bcb variablelist <page_number>");
					}
				}
				
				String str = new String();
				
				str += ChatColor.GOLD + "[Better Command Blocks] Variable List - Page " + (pageNum + 1) + "\n";
				str += ChatColor.GOLD + "=============================================\n";
								
				int index = pageNum * 10;
				
				for(int i = index; (arr.size() - i) > 0 && i < index + 10; i++){
					str += ChatColor.GREEN + arr.get(i).toString() + "\n";
				}
				
				str += ChatColor.GOLD + "=============================================";
				
				player.sendMessage(str);
			}
			
			//player is looking up variable info
			else if(args[0].equalsIgnoreCase("varinfo") && args.length <= 2 && player.hasPermission("bcblists"))
			{
				Variable var = dm.variables.get(args[1]);
				
				if(var != null)
				{
					String str = new String();
					str += ChatColor.GOLD + "[Better Command Blocks]" + "\nVariable Info: " + ChatColor.AQUA + var.getName() +  "\n";
					str += ChatColor.GRAY + "Current Value: " + ChatColor.AQUA + var.getValue() + "\n";
					str += ChatColor.GRAY + "Owner: " + ChatColor.AQUA + var.getPlayerName();
					
					player.sendMessage(str);
				}
				else{
					player.sendMessage(ChatColor.RED + "The variable " + ChatColor.AQUA + "'" + args[1] + "' " + ChatColor.RED + "does not exist.");
				}
			}
			
			else{
				player.sendMessage(ChatColor.RED + "Invalid command or arguments");
			}
							
			return true;
		}
		return false;
	}
}
