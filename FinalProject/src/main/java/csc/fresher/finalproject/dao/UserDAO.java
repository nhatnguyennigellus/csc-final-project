package csc.fresher.finalproject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.User;

@Component
public class UserDAO {
	public User checkUser(String username, String password) {
		EntityManager entityManager = EntityManagerFactoryUtil.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();

		User user = new User();
		try {
			enTr.begin();

			TypedQuery<User> query = entityManager
					.createQuery(
							"SELECT u FROM User u WHERE u.username = ?1 AND u.password = ?2",
							User.class);
			query.setParameter(1, username);
			query.setParameter(2, password);
			user = query.getSingleResult();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}

		return user;
	}
	
	public boolean checkUserActive(String username) {
		EntityManager entityManager = EntityManagerFactoryUtil.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		boolean active = false;
		try {
			enTr.begin();

			TypedQuery<User> query = entityManager
					.createQuery(
							"SELECT u FROM User u WHERE u.username = ?1 AND u.enable = 1",
							User.class);
			query.setParameter(1, username);
			active = query.getSingleResult() != null;
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return false;
		}

		return active;
	}

	public List<User> getUserByRole(String role) {
		EntityManager em = EntityManagerFactoryUtil.createEntityManager();
		EntityTransaction enTr = em.getTransaction();

		List<User> users = new ArrayList<User>();
		try {
			enTr.begin();

			TypedQuery<User> query = em.createQuery(
					"SELECT u FROM User u WHERE u.role.role = ?1", User.class);
			query.setParameter(1, role);
			users = query.getResultList();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			em.close();
			return null;
		}

		return users;
	}
}
