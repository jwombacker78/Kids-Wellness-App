package com.techelevator.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.techelevator.archive.Parent;
import com.techelevator.archive.ParentDAO;
import com.techelevator.dao.ChildDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Child;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;
import com.techelevator.model.UserAlreadyExistsException;

@RestController
@CrossOrigin
public class ParentController {

	// private ParentDAO parentDAO;
	private ChildDAO childDAO;
	private UserDAO userDAO;

	public ParentController(/* ParentDAO parentDAO, */ ChildDAO childDAO, UserDAO userDAO) {
		// this.parentDAO = parentDAO;
		this.childDAO = childDAO;
		this.userDAO = userDAO;
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@RequestMapping(path = "/user/{parentUserId}", method = RequestMethod.GET)
	public User getParentByUserId(@PathVariable int parentUserId) {

		return userDAO.getUserById(parentUserId);
		// return parentDAO.getParentById(parentUserId);
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@RequestMapping(path = "/user/{parentUserId}/list", method = RequestMethod.GET)
	public List<Child> getChildrenByUserId(@PathVariable int parentUserId, Principal p) {
		
//		int userId = userDAO.findIdByUsername(p.getName()); // userId of Principal (person making request)
//		
//		if (parentUserId == userId) { // if person making the request is the same as the parentId in path
			List<Child> output = childDAO.getChildrenByParentId(parentUserId);
			return output;
//		}
//		else {
//			return null;
//		}


	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "user/{parentId}/add-sub", method = RequestMethod.POST)
	public Child giveBirth(@Valid @RequestBody RegisterUserDTO newUser, @PathVariable int parentId, Principal p) {

//		int userId = userDAO.findIdByUsername(p.getName()); // userId of Principal (person making request)
//
//		if (parentId == userId) { // if person making the request is the same as the parentId in path

			try {
				User user = userDAO.findByUsername(newUser.getUsername());
				throw new UserAlreadyExistsException();
			} catch (UsernameNotFoundException e) {
				return userDAO.giveBirth(newUser, parentId);
			}
//		} else {
//			return null;
//		}
	}
}
