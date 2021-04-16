package com.techelevator.archive;

import java.sql.Date;
import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;

@Service
public class ParentSqlDAO implements ParentDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public ParentSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
	}
//	@Override
//	public boolean addNewParent(Parent parent) {
//		
//		
//		String sqlAddNewParent = "INSERT INTO parents (parent_user_id, email, dob) VALUES (?, ?, ?)";
//		
//		
//		int result = jdbcTemplate.update(sqlAddNewParent, parent.getUserId(), parent.getEmail(), parent.getDateOfBirth());
//		
//		if (result == 1) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	@Override
	public User getParentById(int parentUserId) {
		User parent = new User();
		
		String sqlGetParent = "SELECT * FROM users WHERE user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParent, parentUserId);
		
		if (results.next()) {
			parent = mapRowToUser(results);
		}
		return parent;
	}
	
	@Override 
	public boolean giveBirth(RegisterUserDTO newUser, int parentUserId) {	//need to include parentID
		
		String username = newUser.getUsername();
		String password = newUser.getPassword();
		String role = newUser.getRole();
		String firstName = newUser.getFirstName();
		String lastName = newUser.getLastName();
		String email = newUser.getEmail();
		Date dob = Date.valueOf(newUser.getDateOfBirth());
		boolean status = newUser.isActivated();
		
		
		boolean userCreated = false;

        // create user
        String insertUser = "INSERT INTO users (username, password_hash, role, first_name, last_name, email, dob, active) " +
				"VALUES (?,?,?,?,?,?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = "ROLE_" + role.toUpperCase();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String id_column = "user_id";
        userCreated = jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(insertUser, new String[]{id_column});
                    ps.setString(1, username);
                    ps.setString(2, password_hash);
                    ps.setString(3, ssRole);
                    ps.setString(4, firstName);
                    ps.setString(5, lastName);
                    ps.setString(6, email);
                	ps.setDate(7, dob);
                    ps.setBoolean(8, status);
                    return ps;
                }
                , keyHolder) == 1;
        
        int newUserId = (int) keyHolder.getKeys().get(id_column);
        
        boolean childInsertResults = createNewChild(newUserId, parentUserId);
       
        
        return childInsertResults;
	}
	
	// HELPER 
	private boolean createNewChild(int childUserId, int parentUserId) {
		int childInsertResults = -999;
		int parentChildInsertResults = -1;
		
		String insertChild = "INSERT INTO children (child_user_id) VALUES (?)";
		
		childInsertResults = jdbcTemplate.update(insertChild, childUserId);

		String insertParentChild = "INSERT INTO parent_child (parent_user_id, child_user_id) VALUES (?, ?)";

		parentChildInsertResults = jdbcTemplate.update(insertParentChild, parentUserId, childUserId);
		
		boolean closetInsertResults = createNewCloset(childUserId);
		
		if (parentChildInsertResults == childInsertResults) {
			return true;			
		} else {
			return false;
		}
	}

//	// HELPER
//	private int getChildId(int userId) {
//		int childId = -1;
//		
//		String sqlChildId = "SELECT child_id FROM children WHERE user_id = ?;";
//        
//        SqlRowSet childIdResults = jdbcTemplate.queryForRowSet(sqlChildId, userId);
//        
//        if (childIdResults.next()) {
//        	childId = childIdResults.getInt("child_id");
//        }
//        
//		return childId;
//	}
	
	// HELPER
	private boolean createNewCloset(int childUserId) {
		
		String insertCloset = "INSERT INTO closets (closet_id, child_user_id)"
				+ "VALUES (DEFAULT, ?)";

		int closetInsertResults = jdbcTemplate.update(insertCloset, childUserId);
		
		if (closetInsertResults == 1) {
			return true;
		}else {
			return false;
		}
	}
	// HELPER
	private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(rs.getString("role"));
//        user.setActivated(true);
        // Added by Matt T.
        	user.setActivated(rs.getBoolean("active"));
        	user.setFirstName(rs.getString("first_name"));
        	user.setLastName(rs.getString("last_name"));
        	user.setEmail(rs.getString("email"));
        	user.setDateOfBirth(rs.getDate("dob").toLocalDate());
        
        return user;
    }
	
//	private Parent mapRowToParent(SqlRowSet rs) {
//		Parent p = new Parent();
//		
//		p.setUserId(rs.getInt("parent_user_id"));
//		p.setEmail(rs.getString("email"));
//		p.setDateOfBirth(rs.getDate("dob").toLocalDate());
//		
//		return p;
//		
//	}
	
//	// HELPER
//	public int getParentIdByUserId(int userId) {
//		String sqlGetParentId = "SELECT parent_id FROM parents WHERE user_id = ?";
//		
//		
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParentId, userId);
//		
//		if (results.next()) {
//			return results.getInt("parent_id");
//		} else {
//			return -1;
//		}
//	}

}
