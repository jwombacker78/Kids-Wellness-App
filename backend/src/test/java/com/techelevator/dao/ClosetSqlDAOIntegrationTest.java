package com.techelevator.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.archive.Parent;
import com.techelevator.archive.ParentSqlDAO;
import com.techelevator.model.Child;
import com.techelevator.model.Closet;
import com.techelevator.model.Item;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;



public class ClosetSqlDAOIntegrationTest extends DAOIntegrationTest{
	
	private ClosetSqlDAO closetSqlDao;
	private ChildSqlDAO childSqlDao;
	private ItemSqlDAO itemSqlDao;
	private RegisterUserDTO testChild;
	
	private UserSqlDAO userSqlDao;
	private User testUser;
	
	private RegisterUserDTO newUser;
	
	private JdbcTemplate jdbcTemplate;
	
	private Closet testCloset;
	
	private Child theTestChild;
	
	@Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        closetSqlDao = new ClosetSqlDAO(jdbcTemplate);
        childSqlDao = new ChildSqlDAO(jdbcTemplate); 
        itemSqlDao = new ItemSqlDAO(jdbcTemplate);
        userSqlDao = new UserSqlDAO(jdbcTemplate);

        userSqlDao = new UserSqlDAO(jdbcTemplate);
        newUser = new RegisterUserDTO();
        newUser.setUsername("MALL_COP_4_LYFE");
        newUser.setPassword("LOCKDOWN");
        newUser.setRole("USER");
        LocalDate date = LocalDate.of(2020, 1, 8);
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
        theTestChild = userSqlDao.giveBirth(testChild, testUser.getId());

	}

	@Test
	public void viewCloset_returns_a_closet() {
		String sqlInsertItemsIntoCloset = "Insert Into closets (closet_id, child_user_id, item_id, equipped)"
										+ "Values (DEFAULT, ?, ?, ?)";
		
		jdbcTemplate.update(sqlInsertItemsIntoCloset, theTestChild.getId(), 2, false);
		testCloset = closetSqlDao.viewCloset(theTestChild.getId());
		List<Item> closetItems = new ArrayList<>();
		closetItems.add(itemSqlDao.getItemById(1));
		closetItems.add(itemSqlDao.getItemById(2));
		
		Assert.assertNotNull(testCloset);
		Assert.assertEquals(closetItems.get(0).getColor(), testCloset.getClosetItems().get(0).getColor());
	
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
}
