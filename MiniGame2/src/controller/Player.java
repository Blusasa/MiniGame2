package controller;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private int curRoom;
	private List<Item> inventory;
	
	protected Player() {
		this.inventory = new ArrayList<>();
	}
	
	protected void addItem(Item it) {
		inventory.add(it);
	}
	
	public int getCurRoom() {
		return curRoom;
	}
	
	protected List<Item> getInventory(){
		return inventory;
	}
	
	protected String printInventory() {
		if(this.inventory.isEmpty()) return "There are no items in your inventory";
		
		StringBuilder inv = new StringBuilder();
		inventory.forEach(i -> inv.append(i.getItemName() + ": " + i.getItemDescription() + "\n"));
		
		return inv.toString();
	}
	
	protected void removeItem(Item it) {
		inventory.remove(it);
	}
	
	public void setCurRoom(int current) {
		this.curRoom = current;
	}
}
