package csc.fresher.finalproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.SavingInterestRate;

@Repository("interestRateDAO")
public class InterestRateDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public List<SavingInterestRate> getInterestRateList() {
		List<SavingInterestRate> rateList = null;
		try {
			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r",
					SavingInterestRate.class);
			rateList = query.getResultList();
		} catch (Exception e) {
			return null;
		}

		return rateList;
	}

	public SavingInterestRate getInterestRateByPeriod(Integer interestRatePeriod) {
		SavingInterestRate rate = new SavingInterestRate();
		try {

			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r WHERE r.period = ?1",
					SavingInterestRate.class);
			query.setParameter(1, interestRatePeriod);
			rate = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

		return rate;
	}

	public List<SavingInterestRate> getCurrentInterestRateList() {
		List<SavingInterestRate> rateList = null;
		try {
			TypedQuery<SavingInterestRate> query = entityManager
					.createQuery(
							"SELECT r FROM SavingInterestRate r WHERE r.state = 'Current'"
							+ " ORDER BY r.period",
							SavingInterestRate.class);
			rateList = query.getResultList();
		} catch (Exception e) {
			return null;
		}

		return rateList;

	}

	public SavingInterestRate getCurrentInterestRateByPeriod(
			Integer interestRatePeriod) {
		SavingInterestRate rate = new SavingInterestRate();
		try {

			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r WHERE r.period = ?1 "
							+ "AND r.state = 'Current' ORDER BY r.period",
					SavingInterestRate.class);
			query.setParameter(1, interestRatePeriod);
			rate = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

		return rate;
	}

	public SavingInterestRate getInterestRateById(int id) {

		SavingInterestRate rate = new SavingInterestRate();
		try {

			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r WHERE r.id = ?1",
					SavingInterestRate.class);
			query.setParameter(1, id);
			rate = query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

		return rate;
	}

	public boolean updateInterestRate(SavingInterestRate rate) {
		try {
			entityManager.merge(rate);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean addInterestRate(SavingInterestRate newInterestRate) {
		try {
			entityManager.persist(newInterestRate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// -----------------TEST-----------------------
	public boolean addInterestRate(SavingInterestRate newInterestRate,
			EntityManager entityManager) {
		try {
			entityManager.persist(newInterestRate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean updateInterestRate(SavingInterestRate interestRate,
			EntityManager entityManager) {
		try {
			entityManager.merge(interestRate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
