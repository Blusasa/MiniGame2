	package view;

import java.util.Scanner;

import controller.GameController;
import gameExceptions.GameException;

public class Adventure {
	private GameController gc;
	private Scanner in;
	
	public Adventure() {
		gc = new GameController();
	}
	
	private String getCommand() {
		in = new Scanner(System.in);
		String cmd = in.nextLine();
		in.close();
		return cmd;
	}
	
	private void playGame() {
		System.out.println("Welcome to my game. You will go through different rooms and use different items as you try to reach the end");
		System.out.println("You can navigate with all cardinal directions and up/down");
		System.out.println("You can grab, look, and remove items. You can also view your backpack.");
		System.out.println("All commands except any of the whole words above or their first letter. Press X at any time to quit the game");
		System.out.println("=======================================================================================");
		
		try {
			gc.displayFirstRoom();
		} catch (GameException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void main(String[] args) {
		Adventure adventure = new Adventure();
		in = Scanner(System.in);
	}
}
