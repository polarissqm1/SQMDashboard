package com.sqm.dashboard.controller;


import java.util.List;


import com.sqm.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/user")
public class UserController {

	

	@Autowired
	private UserService userService;

	
	
	@RequestMapping("/userlist")
	public @ResponseBody
	List<String> getUsersList() {
		return userService.getAllUsers();
	}

	@RequestMapping(value = "/addUser/{firstname}", method = RequestMethod.POST , consumes = "application/json", produces = "application/json")
	public @ResponseBody
	String addUser(@PathVariable("firstname") String firstname) throws Exception {
		
		System.out.println("Inside add user method >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		userService.addUser(firstname);
		return "Success";
		
		
	}

}