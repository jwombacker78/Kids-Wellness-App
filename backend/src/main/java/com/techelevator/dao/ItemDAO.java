package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Item;

public interface ItemDAO {
	
	List<Item> getAllItems();

	Item getItemById(int itemId);

}
