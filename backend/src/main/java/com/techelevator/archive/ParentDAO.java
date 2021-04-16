package com.techelevator.archive;

import java.util.List;

import com.techelevator.model.Child;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;

public interface ParentDAO {
	
//	boolean addNewParent(Parent parent);
	
	User getParentById(int parentUserId);
	
	boolean giveBirth(RegisterUserDTO newUser, int parentUserId);
	
//	int getParentIdByUserId(int parentUserId);
}
