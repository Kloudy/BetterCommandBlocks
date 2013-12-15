package kloudy.mc.bettercommandblocks;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.ChatColor;

public class SelectionDisplay implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ArrayList<SerialBlock> blocks;
	
	private String name, playerName;
	private int maxX, maxY, maxZ;
	private int minX, minY, minZ;
	
	public SelectionDisplay(ArrayList<SerialBlock> blocks, String name, String playerName, int maxX, int maxY, int maxZ, int minX, int minY, int minZ)
	{
		this.blocks = blocks;
		this.name = name;
		this.playerName = playerName;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public int getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(int maxZ) {
		this.maxZ = maxZ;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public int getMinZ() {
		return minZ;
	}

	public void setMinZ(int minZ) {
		this.minZ = minZ;
	}

	public ArrayList<SerialBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<SerialBlock> blocks) {
		this.blocks = blocks;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString()
	{
		return ChatColor.GRAY + "Name: " + ChatColor.GREEN + "'" + name + "'" + ChatColor.GRAY + " Owner: " + ChatColor.AQUA + playerName;
	}

}
