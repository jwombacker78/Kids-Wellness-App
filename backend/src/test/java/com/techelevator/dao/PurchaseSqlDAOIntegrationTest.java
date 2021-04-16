package com.techelevator.dao;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.Child;
import com.techelevator.model.Item;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;

public class PurchaseSqlDAOIntegrationTest extends DAOIntegrationTest {
	
	private PurchaseSqlDAO purchaseSqlDAO;
	private UserSqlDAO userSqlDAO;
	private ChildSqlDAO childSqlDAO;
	private ItemSqlDAO itemSqlDAO;
	private JdbcTemplate jdbcTemplate;
	
	private RegisterUserDTO newParentUser;
    private RegisterUserDTO newChildUser;
	
    private User testParent;
    private Child testChild;
	
    
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
        
        testChild = userSqlDAO.giveBirth(newChildUser, testParent.getId());
        
    }

    @Test
    public void purchaseItem_adds_item_to_closet() {
    	testChild.setCarrotBalance(50);
    	childSqlDAO.updateChild(testChild);
    	
    	
    	
    	Item testItem = itemSqlDAO.getItemById(1);
//    	testItem.setColor("ORANGE");
//    	testItem.setType("HAT");
//    	testItem.setStyle("BEANIE");
//    	testItem.setDescription("THIS AN ORANGE BEANIE");
//    	testItem.setPrice(24);testItem
    	purchaseSqlDAO.purchaseItem(testItem, testChild);
    	
    	
    	
    	
    }
    
    
}
