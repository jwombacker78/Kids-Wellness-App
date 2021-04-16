package com.techelevator.dao;

import java.time.LocalDate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Child;
import com.techelevator.model.Item;
import com.techelevator.model.Mascot;

@Service
public class PurchaseSqlDAO implements PurchaseDAO {
	
	private JdbcTemplate jdbcTemplate;
	private ChildSqlDAO childSqlDAO;
	
	
	public PurchaseSqlDAO(JdbcTemplate jdbcTemplate, ChildSqlDAO childSqlDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.childSqlDAO = childSqlDAO;
	}
	
	@Override
	public Item purchaseItem(Item itemToPurchase, Child theChild) {
		
		int thePrice = itemToPurchase.getPrice();
		
		if (thePrice <= theChild.getCarrotBalance()) {
			String sqlPurchaseItem = "INSERT INTO purchases (purchase_id, item_id, child_user_id, purchase_date, payment_amount) VALUES (DEFAULT, ?, ?, ?, ?)";
			jdbcTemplate.update(sqlPurchaseItem, itemToPurchase.getItemId(), theChild.getId(), LocalDate.now(), itemToPurchase.getPrice());
			
			String sqlInsertIntoCloset = "INSERT INTO closets (closet_id, child_user_id, item_id, equipped) VALUES (DEFAULT, ?, ?, DEFAULT)";
			jdbcTemplate.update(sqlInsertIntoCloset, theChild.getId(), itemToPurchase.getItemId());
			
			updateCarrotBalance(theChild, thePrice);
			
			
			return itemToPurchase;
			
		}
		
		return null;
	}
	
	
	@Override
	public boolean purchaseMascot(Mascot mascot, int childUserId) {
		
		boolean purchased = false;
		Child theChild = childSqlDAO.getChildById(childUserId);
		
		if (mascot.getPrice() <= theChild.getCarrotBalance()) {
			String sqlPurchaseItem = "INSERT INTO purchases (purchase_id, mascot_id, child_user_id, purchase_date, payment_amount) VALUES (DEFAULT, ?, ?, ?, ?)";
			jdbcTemplate.update(sqlPurchaseItem, mascot.getId(), theChild.getId(), LocalDate.now(), mascot.getPrice());
			
			String sqlInsertIntoCloset = "INSERT INTO closets (closet_id, child_user_id, mascot_id, equipped) VALUES (DEFAULT, ?, ?, DEFAULT)";
			jdbcTemplate.update(sqlInsertIntoCloset, theChild.getId(), mascot.getId());
			
			if (updateCarrotBalance(theChild, mascot.getPrice())) {
				purchased = true;
			} 
		}					
		return purchased;		
	}
	
	

	private boolean updateCarrotBalance(Child theChild, int thePrice) {
		boolean updatedCarrotBalance = false;
		String sqlGetCarrotBalance = "SELECT carrot_balance FROM children WHERE child_user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCarrotBalance, theChild.getId());
		
		Child newChild = new Child();
		if (results.next()) {
			newChild.setCarrotBalance(results.getInt("carrot_balance"));			
		}
//		System.out.println("\n\n NewChild Balance: " + newChild.getCarrotBalance() + "\n\n");		
//		System.out.println("\n\n thePrice : " + thePrice + "\n\n");
		
		theChild.setCarrotBalance(newChild.getCarrotBalance() - thePrice);
		
//		System.out.println("\n\n theChild Balance: " + theChild.getCarrotBalance() + "\n\n");		
//		System.out.println("\n\n theChild ID: " + theChild.getId() + "\n\n");
		
		
		String sqlUpdateCarrotBalance = "UPDATE children SET carrot_balance = ? WHERE child_user_id = ?";
		int update = jdbcTemplate.update(sqlUpdateCarrotBalance, theChild.getCarrotBalance(), theChild.getId());
		
		if ( update == 1 ) {
			updatedCarrotBalance = true;
		}
		return updatedCarrotBalance;
	}
}
