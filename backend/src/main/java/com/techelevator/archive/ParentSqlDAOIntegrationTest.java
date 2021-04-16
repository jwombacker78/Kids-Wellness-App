//package com.techelevator.archive;
//
//import org.junit.*;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//
//import com.techelevator.dao.DAOIntegrationTest;
//import com.techelevator.dao.UserSqlDAO;
//import com.techelevator.model.RegisterUserDTO;
//import com.techelevator.model.User;
//
//import java.time.LocalDate;
//
//
//import javax.sql.DataSource;
//
//public class ParentSqlDAOIntegrationTest extends DAOIntegrationTest{
//
////	private ParentSqlDAO parentSqlDao;
//	private User testParent;
//	
//	private UserSqlDAO userSqlDao; 
//	
//	private RegisterUserDTO newParentUser;
//	private RegisterUserDTO newChildUser;
//	
//	private JdbcTemplate jdbcTemplate;
//	
//	@Before
//    public void setup() {
//        DataSource dataSource = this.getDataSource();
//        jdbcTemplate = new JdbcTemplate(dataSource);
//        userSqlDao = new UserSqlDAO(jdbcTemplate);
//        
//        newParentUser = new RegisterUserDTO();
//        newParentUser.setUsername("MALL_COP_4_LIFE");
//        newParentUser.setPassword("LOCK_DOWN");
//        newParentUser.setRole("ROLE_USER");
//        newParentUser.setFirstName("PAUL");
//        newParentUser.setLastName("BLART");
//        newParentUser.setEmail("mallcop@gmail.com");
//        newParentUser.setDateOfBirth(LocalDate.of(1776, 07, 04));
//        
//        testParent = userSqlDao.create(newParentUser);
//        
//        newChildUser = new RegisterUserDTO();
//        newChildUser.setUsername("BOY_GENIUS");
//        newChildUser.setPassword("GOTTA_BLAST");
//        newChildUser.setRole("CHILD");
//        newChildUser.setFirstName("JIMMY");
//        newChildUser.setLastName("NEUTRON");	
//        newChildUser.setEmail("boygenius@gmail.com");
//        newChildUser.setDateOfBirth(LocalDate.of(2000, 01, 01));
//	}
//	@Test
//	public void getParentById_returns_parent() {
//		parentSqlDao.addNewParent(testParent);
//		int parentId = testParent.getUserId();
//		
//		Parent objUnderTest = parentSqlDao.getParentById(parentId);
//		
//		Assert.assertNotNull(objUnderTest);
//		Assert.assertEquals("Paul", objUnderTest.getFirstName());
//		Assert.assertEquals("2020-01-08", objUnderTest.getDateOfBirth().toString());
//	}
//	@Test
//	public void addNewParent_creates_new_parent() {
//		boolean parentAdded = parentSqlDao.addNewParent(testParent);
//		Assert.assertTrue(parentAdded);
//	}
//	
//	@Test
//	public void giveBirth_creates_new_child() {
//		parentSqlDao.addNewParent(testParent);
//		boolean childCreated = parentSqlDao.giveBirth(newChildUser, testParent.getUserId());
//		Assert.assertTrue(childCreated);
//		User childUser = userSqlDao.findByUsername("BOY_GENIUS");
//		Assert.assertEquals("BOY_GENIUS", childUser.getUsername());
//		
//	}
//	
//	
//	public int getUserId(String username) {
//		String sqlGetUserId = "SELECT user_id FROM users WHERE username = ?";
<<<<<<< HEAD
//		
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetUserId, username);
//		
//		if(results.next()) {
//			return results.getInt("user_id");
//		} else {
//			return -1;
//		}
//	}
//	
//	public int getParentId(int userId) {
//		String sqlGetParentId = "SELECT parent_id FROM parents WHERE user_id = ?";
=======
>>>>>>> 1140e7fcd6d918cfe0364cec385d103f3abd2e7a
//		
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetUserId, username);
//		
//		if(results.next()) {
//			return results.getInt("user_id");
//		} else {
//			return -1;
//		}
//	}
<<<<<<< HEAD
=======
//	
////	public int getParentId(int userId) {
////		String sqlGetParentId = "SELECT parent_id FROM parents WHERE user_id = ?";
////		
////		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParentId, userId);
////		
////		if (results.next()) {
////			return results.getInt("parent_id");
////		} else {
////			return -1;
////		}
////	}
>>>>>>> 1140e7fcd6d918cfe0364cec385d103f3abd2e7a
//}