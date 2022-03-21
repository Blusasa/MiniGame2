	package view;

import java.util.Scanner;

import controller.GameController;
import gameExceptions.GameException;
import model.ItemDB;
import model.RoomDB;

public class Adventure {
	private GameController gc;
	private Scanner in;
	
	public Adventure() {
		gc = new GameController();
	}
	
	private String getCommand() {
		return in.nextLine();
	}
	
	private void playGame() {

		System.out.println("Welcome to my game. You will go through different rooms and use different items as you try to reach the end");
		System.out.println("You can navigate with all cardinal directions and up/down");
		System.out.println("You can grab, look, and remove items. You can also view your backpack.");
		System.out.println("All commands except any of the whole words above or their first letter. Press X at any time to quit the game");
		System.out.println("=======================================================================================");
		
		try {
			System.out.println(gc.displayFirstRoom());
		} catch (GameException e1) {
			System.out.println(e1.getMessage());
		}
		
		while(true) {
			String input = getCommand();
			String result = "";
			
			try {
				result = gc.executeCommand(input);
			} catch (GameException e) {
				System.out.println(e.getMessage());
			}
			
			//If they player wishes to exit the game, the loop will end and the program will advance to the "ending" code
			if(result.equalsIgnoreCase("X")) return;
			System.out.println(result);
		}
	}
	
	public static void main(String[] args) {
		//initialize the view object the user will interact with
		Adventure adventure = new Adventure();
		
		//initialize a scanner and set the scanner of the view object to this scanner
		Scanner input = new Scanner(System.in);
		adventure.in = input;
		
		//load in the Items and rooms IN THAT ORDER ALWAYS, before beginning the game
		try {
			ItemDB.getInstance();
		} catch (GameException e) {
			System.out.println(e.getMessage());
			input.close();
			return;
		}
		RoomDB.getInstance();
		
		//start the gameplay loop
		adventure.playGame();
		
		//to be displayed after the loop ends and the game comes to an end
		System.out.println("Thank you for playing my game");
		input.close();
	}
}
