package csc.fresher.finalproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.SavingAccount;

@Component
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
			Query query = entityManager
					.createQuery("UPDATE SavingAccount SET accountOwner='"
							+ account.getAccountOwner() + "', balanceAmount="
							+ account.getBalanceAmount() + ", interest="
							+ account.getInterest() + ", repeatable="
							+ account.isRepeatable() + ", state='"
							+ account.getState() + "', customerId="
							+ account.getCustomer().getCustomerId()
							+ ", interestRate="
							+ account.getInterestRate().getId()
							+ " WHERE accountNumber='"
							+ account.getAccountNumber() + "'");
			query.executeUpdate();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return false;
		}
		return true;
	}

	public SavingAccount findAccount(String accountNumber) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();

		return entityManager.find(SavingAccount.class, accountNumber);
	}

	public boolean approve(SavingAccount account) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();
		try{
			enTr.begin();
			entityManager.merge(account);
			enTr.commit();
		} catch(Exception e){
			e.printStackTrace();
			entityManager.close();
			return false;
		}
		
		
		return true;
	}
}
