package com.techelevator.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.model.Child;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;

@Service
public class UserSqlDAO implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    public UserSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        return jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
    }

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if(results.next()) {
			return mapRowToUser(results);
		} else {
			throw new RuntimeException("userId "+userId+" was not found.");
		}
	}

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        for (User user : this.findAll()) {
            if( user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public User create(RegisterUserDTO newUser) {
        
    	User createdUser = new User();
    	boolean userCreated = false;

        // create user
        String username = newUser.getUsername();
		String password = newUser.getPassword();
		String role = newUser.getRole();
		String firstName = newUser.getFirstName();
		String lastName = newUser.getLastName();
		String email = newUser.getEmail();
		boolean status = newUser.isActivated();

        // create user
//        String insertUser = "INSERT INTO users (username, password_hash, role, first_name, last_name, active) VALUES (?,?,?,?,?,?)";
        String insertUser = "INSERT INTO users (username, password_hash, role, first_name, last_name, email, active) " +
        						"VALUES (?,?,?,?,?,?,DEFAULT)";

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
                    
                    return ps;
                }
                , keyHolder) == 1;
        int newUserId = (int) keyHolder.getKeys().get(id_column);
        
        String sqlSelectNewUser = "Select * From users Where user_id = ?";
        
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectNewUser, newUserId);
        while (results.next()) {
        	createdUser = mapRowToUser(results);
        }
  
        return createdUser;
    }
    @Override 
	public Child giveBirth(RegisterUserDTO newUser, int parentUserId) {	//need to include parentID
	

    	User childCreated = create(newUser);
        boolean childInsertResults = createNewChild(childCreated.getId(), parentUserId);

//        String sql = "SELECT c.child_user_id, c.steps, c.playtime_balance, c.carrot_balance, c.activity_minutes, u.username, u.first_name, u.last_name, u.email, u.active FROM children  c JOIN users u ON (u.user_id) = (c.child_user_id) Where child_user_id = ?";
//        
        String sql = "SELECT * FROM children JOIN users ON (users.user_id) = (children.child_user_id) JOIN parent_child USING (child_user_id) WHERE child_user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, childCreated.getId());
        Child theChild = new Child();
        
        while (results.next()) {
        	theChild = mapRowToChild(results);
        }
        if (childInsertResults) {
        	return theChild;  
        } else {
        	return null;
        }
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
		
		if ((parentChildInsertResults == childInsertResults) && closetInsertResults) {
			return true;			
		} else {
			return false;
		}
	}
	// HELPER
		private boolean createNewCloset(int childUserId) {
			
			String insertCloset = "INSERT INTO closets (closet_id, child_user_id, item_id, equipped) VALUES (DEFAULT, ?, DEFAULT, DEFAULT)";

			int closetInsertResults = jdbcTemplate.update(insertCloset, childUserId);
			
//			String getCloset = "SELECT closet_id FROM closets WHERE child_user_id = ?";
//			SqlRowSet srs = jdbcTemplate.queryForRowSet(getCloset, childUserId);
//			int closetId = -1;
//			if(srs.next()) {
//				closetId = srs.getInt("closet_id");
//			}
//			
//			String insertChildCloset = "INSERT INTO child_closet (child_user_id, closet_id, item_id) VALUES (?, ?, DEFAULT)";
//			
//			int childClosetResults = jdbcTemplate.update(insertChildCloset, childUserId, closetId);
			if (closetInsertResults == 1 /*&& childClosetResults == 1*/) {
				return true;
			}else {
				return false;
			}
		}

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(rs.getString("role"));
    	user.setActivated(rs.getBoolean("active"));
    	user.setFirstName(rs.getString("first_name"));
    	user.setLastName(rs.getString("last_name"));
    	user.setEmail(rs.getString("email"));
        
        return user;
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
