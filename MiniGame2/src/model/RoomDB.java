package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.Exit;
import controller.Item;
import controller.Room;
import gameExceptions.GameException;

public class RoomDB {
	private static RoomDB instance;
	private List<Room> rooms;
	
	private RoomDB() throws GameException{
		this.rooms = new ArrayList<>();
	}
	
	public static RoomDB getInstance() {
		if(instance == null) {
			try {
				instance = new RoomDB();
				instance.readRooms();
			} catch (GameException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return instance;
	}
	
	public List<Item> getItems(int roomID) throws GameException{
		Room room = rooms.stream().filter(r -> r.getRoomID() == roomID)
				.findFirst()
				.orElseThrow(() -> new GameException("Room " + roomID + " not found"));
		
		List<Item> items = new ArrayList<>();
		for(Integer x: room.getItems()) {
			Item item = ItemDB.getInstance().getItem(x);
			items.add(item);
		}
		
		return items;
	}
	
	public String getMap() {
		//TODO: Returns the String of the complete map
		return "";
	}
	
	public Room getRoom(int roomID) throws GameException {
		return rooms.stream().filter(r -> r.getRoomID() == roomID)
				.findFirst()
				.orElseThrow(() -> new GameException("Room not found"));
	}
	
	public void readRooms() throws GameException{

		String filePath = "src/Rooms.txt";
		File file = new File(filePath);
		Scanner in;
		
		try {
			in = new Scanner(file);
		} catch(FileNotFoundException e) {
			throw new GameException("Rooms file not found");
		}
		
		while(in.hasNext()) {
			Room room = new Room();
			List<Integer> roomItems = new ArrayList<>();
			List<Exit> roomExits = new ArrayList<>();
			
			int roomID = Integer.parseInt(in.nextLine());
			String roomName = in.nextLine();
			String roomDescription = in.nextLine();
			room.setRoomID(roomID);
			room.setDescription(roomDescription);
			room.setName(roomName);
			in.nextLine();
			
			String next = in.nextLine();
			do {
				int itemID = Integer.parseInt(next);
				roomItems.add(itemID);
				next = in.nextLine();
			} while(!next.equalsIgnoreCase("-"));
			room.setItems(roomItems);
			
			next = in.nextLine();
			do{
				Exit exit = new Exit();
				exit.buildExit(next);
				roomExits.add(exit);
				next = in.nextLine();
			} while(!next.equalsIgnoreCase("-"));
			room.setExits(roomExits);
			
			rooms.add(room);
		}
		
		in.close();
	}
	
	public void updateRoom(Room rm) throws GameException {
		//TODO: Updates the room in the current map throws an exception if the room is not found
	}
}
