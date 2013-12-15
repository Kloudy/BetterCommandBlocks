package kloudy.mc.bettercommandblocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import kloudy.mc.bettercommandblocks.events.OnCommandEvent;
import kloudy.mc.bettercommandblocks.events.RedstoneEvent;
import kloudy.mc.bettercommandblocks.util.DataManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterCommandBlocks extends JavaPlugin
{
	public static Logger logger;
	public static WorldEditPlugin we;
	DataManager dm;
	
	@SuppressWarnings("unchecked")
	public void onEnable()
	{
		dm = DataManager.getInstance();
		logger = getLogger();
		we = getWorldEdit();
		getCommand("bcb").setExecutor(new OnCommandEvent());
		getServer().getPluginManager().registerEvents(new RedstoneEvent(), this);
		
		//File I/O
		try
		{
			File folder = new File("plugins/BetterCommandBlocks_Data");
			
			if(!folder.exists()){
				folder.mkdir();
			}
			
			File displayFile = new File("plugins/BetterCommandBlocks_Data/displays.ser");
			File variableFile = new File("plugins/BetterCommandBlocks_Data/variables.ser");
			
			if(!displayFile.exists()){
				displayFile.createNewFile();
			}
			
			if(!variableFile.exists()){
				variableFile.createNewFile();
			}
			
			FileInputStream fileIn = new FileInputStream(displayFile);
			ObjectInputStream in = null;
			
			if(displayFile.length() > 0)
			{				
				in = new ObjectInputStream(fileIn);
				dm.displays = (HashMap<String, SelectionDisplay>) in.readObject();
				in.close();
			}
			
			fileIn = new FileInputStream(variableFile);
			
			if(variableFile.length() > 0)
			{
				in = new ObjectInputStream(fileIn);
				dm.variables = (HashMap<String, Variable>) in.readObject();
				in.close();
			}
		}
		catch(IOException i){
			i.printStackTrace();
		}
		catch(ClassNotFoundException c){
			c.printStackTrace();
		}
	}
	
	public void onDisable()
	{
		//write data to file
		try
		{
			File folder = new File("plugins/BetterCommandBlocks_Data");
			
			if(!folder.exists()){
				folder.mkdir();
			}
			
			File displayFile = new File("plugins/BetterCommandBlocks_Data/displays.ser");
			File variableFile = new File("plugins/BetterCommandBlocks_Data/variables.ser");
			
			if(!displayFile.exists()){
				displayFile.createNewFile();
			}
			
			if(!variableFile.exists()){
				variableFile.createNewFile();
			}
			
			FileOutputStream fileOut = new FileOutputStream("plugins/BetterCommandBlocks_Data/displays.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(dm.displays);
			out.close();
			
			fileOut = new FileOutputStream("plugins/BetterCommandBlocks_Data/variables.ser");
			out = new ObjectOutputStream(fileOut);
			
			out.writeObject(dm.variables);
			out.close();
		}
		catch(IOException i){
			i.printStackTrace();
		}
	}
	
	WorldEditPlugin getWorldEdit(){
		return (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	}	
}
