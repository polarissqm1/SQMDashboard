package com.sqm.dashboard.util;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user") 
public class User {

	
	   
	    @Id	
	    private String id;
		private String firstname;
		private String surname;
		private int age;
		private String username;
		private String password;
		private int role;
	    
	    
	    
	    public User() {
			super();		
		}



		public User(String id, String firstname, String surname, String username,
				String password, int role) {
			super();
			this.id = id;
			this.firstname = firstname;
			this.surname = surname;
			this.username = username;
			this.password = password;
			this.role = role;
		}



		public String getId() {
			return id;
		}



		public void setId(String id) {
			this.id = id;
		}



		public String getFirstName() {
			return firstname;
		}



		public void setFirstName(String firstname) {
			this.firstname = firstname;
		}



		public String getSurname() {
			return surname;
		}



		public void setSurname(String surname) {
			this.surname = surname;
		}



		public int getAge() {
			return age;
		}



		public void setAge(int age) {
			this.age = age;
		}



		public String getUsername() {
			return username;
		}



		public void setUsername(String username) {
			this.username = username;
		}



		public String getPassword() {
			return password;
		}



		public void setPassword(String password) {
			this.password = password;
		}



		public int getRole() {
			return role;
		}



		public void setRole(int role) {
			this.role = role;
		}

		
		

	    	
}
