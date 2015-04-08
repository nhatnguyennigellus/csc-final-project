package csc.fresher.finalproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.Customer;

@Component
public class CustomerDAO {
	public List<Customer> getCustomers() {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<Customer> customers = null;
		try {
			enTr.begin();
			TypedQuery<Customer> query = entityManager.createQuery(
					"SELECT c FROM Customer c", Customer.class);
			customers = query.getResultList();
			enTr.commit();
		} catch (Exception e) {
			entityManager.close();
		}
		return customers;
	}

	public boolean addCustomer(Customer customer) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		try {
			enTr.begin();
			entityManager.persist(customer);
			enTr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			enTr.rollback();
			entityManager.close();
			return false;
		}
		return true;
	}

	public Customer getCustomerById(int id) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		Customer customer = null;
		try {
			enTr.begin();
			TypedQuery<Customer> query = entityManager.createQuery(
					"SELECT c FROM Customer c WHERE id = ?1", Customer.class);
			query.setParameter(1, id);
			customer = query.getSingleResult();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}
		return customer;
	}
}
