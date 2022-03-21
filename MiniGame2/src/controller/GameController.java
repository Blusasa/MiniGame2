package controller;

import gameExceptions.GameException;
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
		//TODO: Handles the print map command from Adventure Builds a String representation of the current map
		return "";
	}
}
