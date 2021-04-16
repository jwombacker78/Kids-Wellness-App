package com.techelevator.model;

import java.util.List;

public class Mascot {
	
	private int id;
	private String name;
	private int price;

//	private List<Item> equippedItems;
	
	public int getId() {
		return id;
	}
	public void setId(int mascotId) {
		this.id = mascotId;
	}
	public String getName() {
		return name;
	}
	public void setName(String mascotName) {
		this.name = mascotName;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPrice() {
		return price;
	}
//	public List<Item> getEquippedItems() {
//		return equippedItems;
//	}
//	public void setEquippedItems(List<Item> equippedItems) {
//		this.equippedItems = equippedItems;
//	}
	
	

}
