package csc.fresher.finalproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.Transaction;

@Component
public class TransactionDAO {
	public List<Transaction> getTransactions() {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<Transaction> transactions = null;
		try {
			enTr.begin();
			TypedQuery<Transaction> query = entityManager.createQuery(
					"SELECT t FROM Transaction t", Transaction.class);
			transactions = query.getResultList();
			enTr.commit();
		} catch (Exception e) {
			entityManager.close();
		}
		return transactions;
	}

	public Transaction getTransactionById(int id) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		Transaction transaction = null;
		try {
			enTr.begin();
			TypedQuery<Transaction> query = entityManager.createQuery(
					"SELECT t FROM Transaction t WHERE t.id = ?1", Transaction.class);
			query.setParameter(1, id);
			transaction = query.getSingleResult();
			enTr.commit();
		} catch (Exception e) {
			entityManager.close();
		}
		return transaction;
	}
	/*
	 * public List<Transaction> searchTransaction() {
	 * 
	 * }
	 */

	public boolean performTransaction(Transaction transaction) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		try {
			enTr.begin();
			
			entityManager.persist(transaction);
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return false;
		}
		return true;
	}
	
	public boolean approveTransaction(Transaction transaction) {
		return true;
	}
}
