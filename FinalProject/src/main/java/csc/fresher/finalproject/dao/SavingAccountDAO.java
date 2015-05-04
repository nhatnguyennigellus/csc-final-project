package csc.fresher.finalproject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import csc.fresher.finalproject.domain.SavingAccount;

/**
 * DAO class for SavingAccount
 * 
 * @author Nhat Nguyen, Vinh Truong, Tai Tran
 *
 */
@Repository("savingAccountDAO")
@Transactional
public class SavingAccountDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Get list of saving account
	 * 
	 * @author Nhat Nguyen
	 * @return list of account
	 */
	public List<SavingAccount> getAccountList() {
		List<SavingAccount> savingAccounts = null;
		try {
			TypedQuery<SavingAccount> query = entityManager.createQuery(
					"SELECT a FROM SavingAccount a", SavingAccount.class);
			savingAccounts = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return savingAccounts;
	}

	/**
	 * Get list of saving account by state
	 * 
	 * @author Nhat Nguyen
	 * @param state
	 * @return list of account
	 */
	public List<SavingAccount> getAccountByState(String state) {
		List<SavingAccount> savingAccounts = null;
		try {
			TypedQuery<SavingAccount> query = entityManager.createQuery(
					"SELECT a FROM SavingAccount a WHERE a.state = ?1",
					SavingAccount.class);
			query.setParameter(1, state);
			savingAccounts = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return savingAccounts;
	}

	/**
	 * Get list of saving account by account number
	 * 
	 * @author Nhat Nguyen
	 * @param accountNo
	 *            Account Number
	 * @return list of account
	 */
	public SavingAccount getAccountByAccNumber(String accountNo) {
		SavingAccount savingAccount = null;
		try {
			TypedQuery<SavingAccount> query = entityManager.createQuery(
					"SELECT a FROM SavingAccount a WHERE a.accountNumber = ?1",
					SavingAccount.class);
			query.setParameter(1, accountNo);
			savingAccount = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		return savingAccount;
	}

	/**
	 * Get list of saving account by account ID
	 * 
	 * @author Nhat Nguyen
	 * @param accountId
	 * @return saving account with ID
	 */
	public SavingAccount getAccountById(int accountId) {
		SavingAccount savingAccounts = null;
		try {
			entityManager.find(SavingAccount.class, accountId);
		} catch (Exception e) {
			return null;
		}
		return savingAccounts;
	}

	/**
	 * Add new saving account
	 * 
	 * @author Nhat Nguyen
	 * @param account
	 * @return action result
	 */
	public boolean addSavingAccount(SavingAccount account) {
		try {
			entityManager.persist(account);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Update saving account information
	 * 
	 * @author Tai Tran
	 * @param account
	 * @return action result
	 */
	public boolean updateSavingAccount(SavingAccount account) {
		try {
			entityManager.merge(account);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Get account by ID card number
	 * 
	 * @param idCard
	 *            ID card number of customer
	 * @return SavingAccount found or null
	 * @author Vinh Truong
	 */
	public SavingAccount getAccountByIdCard(String idCard) {
		SavingAccount account = null;
		try {
			TypedQuery<SavingAccount> query = entityManager
					.createQuery(
							"SELECT a FROM SavingAccount a WHERE a.customer.idCardNumber = ?1",
							SavingAccount.class);
			query.setParameter(1, idCard);
			account = query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No result found");
			return null;
		} catch (Exception e) {
			return null;
		}
		return account;
	}

	/**
	 * Search for accounts by ID card number and account number
	 * 
	 * @param idCard
	 * @param accNumber
	 * @return a list of accounts
	 * @author Vinh Truong
	 */
	public List<SavingAccount> searchSavingAccounts(String idCard,
			String accNumber) {
		List<SavingAccount> accounts = new ArrayList<SavingAccount>();
		try {
			TypedQuery<SavingAccount> query = entityManager
					.createQuery(
							"SELECT a FROM SavingAccount a WHERE a.customer.idCardNumber LIKE ?1 AND a.accountNumber LIKE ?2",
							SavingAccount.class);
			query.setParameter(1, "%" + idCard + "%");
			query.setParameter(2, "%" + accNumber + "%");
			accounts = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return accounts;
	}

	/**
	 * Approve new account and change state into active
	 * 
	 * @author Tai Tran
	 * @param account
	 * @return action result
	 * 
	 */
	public boolean approve(SavingAccount account) {
		try {
			entityManager.merge(account);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
