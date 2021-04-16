package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Closet;
import com.techelevator.model.Item;

@Service
public class ClosetSqlDAO implements ClosetDAO {
	
	private JdbcTemplate jdbcTemplate;

	public ClosetSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Closet viewCloset(int childUserId) {
		//List<Item> closetItems = new ArrayList<>();
		Closet theCloset = new Closet();
		String sqlGetCloset = "Select * From closets Join items Using (item_id) Where child_user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCloset, childUserId);
		
		List<Item> closetItems = new ArrayList<>();
		
		while (results.next()) {
			Item closetItem = new Item();
			closetItem = mapRowToItem(results);
			closetItems.add(closetItem);
		}
//		if (theCloset.getChildUserId() == childUserId) {
//		String sqlGetClosetItems = "SELECT children.child_user_id, items.item_id, items.item_type, items.item_style, items.item_color, items.item_price, items.description"
//								 + "From child_closet JOIN items USING (item_id) WHERE child_user_id = ?";	
//		SqlRowSet closetItemsResults = jdbcTemplate.queryForRowSet(sqlGetClosetItems, childUserId);
//		while (closetItemsResults.next()) {
//				closetItems.add(mapRowToItem(closetItemsResults));
//			}		
//			theCloset.setClosetItems(closetItems);
//		}			
		theCloset.setClosetItems(closetItems);
		return theCloset;
	}
			  		
					

	private Item mapRowToItem(SqlRowSet rs) {
		Item closetItem = new Item();
		closetItem.setItemId(rs.getInt("item_id"));
		closetItem.setType(rs.getString("item_type"));
		closetItem.setStyle(rs.getString("item_style"));
		closetItem.setColor(rs.getString("item_color"));
		closetItem.setPrice(rs.getInt("item_price"));
		closetItem.setDescription(rs.getString("description"));
		return closetItem;
		
	}

	private Closet mapRowToCloset(SqlRowSet rs) {
		Closet theCloset = new Closet();
		theCloset.setClosetId(rs.getInt("closet_id"));
		theCloset.setChildUserId(rs.getInt("child_user_id"));
		theCloset.setItemId(rs.getInt("item_id"));
		theCloset.setEquipped(rs.getBoolean("equipped"));
		return theCloset;
	}
}