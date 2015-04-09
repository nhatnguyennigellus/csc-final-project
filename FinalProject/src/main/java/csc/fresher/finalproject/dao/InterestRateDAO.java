package csc.fresher.finalproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.SavingInterestRate;

@Component
public class InterestRateDAO {
	public List<SavingInterestRate> getInterestRateList() {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();

		// Obtains transaction from entity manager
		EntityTransaction entr = entityManager.getTransaction();

		// -----------Begin transaction-----------
		List<SavingInterestRate> rateList = null;
		try {
			entr.begin();
			// Get a list of accounts from DB
			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r",
					SavingInterestRate.class);
			rateList = query.getResultList();
			entr.commit();
		} catch (Exception e) {
			entityManager.close();
		}
		// -----------End transaction-----------

		return rateList;
	}

	public SavingInterestRate getInterestRateByPeriod(double interestRatePeriod) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();

		SavingInterestRate rate = new SavingInterestRate();
		try {
			enTr.begin();

			TypedQuery<SavingInterestRate> query = entityManager
					.createQuery(
							"SELECT r FROM SavingInterestRate r WHERE r.period = ?1",
							SavingInterestRate.class);
			query.setParameter(1, interestRatePeriod);
			rate = query.getSingleResult();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}

		return rate;
	}
	
	public SavingInterestRate getInterestRateById(int id) {
		EntityManager entityManager = EntityManagerFactoryUtil
				.createEntityManager();
		EntityTransaction enTr = entityManager.getTransaction();

		SavingInterestRate rate = new SavingInterestRate();
		try {
			enTr.begin();

			TypedQuery<SavingInterestRate> query = entityManager
					.createQuery(
							"SELECT r FROM SavingInterestRate r WHERE r.id = ?1",
							SavingInterestRate.class);
			query.setParameter(1, id);
			rate = query.getSingleResult();
			enTr.commit();
		} catch (Exception e) {
			enTr.rollback();
			entityManager.close();
			return null;
		}

		return rate;
	}
	/*
	public boolean updateInterestRate(SavingInterestRate rate) {
		
	}*/
}
