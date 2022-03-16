package controller;

public class Item {
	private String itemDescription;
	private int itemID;
	private String itemName;
	
	public String display() {
		return itemDescription;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemDescription(String description) {
		this.itemDescription = description;
	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Override
	public String toString() {
		return itemID + ": " + itemName + "\n" + itemDescription;
	}
}
