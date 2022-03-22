package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
		
		//immediately set visited to true after confirmed valid move. This is in case the user inputs multiple commands before moving, the game can display the room again as visited. 
		RoomDB.getInstance().getRoom(player.getCurRoom()).setVisisted(true);
		
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
		//Stream to either get the item by name or throw exception that it isn't in the room
		Item item = room.getRoomItems().stream().filter(i -> i.getItemName().equalsIgnoreCase(cmd))
				.findFirst()
				.orElseThrow(() -> new GameException(cmd + " is not in this room"));
		
		//give the item to the player
		player.addItem(item);
		//remove the item from the room
		room.removeItem(item);
		
		//return String to signal that the item was successfully grabbed
		return cmd + " has been added to your inventory";
	}
	
	private String itemCommand(String cmd) throws GameException {
		String[] cmdsArr = cmd.split(" ");
		String cmdType = cmdsArr[0].substring(0, 1).toUpperCase();
		
		//the name of a Item could include whitespace like "Sword of Person", so this grabs the substring from the full commandLine to include the full name to match Item name in DB
		//EX: "g Sword of Person" -> this sets to Sword of Person. Item name is used for all item commands so this is needed.
		String cmdArg = cmd.substring(cmdsArr[0].length() + 1, cmd.length());
		
		Room curRoom = RoomDB.getInstance().getRoom(player.getCurRoom());
		
		//validating whole words doesn't matter here because if the system has gotten to this point it is a valid command either way so letters are fine
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
		//First check is to see if the item the players want's to inspect is in the room
		Optional<Item> roomItem = room.getRoomItems().stream()
				.filter(r -> r.getItemName().equalsIgnoreCase(cmd))
				.findFirst();
		
		//Second check to see if the player has it in their inventory
		Optional<Item> playerItem = player.getInventory().stream()
				.filter(i -> i.getItemName().equalsIgnoreCase(cmd))
				.findFirst();
		
		//Using Optional<T> isPresent, if neither Optional contains a value, than the item isn't available to be inspected.
		if(!roomItem.isPresent() && !playerItem.isPresent()) {
			throw new GameException(cmd + " is not in the room or your inventory");
		}
		
		//get item from either the Optional room check or player inv check
		Item item = roomItem.isPresent() ? roomItem.get() : playerItem.get();
		
		return item.getItemDescription();
	}
	
	private String move(String cmdRoom) throws GameException {
		RoomDB rdbInst = RoomDB.getInstance();
		int currentRoom = player.getCurRoom();
		
		//gets the room number of the exit destination or throws an exception based on call to valid direction
		int newRoom = rdbInst.getRoom(currentRoom).validDirection(cmdRoom.charAt(0));
		
		//set the new current room for the player
		player.setCurRoom(newRoom);
		
		//display for the new room
		return RoomDB.getInstance().getRoom(player.getCurRoom()).display();
	}
	
	private String remove(String cmd, Room room) throws GameException {
		//Using streams to cleanly either get the Item by name or throw an exception that it isn't in the player's inventory
		Item item = player.getInventory().stream().filter(i -> i.getItemName().equalsIgnoreCase(cmd))
				.findFirst()
				.orElseThrow(() -> new GameException("Item not in your inventory"));
		
		//if found the item is removed from the player and added to the room to be interacted with later
		player.removeItem(item);
		room.dropItem(item);
		return "The " + item.getItemName() + " has been dropped";
	}
	
	private int validateCommand(String cmdLine) throws GameException {
		//method broke into 2 parts. Single letter or full word validation. This is to prevent allowing commands that shouldn't pass. EX: Normal for North would pass on the 'N' case
		//flag to signify matching command
		boolean match = false;
		
		//run through checks of single chars ONLY if the length of the command is one. 
		if(cmdLine.length() == 1) {
			//convert string letter to char at upper case for testing
			char abbrv = cmdLine.toUpperCase().charAt(0);
			
			//check directions list
			match = VALID_DIRECTIONS.stream().anyMatch(c -> c == abbrv);
			if(match) return 1;
			
			//check item commands list
			match = ITEM_COMMANDS.stream().anyMatch(c -> c == abbrv);
			if(match) return 2;
			
			//3 commands aren't in list: look, backpack, exit. Checks for these
			switch(abbrv) {
			case 'L':
				return 3;
			case 'B':
				return 4;
			case 'X':
				return 0;
			}
			
			//is repetitive from bottom but avoids unnecessary checks that will never pass below anyways
			throw new GameException("Invalid Command! Try again");
		}
		
		//convert cmdLine to upper case for matching purposes
		String upperCmd = cmdLine.toUpperCase();
		//if cmdLine isn't 1 letter, than only the full word should be allowed to pass. This starts with directional commands
		match = Arrays.asList("North", "South", "East", "West", "Up", "Down").stream().anyMatch(s -> s.equalsIgnoreCase(upperCmd));
		if(match) return 1;
		
		//runs through item commands
		match = Arrays.asList("Grab", "Remove", "Inspect").stream().anyMatch(s -> s.equalsIgnoreCase(upperCmd));
		if(match) return 2;
		
		//checks individual commands
		switch(upperCmd) {
		case "LOOK":
			return 3;
		case "BACKPACK":
			return 4;
		case "EXIT":
			return 0;
		}
		
		//if nothing was found in the entire method than it was an invalid command
		throw new GameException("Invalid Command! Try again.");
	}
}
