package kloudy.mc.bettercommandblocks;

import java.io.Serializable;

import org.bukkit.ChatColor;

import kloudy.mc.bettercommandblocks.util.DataType;

public class Variable implements Serializable
{
	private static final long serialVersionUID = 1L;
	private DataType dataType;
	private String name, playerName;
	private Object value;
	
	public Variable(DataType dataType, String name, String playerName, Object value)
	{
		this.dataType = dataType;
		this.name = name;
		this.playerName = playerName;
		this.value = value;
	}
	
	public String toString()
	{
		return ChatColor.GRAY + "Name: " + ChatColor.GREEN + "'" + name + "'" + ChatColor.GRAY + " Owner: " + ChatColor.AQUA + playerName;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
