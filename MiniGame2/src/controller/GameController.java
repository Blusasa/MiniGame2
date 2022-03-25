package controller;

import gameExceptions.GameException;
import model.ItemDB;
import model.RoomDB;

public class GameController {
	private Commands commands;
	static int FIRST_ROOM;
	
	public GameController() {
		commands = new Commands();
		FIRST_ROOM = 0;
	}
	
	public String displayFirstRoom() throws GameException{
		return RoomDB.getInstance().getRoom(FIRST_ROOM).display();
	}
	
	public String executeCommand(String cmd) throws GameException{
		return commands.executeCommand(cmd);
	}
	
	public String printMap() throws GameException {
		//this method gets called in the main method of the program and the items have to be loaded before the rooms so calling getInstance will load in the items
		ItemDB.getInstance();
		return RoomDB.getInstance().getMap();
	}
}
