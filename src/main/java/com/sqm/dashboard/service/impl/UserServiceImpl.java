package com.sqm.dashboard.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.sqm.dashboard.dao.UserDAO;
import com.sqm.dashboard.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	
	
	private UserDAO userDAO;
	
	
	private static List<String> usersList = new ArrayList<String>();

	@Override
    public List<String> getAllUsers() {
        return usersList;
    }
	
	@Override
	public void addUser(String firstname) throws Exception {
		userDAO.addUser(firstname);
    }
}
