package com.techelevator.dao;

import com.techelevator.model.Child;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class UserSqlDaoIntegrationTest extends DAOIntegrationTest {

    private UserSqlDAO userSqlDAO;
    private JdbcTemplate jdbcTemplate;
    
    private RegisterUserDTO newParentUser;
    private RegisterUserDTO newChildUser;
    
    private User testParent;

    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        userSqlDAO = new UserSqlDAO(jdbcTemplate);
        
        newParentUser = new RegisterUserDTO();
        
        newParentUser.setUsername("MALL_COP_4_LIFE");
        newParentUser.setPassword("LOCK_DOWN");
        newParentUser.setRole("ROLE_USER");
        newParentUser.setFirstName("PAUL");
        newParentUser.setLastName("BLART");
        newParentUser.setEmail("mallcop@gmail.com");
        
        testParent = userSqlDAO.create(newParentUser);
        
        newChildUser = new RegisterUserDTO();
        
        newChildUser.setUsername("BOY_GENIUS");
        newChildUser.setPassword("GOTTA_BLAST");
        newChildUser.setRole("CHILD");
        newChildUser.setFirstName("JIMMY");
        newChildUser.setLastName("NEUTRON");	
        newChildUser.setEmail("boygenius@gmail.com");

    }

    @Test
    public void createNewUser() {
        User userCreated = userSqlDAO.create(newParentUser);
        Assert.assertNotNull(userCreated);
        User user = userSqlDAO.findByUsername("MALL_COP_4_LIFE");
        Assert.assertEquals("MALL_COP_4_LIFE", user.getUsername());
    }
    
    @Test
    public void giveBirth_creates_new_child() {
    	Child testObj = userSqlDAO.giveBirth(newChildUser, testParent.getId());
    	Assert.assertNotNull(testObj);
    	Assert.assertEquals("JIMMY", testObj.getFirstName());
    	Assert.assertEquals("BOY_GENIUS", testObj.getUsername());
    }
    
    @Test
    public void findIdByUsername_returns_correct_id() {
    	int actual = userSqlDAO.findIdByUsername("MALL_COP_4_LIFE");
    	int expected = testParent.getId();
    	Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getUserById_returns_correct_user() {
    	User actual = userSqlDAO.getUserById(testParent.getId());
    	User expected = testParent;
    	Assert.assertNotNull(actual);
    	Assert.assertEquals(expected.toString(), actual.toString());
    }
    
    @Test(expected = RuntimeException.class)
    public void getUserById_throws_exception() throws Exception {
    	userSqlDAO.getUserById(5);
    }


}
