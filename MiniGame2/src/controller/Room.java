package controller;

import java.util.List;

import model.ItemDB;
import model.RoomDB;

public class Room {
	private String description;
	private List<Exit> exits;
	private ItemDB idb;
	private List<Integer> items;
	private String name;
	private RoomDB rdb;
	private int roomID;
	private boolean visited;
	
	public Room() throws GameException{}
	
	public Room(int id) throws GameException{}
	
	private String buildDescription() {}
	
	private String buildItems() throws GameException{}
	
	public String display() throws GameException{}
	
	private String displayExits() {}
	
	public void dropItem(Item item) throws GameException {}
	
	public String getDescription() {}
	
	public List<Exit> getExits(){}
	
	public List<Integer> getItems(){}
	
	public String getName() {}
	
	public int getRoomID() {}
	
	public List<Item> getRoomItems() throws GameException{}
	
	public boolean isVisited() {}
	
	public void removeItem(Item item) throws GameException{}
	
	public Room retrieveByID(int roomNum) throws GameException{}
	
	public void setDescription(String description) throws GameException {}
	
	public void setExits(List<Exit> exits) {}
	
	public void setItems(List<Integer> items) {}
	
	public void setName(String name) throws GameException{}
	
	public void setRoomID(int roomID) {}
	
	public void setVisisted(boolean visited) {}
	
	@Override
	public String toString() {}
	
	public void updateRoom() throws GameException {}
	
	public int validDirection(char cmd) throws GameException{}

}
