package com.techelevator.dao;
 
import java.util.List;

import com.techelevator.model.Child;

public interface ChildDAO {

	Child getChildById(int childUserId);
	
	Child updateChild(Child child);
	
	List<Child> getChildrenByParentId(int parentUserId);
	
}
