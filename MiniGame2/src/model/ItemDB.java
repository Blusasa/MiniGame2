package model;

import java.util.List;

import gameExceptions.GameException;

public class ItemDB {
	private static ItemDB instance;
	private List<Item> items;
	
	private ItemDB() throws GameException {}
	
	public static ItemDB getInstance() throws GameException{
		
	}
	
	public Item getItem(int id) throws GameException {
		
	}
	
	public void readItems() throws GameException{
		
	}
}
