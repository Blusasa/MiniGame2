package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gameExceptions.GameException;

public class Commands {
	private static int EXIT_COMMAND;
	protected static List<Character> ITEM_COMMANDS;
	private Player player;
	protected static List<Character> VALID_DIRECTIONS;
	
	public static void main(String[] args) {
		Commands cmds = new Commands();
		
		//Use sword
		//look sword
		//remove sword
	}
	
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
		case 1:
			return move(cmdsArr[0]);
		case 2: case 3: case 4:
			return itemCommand(cmd);
		}
	}
	
	private String get(String cmd, Room room) {
		
	}
	
	private String itemCommand(String cmd) {
		String[] cmdsArr = cmd.split(" ");
		String cmdType = cmdsArr[0].substring(0, 1);
		String item = cmdsArr[1];
		
		if(cmdType.equalsIgnoreCase("r")) {
			return remove(item, player.getCurRoom());
		}
		
	}
	
	private String lookItem(String cmd, Room room) {}
	
	private String move(String cmdRoom) {
		
	}
	
	private String remove(String cmd, Room room) {
		
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
