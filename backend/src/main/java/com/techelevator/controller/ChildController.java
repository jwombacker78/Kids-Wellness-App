package com.techelevator.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.techelevator.dao.ChildDAO;
import com.techelevator.dao.ClosetDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Child;
import com.techelevator.model.Closet;
import com.techelevator.model.User;

@RestController
@CrossOrigin
public class ChildController {
	
	private ChildDAO childDAO;
	private UserDAO userDAO;
	private ClosetDAO closetDAO;
	
	public ChildController(ClosetDAO closetDAO, ChildDAO childDAO, UserDAO userDAO) {
		this.childDAO = childDAO;
		this.userDAO = userDAO;
		this.closetDAO = closetDAO;
	}
	
	@RequestMapping(path = "/sub/{childUserId}", method = RequestMethod.GET)
	public Child getChildById(@PathVariable int childUserId) {		 
		  
		return childDAO.getChildById(childUserId);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(path = "/sub/{childUserId}", method = RequestMethod.PUT)
	public Child updateChild(@PathVariable int childUserId, @RequestBody Child child, Principal p) {
		
		return childDAO.updateChild(child);			

	}
	
	@RequestMapping(path = "/sub/{childUserId}/closet", method = RequestMethod.GET)
	public Closet getChildCloset(@PathVariable int childUserId, @RequestBody Child child, Principal p) {
		
		int userId = userDAO.findIdByUsername(p.getName());
		
		if (userId == child.getParentId()) {
			return closetDAO.viewCloset(child.getId());
		} else {
			return null;
		}
	}
	
	@RequestMapping(path = "/sub/{childUserId}/closet/customize", method = RequestMethod.GET)
	public Closet customizeMascot(@PathVariable int childUserId, @RequestBody Child child, Principal p) {
		Closet thisCloset = new Closet();
		
		int userId = userDAO.findIdByUsername(p.getName());
		
		if (userId == child.getParentId()) {
			// mascotDAO.customizeMascot();
		}
		
		
		return thisCloset;
	}

}

	
	


// Request Body should have a child object to get parentId 
// Add principal object validate user by parentId or childId