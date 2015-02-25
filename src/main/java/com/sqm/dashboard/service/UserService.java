package com.sqm.dashboard.service;

import java.util.List;

public interface UserService {

	
	  public List<String> getAllUsers();
	  public void addUser(String firstname) throws Exception;

}
