package csc.fresher.finalproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.Customer;
import csc.fresher.finalproject.domain.SavingAccount;

@Repository("customerDAO")
@Transactional
public class CustomerDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public CustomerDAO() {
	}

	public List<Customer> getCustomers() {

		List<Customer> customers = null;
		try {

			TypedQuery<Customer> query = entityManager.createQuery(
					"SELECT c FROM Customer c", Customer.class);
			customers = query.getResultList();

		} catch (Exception e) {

		}
		return customers;
	}

	public boolean addCustomer(Customer customer) {

		try {

			entityManager.persist(customer);

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
		return true;
	}

	public Customer getCustomerById(int id) {
		Customer customer = null;
		try {

			TypedQuery<Customer> query = entityManager.createQuery(
					"SELECT c FROM Customer c WHERE id = ?1", Customer.class);
			query.setParameter(1, id);
			customer = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return customer;
	}

	public Customer findCustomerOfAccount(SavingAccount savingAccount) {
		return entityManager.find(Customer.class, savingAccount.getCustomer()
				.getCustomerId());
	}

	public boolean updateCustomer(Customer customer) {
		try {
	
			entityManager.merge(customer);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
