package csc.fresher.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csc.fresher.finalproject.dao.UserDAO;
import csc.fresher.finalproject.domain.User;

@Service("userService")
public class UserService {
	@Autowired
	private UserDAO userDAO;

	public UserService() {	
	}

	public User checkUserAuthentication (String username, String password) {
		return userDAO.checkUser(username, password);
	}
	
	public User getUserByUsername (String username) {
		return userDAO.getUserByUsername(username);
	}
	
	public List<User> getUserByRole (String role) {
		return userDAO.getUserByRole(role);
	}

	public boolean checkUserActive (String username) {
		return userDAO.checkUserActive(username);
	}
}
