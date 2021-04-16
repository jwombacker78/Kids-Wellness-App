package com.techelevator.dao;

import com.techelevator.model.Mascot;

public interface MascotDAO {
	
	Mascot viewMascot(int mascotId);
	
	boolean customizeMascot(int mascotId);

}
