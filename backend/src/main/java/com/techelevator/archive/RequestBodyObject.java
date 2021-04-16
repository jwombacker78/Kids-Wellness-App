package com.techelevator.archive;

import com.techelevator.model.RegisterUserDTO;

public class RequestBodyObject {
	
	private RegisterUserDTO registerUser;
	
	private Parent parent;

	public RegisterUserDTO getRegisterUser() {
		return registerUser;
	}

	public void setRegisterUserDTO(RegisterUserDTO registerUser) {
		this.registerUser = registerUser;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
}
