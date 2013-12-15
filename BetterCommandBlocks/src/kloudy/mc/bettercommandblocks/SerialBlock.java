package kloudy.mc.bettercommandblocks;

import java.io.Serializable;

public class SerialBlock implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;
	private byte data;
	
	public SerialBlock(int id, byte data)
	{
		this.id = id;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}
}
