package csc.fresher.finalproject.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.service.DateUtils;

@Repository("transactionDAO")
@Transactional
public class TransactionDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Transaction> getTransactions() {
		List<Transaction> transactions = null;
		try {
			TypedQuery<Transaction> query = entityManager.createQuery(
					"SELECT t FROM Transaction t", Transaction.class);
			transactions = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public Transaction getTransactionById(int id) {
		Transaction transaction = null;
		try {
			transaction = entityManager.find(Transaction.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transaction;
	}

	public boolean getInterestAlready(SavingAccount account) {
		boolean res = false;
		try {
			TypedQuery<Transaction> query = entityManager.createQuery(
					"SELECT t FROM Transaction t WHERE t.savingAccount.accountNumber = ?1"
							+ " AND DAYOFMONTH(date) = DAYOFMONTH(CURDATE())"
							+ " AND t.type = 'Withdraw Interest'",
					Transaction.class);
			query.setParameter(1, account.getAccountNumber());
			res = query.getSingleResult() != null;
		} catch (Exception e) {
			return res;
		}

		return res;
	}

	public List<Date> getInterestWithdraw(SavingAccount account) {
		List<Date> list = new ArrayList<Date>();
		try {
			TypedQuery<Date> query = entityManager.createQuery(
					"SELECT t.date FROM Transaction t WHERE t.savingAccount.accountNumber = ?1"
							+ " AND t.type = 'Withdraw Interest'", Date.class);
			query.setParameter(1, account.getAccountNumber());
			list = query.getResultList();
		} catch (Exception e) {
			return null;
		}

		return list;
	}

	public List<Date> getWithdrawAll(SavingAccount account) {
		List<Date> list = new ArrayList<Date>();
		try {
			TypedQuery<Date> query = entityManager.createQuery(
					"SELECT t.date FROM Transaction t WHERE t.savingAccount.accountNumber = ?1"
							+ " AND t.type = 'Withdraw All'", Date.class);
			query.setParameter(1, account.getAccountNumber());
			list = query.getResultList();
		} catch (Exception e) {
			return list;
		}

		return list;
	}

	public boolean pendingTransAvail(String accNumber) {
		boolean availPending = false;
		try {
			TypedQuery<Transaction> query = entityManager.createQuery(
					"SELECT t FROM Transaction t WHERE t.savingAccount.accountNumber = ?1 "
							+ "AND t.state = 'Pending'", Transaction.class);
			query.setParameter(1, accNumber);
			availPending = query.getResultList().size() > 0;
		} catch (Exception e) {
			return false;
		}
		return availPending;
	}

	public boolean performTransaction(Transaction transaction) {
		try {
			entityManager.persist(transaction);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean approveTransaction(Transaction transaction) {
		try {
			entityManager.merge(transaction);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean rejectTransaction(Transaction trans) {
		try {

			entityManager.merge(trans);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public List<Transaction> searchTransaction(Transaction transaction) {
		List<Transaction> transactions = null;
		try {
			TypedQuery<Transaction> query = entityManager
					.createQuery(
							"SELECT t FROM Transaction t WHERE t.state LIKE ?1 AND t.type LIKE ?2 AND t.savingAccount.accountNumber LIKE ?3",
							Transaction.class);
			query.setParameter(1, "%" + transaction.getState() + "%");
			query.setParameter(2, "%" + transaction.getType() + "%");
			query.setParameter(3, "%"
					+ transaction.getSavingAccount().getAccountNumber() + "%");
			transactions = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return transactions;
	}
}
