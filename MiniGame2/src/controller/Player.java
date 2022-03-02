package controller;

import java.util.List;

public class Player {
	private int curRoom;
	private List<Item> inventory;
	
	protected Player() {}
	
	protected void addItem(Item it) {}
	
	public int getCurRoom() {}
	
	protected List<Item> getInventory(){}
	
	protected String printInventory() {}
	
	protected void removeItem(Item it) {}
	
	public void setCurRoom(int current) {}
}
