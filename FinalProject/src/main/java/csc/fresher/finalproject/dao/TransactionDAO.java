package csc.fresher.finalproject.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.service.DateUtils;

@Repository("transactionDAO")
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
			transaction = entityManager.find(Transaction.class, id);
			enTr.commit();
		} catch (Exception e) {
			entityManager.close();
			e.printStackTrace();
		}
		return transaction;
	}

	public boolean getInterestAlready(SavingAccount account) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		boolean res = false;
		try {
			enTr.begin();
			TypedQuery<Transaction> query = entityManager.createQuery(
					"SELECT t FROM Transaction t WHERE t.savingAccount.accountNumber = ?1"
							+ " AND DAYOFMONTH(date) = DAYOFMONTH(CURDATE())"
							+ " AND t.type = 'Withdraw Interest'",
					Transaction.class);
			query.setParameter(1, account.getAccountNumber());
			res = query.getSingleResult() != null;
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return res;
		}

		return res;
	}

	public List<Date> getInterestWithdraw(SavingAccount account) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<Date> list = new ArrayList<Date>();
		try {
			enTr.begin();
			TypedQuery<Date> query = entityManager.createQuery(
					"SELECT t.date FROM Transaction t WHERE t.savingAccount.accountNumber = ?1"
							+ " AND t.type = 'Withdraw Interest'", Date.class);
			query.setParameter(1, account.getAccountNumber());
			list = query.getResultList();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}

		return list;
	}

	public List<Date> getWithdrawAll(SavingAccount account) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<Date> list = new ArrayList<Date>();
		try {
			enTr.begin();
			TypedQuery<Date> query = entityManager.createQuery(
					"SELECT t.date FROM Transaction t WHERE t.savingAccount.accountNumber = ?1"
							+ " AND t.type = 'Withdraw All'", Date.class);
			query.setParameter(1, account.getAccountNumber());
			list = query.getResultList();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			e.printStackTrace();
			return list;
		}

		return list;
	}

	public boolean pendingTransAvail(String accNumber) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		boolean availPending = false;
		try {
			enTr.begin();
			TypedQuery<Transaction> query = entityManager.createQuery(
					"SELECT t FROM Transaction t WHERE t.savingAccount.accountNumber = ?1 "
							+ "AND t.state = 'Pending'", Transaction.class);
			query.setParameter(1, accNumber);
			availPending = query.getResultList().size() > 0;
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return false;
		}
		return availPending;
	}

	public boolean performTransaction(Transaction transaction) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		try {
			enTr.begin();

			entityManager.persist(transaction);
			enTr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			enTr.rollback();
			entityManager.close();
			return false;
		}
		return true;
	}

	public boolean approveTransaction(Transaction transaction) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		try {
			enTr.begin();
			SavingAccount account = entityManager.find(SavingAccount.class,
					transaction.getSavingAccount().getAccountNumber());

			if (transaction.getType().equals("Withdraw All")) {
				account.setBalanceAmount(0);
				account.setInterest(0);
			} else if (transaction.getType().equals("Withdraw Balance")) {
				// Reset new balance = old balance - transaction (withdraw)
				// amount
				account.setBalanceAmount(account.getBalanceAmount()
						- transaction.getAmount() + account.getInterest());
				double interest = 0;

				// Reset new interest
				interest = account.getBalanceAmount()
						* account.getInterestRate().getInterestRate() / 360
						* 30;
				account.setInterest(interest);
			} else if (transaction.getType().equals("Withdraw Interest")) {

			} else if (transaction.getType().equals("Deposit")) {
				// Set new balance
				account.setBalanceAmount(account.getBalanceAmount()
						+ transaction.getAmount());

				// Set new interest
				double interest = 0;
				// If period account, set start date and due date
				if (account.getInterestRate().getPeriod() != 0) {
					Calendar cal = Calendar.getInstance();
					account.setStartDate(cal.getTime());

					cal.add(Calendar.MONTH, (int) account.getInterestRate()
							.getPeriod());
					account.setDueDate(cal.getTime());
					int dateDiff = DateUtils.daysBetween(account.getStartDate()
							.getTime(), account.getDueDate().getTime());
					interest = account.getBalanceAmount()
							* account.getInterestRate().getInterestRate() / 365
							* dateDiff;
				} else {
					interest = account.getBalanceAmount()
							* account.getInterestRate().getInterestRate() / 360
							* 30;
				}
				account.setInterest(interest);
			}
			entityManager.merge(transaction);
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return false;
		}

		return true;
	}

	public boolean rejectTransaction(Transaction trans) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		try {
			enTr.begin();

			entityManager.merge(trans);
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return false;
		}
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
			query.setParameter(1, "%" + transaction.getState() + "%");
			query.setParameter(2, "%" + transaction.getType() + "%");
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
