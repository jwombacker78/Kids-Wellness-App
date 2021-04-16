package com.techelevator.dao;

import com.techelevator.model.Child;
import com.techelevator.model.RegisterUserDTO;
import com.techelevator.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    User create(RegisterUserDTO newUser);

    Child giveBirth(RegisterUserDTO newUser, int parentUserId);
}
