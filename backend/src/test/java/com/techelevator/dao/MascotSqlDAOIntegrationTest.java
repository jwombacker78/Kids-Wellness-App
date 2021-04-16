package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import org.junit.Assert;

public class MascotSqlDAOIntegrationTest extends DAOIntegrationTest{

	private MascotSqlDAO mascotSqlDAO;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setup() {
		DataSource dataSource = this.getDataSource();
		jdbcTemplate = new JdbcTemplate(dataSource);
		mascotSqlDAO = new MascotSqlDAO(jdbcTemplate);
	}
	
	@Test
	public void getAllMascots_returns_list_of_mascots() {
		List<Integer> pre = new ArrayList<>();
		
		List<Integer> post = mascotSqlDAO.getAllMascots();
		
		Assert.assertEquals(2, post.size());
		
	}
}
