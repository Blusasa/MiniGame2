package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import gameExceptions.GameException;
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
	
	public Room() throws GameException{
		this.exits = new ArrayList<>();
		this.items = new ArrayList<>();
		idb = ItemDB.getInstance();
		rdb = RoomDB.getInstance();
		this.visited = false;
	}
	
	public Room(int id) throws GameException{
		this.exits = new ArrayList<>();
		this.items = new ArrayList<>();
		idb = ItemDB.getInstance();
		rdb = RoomDB.getInstance();
		
		if(rdb.getRoom(id) == null) {
			throw new GameException("A room with that ID already exists");
		} else {
			this.roomID = id;
		}
		
		this.visited = false;
	}
	
	private String buildDescription() {
		return this.description;
	}
	
	private String buildItems() throws GameException{
		StringBuilder str = new StringBuilder();
		items.forEach(i -> {
			Item item = null;
			try {
				item = idb.getItem(i);
			} catch (GameException e) {
			}
			str.append(item.getItemDescription() + ". ");
		});
		
		return str.toString();
	}
	
	public String display() throws GameException{
		StringBuilder roomStr = new StringBuilder();
		roomStr.append(this.roomID + "\n" + this.name);
		roomStr.append(buildDescription() + buildItems());
		roomStr.append("\n" + displayExits());
		
		return roomStr.toString();
	}
	
	private String displayExits() {
		StringBuilder str = new StringBuilder();
		exits.forEach(e -> str.append(e.getDirection() + "\n"));
		return str.toString();
	}
	
	public void dropItem(Item item) throws GameException {}
	
	public String getDescription() {
		return this.description;
	}
	
	public List<Exit> getExits(){
		return this.exits;
	}
	
	public List<Integer> getItems(){
		return this.items;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRoomID() {
		return this.roomID;
	}
	
	public List<Item> getRoomItems() throws GameException{
		items.stream().map(i -> {
			
		})
	}
	
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
