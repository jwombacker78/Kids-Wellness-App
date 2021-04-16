package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Child;

@Service
public class ChildSqlDAO implements ChildDAO {

private JdbcTemplate jdbcTemplate;
	
	
	public ChildSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public Child getChildById(int childUserId) {
		Child theBrat = new Child();
		
		String sqlGetChildById = "SELECT * FROM children JOIN users ON (users.user_id) = (children.child_user_id) JOIN parent_child USING (child_user_id) WHERE child_user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetChildById, childUserId);
		
		if (results.next()) {
			theBrat = mapRowToChild(results); 
		}
		return theBrat;
	}

  
	@Override
	public Child updateChild(Child child) {
		
		String sqlUpdateChild = "UPDATE children SET steps = ?, playtime_balance = ?, carrot_balance = ?, " + 
								"activity_minutes = ?, mascot_id = ? WHERE child_user_id = ?";
		
		jdbcTemplate.update(sqlUpdateChild, child.getSteps(), child.getPlaytimeBalance(), child.getCarrotBalance(),
											child.getActivityMinutes(), child.getMascotId(), child.getId());
		
		return getChildById(child.getId());

	}
	
	@Override
	public List<Child> getChildrenByParentId(int parentUserId) {
		
		List<Child> children = new ArrayList<>();
		
		String sqlGetChildrenByUserId = "Select * From children Join parent_child using (child_user_id) join users on (children.child_user_id) = (users.user_id) Where parent_user_id = ?";
		// The above query selects all child user information by joining parent_child and users table to children
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetChildrenByUserId, parentUserId);
		
		while(results.next()) {
			children.add(mapRowToChild(results));
		}
		
		return children;
	}  
	
	private Child mapRowToChild(SqlRowSet rs) {
		Child child = new Child();
		child.setId(rs.getInt("child_user_id"));
		child.setUsername(rs.getString("username"));
		child.setActive(rs.getBoolean("active"));
    	child.setFirstName(rs.getString("first_name"));
    	child.setLastName(rs.getString("last_name"));
    	child.setEmail(rs.getString("email"));
		child.setSteps(rs.getInt("steps"));
		child.setActivityMinutes(rs.getInt("activity_minutes"));
		child.setCarrotBalance(rs.getInt("carrot_balance"));
		child.setPlaytimeBalance(rs.getInt("playtime_balance"));
		child.setAuthorities(rs.getString("role"));
		child.setParentId(rs.getInt("parent_user_id"));
		child.setMascotId(rs.getInt("mascot_id"));
		return child;
	}
}
