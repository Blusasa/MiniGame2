package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.Item;
import gameExceptions.GameException;

public class ItemDB {
	private static ItemDB instance;
	private List<Item> items;
	
	private ItemDB() throws GameException {
		this.items = new ArrayList<>();
	}
	
	public static ItemDB getInstance() throws GameException{
		if(instance == null) {
			instance = new ItemDB();
		}
		
		return instance;
	}
	
	public Item getItem(int id) throws GameException {
		Optional<Item> item = items.stream().filter(i -> i.getItemID() == id).findFirst();
		
		if(item.isEmpty()){
			throw new GameException("Item doesn't exit");
		}
		
		return item.get();
	}
	
	public void readItems() throws GameException{
		items.forEach(i -> System.out.println(i.toString()));
	}
}
