package kloudy.mc.bettercommandblocks.util;

import java.util.HashMap;

import kloudy.mc.bettercommandblocks.SelectionDisplay;
import kloudy.mc.bettercommandblocks.Variable;

public class DataManager 
{
	public HashMap<String, SelectionDisplay> displays;
	public HashMap<String, Variable> variables;
	public HashMap<String, Long> recentVariables;
	public HashMap<String, Long> recentDisplays;
	public static DataManager instance;
	
	private DataManager()
	{
		displays = new HashMap<String, SelectionDisplay>();
		variables = new HashMap<String, Variable>();
		recentVariables = new HashMap<String, Long>();
		recentDisplays = new HashMap<String, Long>();
	}

	public static DataManager getInstance()
	{
		if(instance == null){
			instance = new DataManager();
		}
		return instance;
	}
}
