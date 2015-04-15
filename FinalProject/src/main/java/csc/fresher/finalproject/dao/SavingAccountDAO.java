package csc.fresher.finalproject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.Transaction;

@Repository("savingAccountDAO")
public class SavingAccountDAO {

	public List<SavingAccount> getAccountList() {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<SavingAccount> savingAccounts = null;
		try {
			enTr.begin();
			TypedQuery<SavingAccount> query = entityManager.createQuery(
					"SELECT a FROM SavingAccount a", SavingAccount.class);
			savingAccounts = query.getResultList();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}
		return savingAccounts;
	}

	public List<SavingAccount> getAccountByState(String state) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<SavingAccount> savingAccounts = null;
		try {
			enTr.begin();
			TypedQuery<SavingAccount> query = entityManager.createQuery(
					"SELECT a FROM SavingAccount a WHERE a.state = ?1",
					SavingAccount.class);
			query.setParameter(1, state);
			savingAccounts = query.getResultList();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}
		return savingAccounts;
	}

	public SavingAccount getAccountByAccNumber(String accountNo) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		SavingAccount savingAccount = null;
		try {
			enTr.begin();
			TypedQuery<SavingAccount> query = entityManager.createQuery(
					"SELECT a FROM SavingAccount a WHERE a.accountNumber = ?1",
					SavingAccount.class);
			query.setParameter(1, accountNo);
			savingAccount = query.getSingleResult();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}
		return savingAccount;
	}

	public List<SavingAccount> getAccountByCustomer(String customerId) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		List<SavingAccount> savingAccounts = null;
		try {
			enTr.begin();
			TypedQuery<SavingAccount> query = entityManager
					.createQuery(
							"SELECT a FROM SavingAccount a WHERE a.customer.customerId = ?1",
							SavingAccount.class);
			query.setParameter(1, customerId);
			savingAccounts = query.getResultList();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}
		return savingAccounts;
	}

	public boolean addSavingAccount(SavingAccount account) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		try {
			enTr.begin();
			entityManager.persist(account);
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return false;
		}
		return true;
	}

	public boolean updateSavingAccount(SavingAccount account) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		try {
			enTr.begin();
			entityManager.merge(account);
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
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
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction entr = entityManager.getTransaction();
		SavingAccount account = null;
		try {
			entr.begin();
			TypedQuery<SavingAccount> query = entityManager
					.createQuery(
							"SELECT a FROM SavingAccount a WHERE a.customer.idCardNumber = ?1",
							SavingAccount.class);
			query.setParameter(1, idCard);
			account = query.getSingleResult();
			entr.commit();
		} catch (NoResultException e) {
			System.out.println("No result found");
			return null;
		} catch (Exception e) {
			entr.rollback();
			entityManager.close();
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
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction entr = entityManager.getTransaction();
		List<SavingAccount> accounts = new ArrayList<SavingAccount>();
		try {
			entr.begin();
			TypedQuery<SavingAccount> query = entityManager
					.createQuery(
							//"SELECT a FROM SavingAccount a WHERE a.customer.idCardNumber LIKE '%" +idCard+ "%' AND a.accountNumber LIKE '%"+ accNumber + "%'",
							"SELECT a FROM SavingAccount a WHERE a.customer.idCardNumber LIKE ?1 AND a.accountNumber LIKE ?2",
							SavingAccount.class);
			query.setParameter(1, "%" + idCard + "%");
			query.setParameter(2, "%" + accNumber + "%");
			accounts = query.getResultList();
			entr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return accounts;
	}
}
