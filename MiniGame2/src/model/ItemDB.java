package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		return items.stream().filter(i -> i.getItemID() == id)
				.findFirst()
				.orElseThrow(() -> new GameException("Item with ID number: " + id + " not found"));
	}
	
	public void readItems() throws GameException{
		String filePath = "/items.txt";
		
		File file = new File(filePath);
		Scanner in;
		
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new GameException("Items file not found");
		}
		
		while(in.hasNext()) {
			Item item = new Item();
			
			int itemID = Integer.parseInt(in.nextLine());
			String itemName = in.nextLine();
			String description = in.nextLine();
			
			item.setItemID(itemID);
			item.setItemName(itemName);
			item.setItemDescription(description);
			
			items.add(item);
		}
		
		in.close();
	}
}
