package controller;

import java.util.List;

public class Commands {
	private static int EXIT_COMMAND;
	protected static List<Character> ITEM_COMMANDS;
	private Player player;
	protected static List<Character> VALID_DIRECTIONS;
	
	public Commands() {}
	
	protected String executeCommand(String cmd) {}
	
	private String get(String cmd, Room room) {}
	
	private String itemCommand(String cmd) {}
	
	private String lookItem(String cmd, Room room) {}
	
	private String move(String cmdRoom) {}
	
	private String remove(String cmd, Room room) {}
	
	private int validateCommand(String cmdLine) {}
	
}
