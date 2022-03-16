package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return instance;
	}
	
	public List<Item> getItems(int roomID) throws GameException{
		Optional<Room> roomOpt = rooms.stream().filter(r -> r.getRoomID() == roomID).findFirst();
		
		if(roomOpt.isEmpty()) {
			throw new GameException("Room with ID: " + roomID + " doesn't exist");
		}
		
		Room room = roomOpt.get();
		
		if(room.getRoomItems().isEmpty()) {
			throw new GameException("Room contains no items");
		}
		
		return room.getRoomItems();
	}
	
	public String getMap() {
		//TODO: wtf is a string map
		return "";
	}
	
	public Room getRoom(int roomID) throws GameException {
		Optional<Room> room = rooms.stream().filter(r -> r.getRoomID() == roomID).findFirst();
		
		if(room.isEmpty()) {
			throw new GameException("Room doens't exist");
		}
		
		return room.get();
	}
	
	public void readRooms() throws GameException{
		String filePath = "/Rooms.txt";
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
			
			while(!in.nextLine().contains("--")) {
				int itemID = Integer.parseInt(in.nextLine());
				roomItems.add(itemID);
			}
			room.setItems(roomItems);
			
			while(!in.nextLine().contains("--")) {
				Exit exit = new Exit();
				exit.buildExit(in.nextLine());
			}
			room.setExits(roomExits);
			
			rooms.add(room);
		}
		
		in.close();
	}
	
	public void updateRoom(Room rm) throws GameException {
		//TODO: I have no fucking idea between this and the map
	}
}
