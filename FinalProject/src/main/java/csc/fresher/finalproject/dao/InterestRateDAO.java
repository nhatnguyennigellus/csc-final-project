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

/**
 * DAO class for InterestRate
 * 
 * @author Nhat Nguyen, Tai Tran
 *
 */
@Repository("interestRateDAO")
public class InterestRateDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Get list of all interest rate
	 * 
	 * @author Nhat Nguyen
	 * @return list of interest rate
	 */
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

	/**
	 * Get list of interest rate by period
	 * 
	 * @author Nhat Nguyen
	 * @param interestRatePeriod
	 * @return list of interest rate
	 */
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

	/**
	 * Get list of current interest rate
	 * 
	 * @author Nhat Nguyen
	 * @return list of interest rate
	 */
	public List<SavingInterestRate> getCurrentInterestRateList() {
		List<SavingInterestRate> rateList = null;
		try {
			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r WHERE r.state = 'Current'"
							+ " ORDER BY r.period", SavingInterestRate.class);
			rateList = query.getResultList();
		} catch (Exception e) {
			return null;
		}

		return rateList;

	}

	/**
	 * Get list of current interest rate by period
	 * 
	 * @param interestRatePeriod
	 * @return list of interest rate
	 */
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

	/**
	 * Get interest rate by ID
	 * 
	 * @author Nhat Nguyen
	 * @param id
	 * @return Interest rate
	 */
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

	/**
	 * 
	 * Update interest rate
	 * 
	 * @author Tai Tran
	 * @param rate
	 * @return action result
	 */
	public boolean updateInterestRate(SavingInterestRate rate) {
		try {
			entityManager.merge(rate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Update interest rate state into Old
	 * 
	 * @author Tai Tran
	 * @param period
	 * @return action result
	 */
	public boolean updateInterestState(int period) {
		Query query = entityManager
				.createQuery("UPDATE SavingInterestRate SET state = 'Old' WHERE period = "
						+ period);

		query.executeUpdate();

		return true;
	}

	/**
	 * Add new interest rate
	 * 
	 * @author Tai Tran
	 * @param newInterestRate
	 * @return
	 */
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
