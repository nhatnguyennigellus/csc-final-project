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

@Repository("savingAccountDAO")
@Transactional
public class SavingAccountDAO {

	@PersistenceContext
	private EntityManager entityManager;

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

	public SavingAccount getAccountById(int accountId) {
		SavingAccount savingAccounts = null;
		try {
			entityManager.find(SavingAccount.class, accountId);
		} catch (Exception e) {
			return null;
		}
		return savingAccounts;
	}

	public boolean addSavingAccount(SavingAccount account) {
		try {
			entityManager.persist(account);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

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
	 * @author vinh-tp
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
	 * Search for accounts
	 * 
	 * @param idCard
	 * @param accNumber
	 * @return a list of accounts
	 * @author vinh-tp
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
