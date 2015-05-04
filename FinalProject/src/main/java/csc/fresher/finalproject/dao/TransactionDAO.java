package csc.fresher.finalproject.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.Transaction;

/**
 * DAO class for Transaction
 * 
 * @author Nhat Nguyen, Vinh Truong
 *
 */
@Repository("transactionDAO")
@Transactional
public class TransactionDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Get list of transaction
	 * 
	 * @author Nhat Nguyen
	 * @return list of transaction
	 */
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

	/**
	 * Get transaction by ID
	 * 
	 * @param id
	 * @return transaction
	 */
	public Transaction getTransactionById(int id) {
		Transaction transaction = null;
		try {
			transaction = entityManager.find(Transaction.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transaction;
	}

	/**
	 * Get result if account has got monthly interest of the month already or not
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return result
	 */
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

	/**
	 * Get list of dates when the account has withdrawn its monthly interest
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return list of date
	 */
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

	/**
	 * Get list of dates when the account has withdrawn all its balance and
	 * interest
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return list of date
	 */
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

	/**
	 * Get result if the account still has a pending transaction or not
	 * 
	 * @author Nhat Nguyen
	 * @param accNumber
	 * @return result
	 */
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

	/**
	 * Add new pending transaction 
	 * 
	 * @author Nhat Nguyen
	 * @param transaction
	 * @return action result
	 */
	public boolean performTransaction(Transaction transaction) {
		try {
			entityManager.persist(transaction);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Approve transaction
	 * 
	 * @author Nhat Nguyen
	 * @param transaction
	 * @return action result
	 */
	public boolean approveTransaction(Transaction transaction) {
		try {
			entityManager.merge(transaction);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Reject transaction
	 * 
	 * @author Nhat Nguyen
	 * @param trans
	 * @return action result
	 */
	public boolean rejectTransaction(Transaction trans) {
		try {

			entityManager.merge(trans);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Search transactions by state, type and account number
	 * 
	 * @author Vinh Truong
	 * @param transaction
	 * @return list of transaction
	 */
	public List<Transaction> searchTransaction(Transaction transaction) {
		List<Transaction> transactions = null;
		try {
			TypedQuery<Transaction> query = entityManager
					.createQuery(
							"SELECT t FROM Transaction t WHERE t.state LIKE ?1 AND t.type LIKE ?2 "
							+ "AND t.savingAccount.accountNumber LIKE ?3",
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
