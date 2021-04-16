package com.techelevator.archive;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;

import com.techelevator.model.Child;
import com.techelevator.model.User;

public class Parent extends User{
	
	
	
	private int userId;
//	@NotEmpty
	private String email;
//	@NotEmpty
	private LocalDate dateOfBirth;
	private ArrayList<Child> chilrenAccounts;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public ArrayList<Child> getChilrenAccounts() {
		return chilrenAccounts;
	}
	public void setChilrenAccounts(ArrayList<Child> chilrenAccounts) {
		this.chilrenAccounts = chilrenAccounts;
	}

}