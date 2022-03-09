package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.Item;
import controller.Room;
import gameExceptions.GameException;

public class RoomDB {
	private static RoomDB instance;
	private List<Room> rooms;
	
	private RoomDB() throws GameException{
		this.rooms = new ArrayList<>();
	}
	
	public RoomDB getInstance() {
		if(instance == null) {
			instance = new RoomDB();
		}
		
		return instance;
	}
	
	public List<Item> getItems(int roomID) throws GameException{
		Room room = getRoom(roomID);
		ItemDB itemStore = ItemDB.getInstance();
		
		List<Item> items = room.getItems().stream().map(i -> itemStore.getItem(i)).collect(Collectors.toList());
		
		return items;
	}
	
	public String getMap() {
		//TODO: returns a string representation of a mpa of the game?
		return "";
	}
	
	public Room getRoom(int roomID) throws GameException {
		Optional<Room> room = rooms.stream().filter(r -> r.getRoomID() == roomID).findFirst();
		
		if(!room.isPresent()) {
			throw new GameException("Room doens't exist");
		}
		
		return room.get();
	}
	
	public void readRooms() throws GameException{
		//TODO: this method will read in the textfile and add rooms to the list
		
	}
	
	public void updateRoom(Room rm) throws GameException {
		
	}
}
