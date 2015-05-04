package csc.fresher.finalproject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import csc.fresher.finalproject.domain.User;

/**
 * DAO class for User
 * 
 * @author Nhat Nguyen
 *
 */
@Repository("userDAO")
@Transactional
public class UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Check if username and password are authenticated
	 * 
	 * @author Nhat Nguyen
	 * @param username
	 * @param password
	 * @return user
	 */
	public User checkUser(String username, String password) {
		User user = new User();
		try {
			TypedQuery<User> query = entityManager
					.createQuery(
							"SELECT u FROM User u WHERE u.username = ?1 AND u.password = ?2",
							User.class);
			query.setParameter(1, username);
			query.setParameter(2, password);
			user = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

		return user;
	}

	/**
	 * Check if user is active
	 * 
	 * @param username
	 * @return active or not
	 */
	public boolean checkUserActive(String username) {
		boolean active = false;
		try {
			TypedQuery<User> query = entityManager
					.createQuery(
							"SELECT u FROM User u WHERE u.username = ?1 AND u.enable = 1",
							User.class);
			query.setParameter(1, username);
			active = query.getSingleResult() != null;
		} catch (Exception e) {
			return false;
		}

		return active;
	}

	/**
	 * Get user by username
	 * 
	 * @param username
	 * @return user
	 */
	public User getUserByUsername(String username) {
		User user = new User();
		try {
			TypedQuery<User> query = entityManager.createQuery(
					"SELECT u FROM User u WHERE u.username = ?1", User.class);
			query.setParameter(1, username);
			user = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

		return user;
	}

	
	/**
	 * Get list of user by role
	 * 
	 * @param role
	 * @return user
	 */
	public List<User> getUserByRole(String role) {
		List<User> users = new ArrayList<User>();
		try {
			TypedQuery<User> query = entityManager.createQuery(
					"SELECT u FROM User u WHERE u.role.role = ?1", User.class);
			query.setParameter(1, role);
			users = query.getResultList();
		} catch (Exception e) {
			return null;
		}

		return users;
	}
}
