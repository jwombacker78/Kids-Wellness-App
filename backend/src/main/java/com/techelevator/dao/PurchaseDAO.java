package com.techelevator.dao;

import com.techelevator.model.Child;
import com.techelevator.model.Item;
import com.techelevator.model.Mascot;

public interface PurchaseDAO {
	
	Item purchaseItem(Item itemToPurchase, Child theChild);
	boolean purchaseMascot(Mascot mascot, int childUserId);

}
