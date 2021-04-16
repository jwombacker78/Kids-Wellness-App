package com.techelevator.model;

import java.util.HashSet;
import java.util.Set;

public class Child extends User{
	
	private int steps;
	private int activityMinutes;
	private int carrotBalance;
	private int playtimeBalance;
	private boolean isActive;
	private int mascotId;
	private int parentId;
	private Set<Authority> authorities = new HashSet<>();
	
	public Child () { 
		super();
	}
	public Child (int id, String username, String password, boolean activated, Set<Authority> authorities, String firstName,
			String lastName, String email) {
		super(id, username, password, activated, authorities, firstName, lastName, email);
	}

	public Set<Authority> getAuthorities() {
       return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
    }
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
	public int getActivityMinutes() {
		return activityMinutes;
	}
	public void setActivityMinutes(int activityMinutes) {
		this.activityMinutes = activityMinutes;
	}
	public int getCarrotBalance() {
		return carrotBalance;
	}
	public void setCarrotBalance(int carrotBalance) {
		this.carrotBalance = carrotBalance;
	}
	public int getPlaytimeBalance() {
		return playtimeBalance;
	}
	public void setPlaytimeBalance(int playtimeBalance) {
		this.playtimeBalance = playtimeBalance;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getMascotId() {
		return mascotId;
	}
	public void setMascotId(int mascotId) {
		this.mascotId = mascotId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	

}
