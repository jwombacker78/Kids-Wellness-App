package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Mascot;

@Service
public class MascotSqlDAO implements MascotDAO {
	
	private JdbcTemplate jdbcTemplate;

	public MascotSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Mascot viewMascot(int mascotId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean customizeMascot(int mascotId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Integer> getAllMascots() {
		String sql = "SELECT * FROM mascots";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		List<Integer> mascotList = new ArrayList<>();
		while (results.next()) {
			Integer mascotId = results.getInt("mascot_id");
			mascotList.add(mascotId);
		}
		
		return mascotList;
	}

	
}
