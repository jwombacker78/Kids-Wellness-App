package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Child;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;

public class ChildrenSqlDAOIntegrationTest extends DAOIntegrationTest {
	
	private ChildSqlDAO childSqlDao;
	private RegisterUserDTO testChild;
	
	private UserSqlDAO userSqlDao;
	private User testUser;
	
	private RegisterUserDTO newUser;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        userSqlDao = new UserSqlDAO(jdbcTemplate);
        childSqlDao = new ChildSqlDAO(jdbcTemplate);
        
        userSqlDao = new UserSqlDAO(jdbcTemplate);
        newUser = new RegisterUserDTO();
        newUser.setUsername("MALL_COP_4_LYFE");
        newUser.setPassword("LOCKDOWN");
        newUser.setRole("USER");
        newUser.setFirstName("Paul");
        newUser.setLastName("Blart");
        newUser.setEmail("mallcop@email.com");
        testUser = userSqlDao.create(newUser);
        
        testChild = new RegisterUserDTO();
        testChild.setUsername("JimmyNeutron");
        testChild.setPassword("ILOVECINDY");
        testChild.setRole("CHILD");
        testChild.setFirstName("Jimmy");
        testChild.setLastName("Neutron");
        testChild.setEmail("jneutron@fake.com");
        
	}
	
	@Test
	public void updateChild_changes_steps_and_minutes_correctly() {
		
		Child theTestChild = userSqlDao.giveBirth(testChild, testUser.getId());
		Child theStepChild = new Child();
		
		theStepChild.setId(theTestChild.getId());
		theStepChild.setSteps(100);
		theStepChild.setPlaytimeBalance(100);
		theStepChild.setActivityMinutes(100);
		theStepChild.setCarrotBalance(100);
		
		childSqlDao.updateChild(theStepChild);
		theTestChild = childSqlDao.getChildById(theTestChild.getId());
		
		Assert.assertEquals(100, theTestChild.getSteps());
		Assert.assertEquals(100, theTestChild.getPlaytimeBalance());
		Assert.assertEquals(100, theTestChild.getActivityMinutes());
		Assert.assertEquals(100, theTestChild.getCarrotBalance());
	}
	
	@Test
	public void getChildrenByParentId_returns_a_list_of_children() {
		
		Child theTestChild = userSqlDao.giveBirth(testChild, testUser.getId());
		RegisterUserDTO testChild2 = new RegisterUserDTO();
		testChild2 = new RegisterUserDTO();
        testChild2.setUsername("TimmyTurner");
        testChild2.setPassword("FairlyOdd");
        testChild2.setRole("CHILD");
        testChild2.setFirstName("Timmy");
        testChild2.setLastName("Turner");
        testChild2.setEmail("tturner@fake.com");
		Child theTestChild2 = userSqlDao.giveBirth(testChild2, testUser.getId());
		
		List<Child> testList = new ArrayList<>();
		testList.add(theTestChild);
		testList.add(theTestChild2);
		
		List<Child> actualList = childSqlDao.getChildrenByParentId(testUser.getId());
		//actualList
		
		Assert.assertEquals(testList.toString(), actualList.toString());
		Assert.assertEquals(testList.get(0).getFirstName(), actualList.get(0).getFirstName());
		//ASsert.assertEquals()
	}
	
	@Test
	public void getChildById_returns_a_child() {
		Child expected = userSqlDao.giveBirth(testChild, testUser.getId());
		Child actual = childSqlDao.getChildById(expected.getId());

		Assert.assertEquals(expected.toString(),actual.toString());
	}
	
	public int getUserId(String username) {
		String sqlGetUserId = "SELECT user_id FROM users WHERE username = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetUserId, username);
		
		if(results.next()) {
			return results.getInt("user_id");
		} else {
			return -1;
		}
		
	}
	
	public int getParentId(int userId) {
		String sqlGetParentId = "SELECT parent_id FROM parents WHERE user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParentId, userId);
		
		if (results.next()) {
			return results.getInt("parent_id");
		} else {
			return -1;
		}
	}
	
	private int getChildId(int userId) {
		int childId = -1;
		
		String sqlChildId = "SELECT child_id FROM children WHERE user_id = ?;";
        
        SqlRowSet childIdResults = jdbcTemplate.queryForRowSet(sqlChildId, userId);
        
        if (childIdResults.next()) {
        	childId = childIdResults.getInt("child_id");
        }
        
		return childId;
	}

}
