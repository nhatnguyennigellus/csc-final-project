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
			// TypedQuery<Transaction> query = entityManager.createQuery(
			// "SELECT t FROM Transaction t WHERE t.id = ?1",
			// Transaction.class);
			// query.setParameter(1, id);
			// transaction = query.getSingleResult();
			transaction = entityManager.find(Transaction.class, id);
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

	/**
	 * Get transactions by state (pending/approved)
	 * 
	 * @param state
	 * @return transaction list
	 * @author vinh-tp
	 */
	public List<Transaction> getTransactionsByState(String state) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<Transaction> transactions = null;
		try {
			enTr.begin();
			TypedQuery<Transaction> query = entityManager.createQuery(
					"SELECT t FROM Transaction t WHERE t.state = ?1",
					Transaction.class);
			query.setParameter(1, state);
			transactions = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.close();
			return null;
		}
		return transactions;
	}

	public List<Transaction> searchTransaction(Transaction transaction) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<Transaction> transactions = null;
		try {
			enTr.begin();
			TypedQuery<Transaction> query = entityManager
					.createQuery(
							"SELECT t FROM Transaction t WHERE t.state LIKE ?1 AND t.type LIKE ?2 AND t.savingAccount.accountNumber LIKE ?3",
							Transaction.class);
			query.setParameter(1, transaction.getState() == "" ? "%"
					: transaction.getState());
			query.setParameter(2, transaction.getType() == "" ? "%"
					: transaction.getType());
			query.setParameter(3, "%"
					+ transaction.getSavingAccount().getAccountNumber() + "%");
			transactions = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.close();
			return null;
		}
		return transactions;
	}
}
