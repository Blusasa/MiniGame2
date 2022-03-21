package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gameExceptions.GameException;
import model.RoomDB;

public class Commands {
	private static int EXIT_COMMAND;
	protected static List<Character> ITEM_COMMANDS;
	private Player player;
	protected static List<Character> VALID_DIRECTIONS;
	
	public Commands() {
		player = new Player();
		EXIT_COMMAND = 0;
		
		VALID_DIRECTIONS = new ArrayList<>();
		Collections.addAll(VALID_DIRECTIONS, 'N', 'S', 'E', 'W', 'U', 'D');
		
		ITEM_COMMANDS = new ArrayList<>();
		Collections.addAll(ITEM_COMMANDS, 'G', 'R', 'I');
	}
	
	protected String executeCommand(String cmd) throws GameException {
		String[] cmdsArr = cmd.split(" ");
		int cmdType = validateCommand(cmdsArr[0]);
		
		switch(cmdType) {
		//direction command
		case 1:
			return move(cmdsArr[0]);
		//item command
		case 2:
			return itemCommand(cmd);
		//look command
		case 3:
			Room room = RoomDB.getInstance().getRoom(player.getCurRoom());
			return room.display();
		//backpack command
		case 4:
			return player.printInventory();
		//exit command
		case 0:
			return "X";
		}
		
		throw new GameException("Unrecognized command. Try again");
	}
	
	private String get(String cmd, Room room) throws GameException {
		Item item = room.getRoomItems().stream().filter(i -> i.getItemName().equalsIgnoreCase(cmd))
				.findFirst()
				.orElseThrow(() -> new GameException(cmd + " is not in this room"));
		
		player.addItem(item);
		room.removeItem(item);
		return cmd + " has been added to your inventory";
	}
	
	private String itemCommand(String cmd) throws GameException {
		String[] cmdsArr = cmd.split(" ");
		String cmdType = cmdsArr[0].substring(0, 1).toUpperCase();
		
		//the name of a Item could include whitespace like "Sword of Person", so this grabs the substring from the full commandLine to include the full name to match Item name in DB
		String cmdArg = cmd.substring(cmdsArr[0].length() + 1, cmd.length());
		
		Room curRoom = RoomDB.getInstance().getRoom(player.getCurRoom());
		
		switch(cmdType) {
		case "G":
			return get(cmdArg, curRoom);
		case "R":
			return remove(cmdArg, curRoom);
		case "I":
			return lookItem(cmdArg, curRoom);
		}
		
		return "";
		
	}
	
	private String lookItem(String cmd, Room room) throws GameException {
		Item item = room.getRoomItems().stream().filter(r -> r.getItemName().equalsIgnoreCase(cmd))
				.findFirst()
				.orElseThrow(() -> new GameException(cmd + " isn't in this room"));
		
		return item.getItemDescription();
	}
	
	private String move(String cmdRoom) throws GameException {
		//TODO: implement move rooms
		RoomDB rdbInst = RoomDB.getInstance();
		int currentRoom = player.getCurRoom();
		
		//gets the room number of the exit destination or throws an exception based on call to valid direction
		int newRoom = rdbInst.getRoom(currentRoom).validDirection(cmdRoom.charAt(0));
		rdbInst.getRoom(currentRoom).setVisisted(true);
		player.setCurRoom(newRoom);
		return RoomDB.getInstance().getRoom(player.getCurRoom()).display();
	}
	
	private String remove(String cmd, Room room) throws GameException {
		Item item = player.getInventory().stream().filter(i -> i.getItemName().equalsIgnoreCase(cmd))
				.findFirst()
				.orElseThrow(() -> new GameException("Item not in your inventory"));
		
		player.removeItem(item);
		room.dropItem(item);
		return "The " + item.getItemName() + " has been dropped";
	}
	
	private int validateCommand(String cmdLine) throws GameException {
		
		//very convoluted way of getting the 1st word of the line, converting to upper case to match, and getting the first letter
		char cmd = cmdLine.substring(0, 1).toUpperCase().charAt(0);
		boolean match = false;
		
		match = VALID_DIRECTIONS.stream().anyMatch(c -> c == cmd);
		if(match) return 1;
		
		match = ITEM_COMMANDS.stream().anyMatch(c -> c == cmd);
		if(match) return 2;
		
		
		switch(cmd) {
		case 'L':
			return 3;
		case 'B':
			return 4;
		case 'X':
			return 0;
		}
		
		throw new GameException("Invalid Command! Try again.");
	}
}
