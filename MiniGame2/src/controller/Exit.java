package controller;

import java.util.ArrayList;
import java.util.List;

import gameExceptions.GameException;

public class Exit {
	private int destination; 
	private String direction;
	private List<String> VALID_DIRECTIONS;
	
	public Exit() {
		this.VALID_DIRECTIONS = new ArrayList<>();
		VALID_DIRECTIONS.add("NORTH");
		VALID_DIRECTIONS.add("SOUTH");
		VALID_DIRECTIONS.add("EAST");
		VALID_DIRECTIONS.add("WEST");
		VALID_DIRECTIONS.add("UP");
		VALID_DIRECTIONS.add("DOWN");
	}
	
	public void buildExit(String ex) throws GameException{
		String[] details = ex.split(" ");
		String direction = details[0].toUpperCase();
		int destination = Integer.parseInt(details[1]);
		
		boolean validDir = VALID_DIRECTIONS.stream().anyMatch(d -> direction.equalsIgnoreCase(d));
		if(!validDir) {
			throw new GameException("Exit construction not valid");
		}
		
		this.destination = destination;
		this.direction = direction;
	}
	
	public int getDestination() {
		return this.destination;
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	public void setDestination(int destination) {
		this.destination = destination;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		return this.direction + " " + this.destination;
	}
}
