package com.techelevator.model;

import java.util.List;

public class Closet {
	
	private int closetId;
	private int childUserId;
	private int itemId;
	private boolean isEquipped;
	private List<Item> closetItems;
	
	public int getClosetId() {
		return closetId;
	}
	public void setClosetId(int closetId) {
		this.closetId = closetId;
	}
	public int getChildUserId() {
		return childUserId;
	}
	public void setChildUserId(int childId) {
		this.childUserId = childId;
	}
	public List<Item> getClosetItems() {
		return closetItems;
	}
	public void setClosetItems(List<Item> closetItems) {
		this.closetItems = closetItems;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public boolean isEquipped() {
		return isEquipped;
	}
	public void setEquipped(boolean isEquipped) {
		this.isEquipped = isEquipped;
	}

}
