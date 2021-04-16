package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Item;

@Service
public class ItemSqlDAO implements ItemDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public ItemSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		List<Item> allItems = new ArrayList<>();
		
		String sqlGetAllItems = "Select * From Items";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllItems);
		
		while (results.next()) {
			allItems.add(mapRowToItem(results));
		}
		return allItems;
	}
	
	@Override
	public Item getItemById(int itemId) {
		Item theItem = new Item();
		
		String sqlGetItemById = "Select * From Items Where item_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetItemById, itemId);
		
		if (results.next()) {
			theItem = mapRowToItem(results);
		}
		return theItem;
	}
	
	private Item mapRowToItem(SqlRowSet rs) {
		Item item = new Item();
		item.setItemId(rs.getInt("item_id"));
		item.setType(rs.getString("item_type"));
		item.setStyle(rs.getString("item_style"));
		item.setColor(rs.getString("item_color"));
		item.setPrice(rs.getInt("item_price"));
		item.setDescription(rs.getString("description"));
		
		return item;
	}

}
