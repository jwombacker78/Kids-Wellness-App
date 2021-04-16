package com.techelevator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.techelevator.dao.PurchaseDAO;
import com.techelevator.model.Mascot;

@RestController
@CrossOrigin
public class PurchaseController {

	private PurchaseDAO purchaseDAO;

	public PurchaseController(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/shop/{childUserId}", method = RequestMethod.POST)
	public boolean purchaseMascot(@PathVariable int childUserId, @RequestBody Mascot mascot) {
		
		return purchaseDAO.purchaseMascot(mascot, childUserId);
		
	}
	
}
