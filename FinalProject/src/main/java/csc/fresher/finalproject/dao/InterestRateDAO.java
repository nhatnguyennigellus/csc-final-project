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
		// Obtains transaction from entity manager
	//	EntityTransaction entr = entityManager.getTransaction();

		// -----------Begin transaction-----------
		List<SavingInterestRate> rateList = null;
		try {
	//		entr.begin();
			// Get a list of accounts from DB
			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r",
					SavingInterestRate.class);
			rateList = query.getResultList();
	//		entr.commit();
		} catch (Exception e) {
	//		entr.rollback();
			return null;
		}
		// -----------End transaction-----------

		return rateList;
	}

	public SavingInterestRate getInterestRateByPeriod(Integer interestRatePeriod) {
	//	EntityTransaction enTr = entityManager.getTransaction();

		SavingInterestRate rate = new SavingInterestRate();
		try {
	//		enTr.begin();

			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r WHERE r.period = ?1",
					SavingInterestRate.class);
			query.setParameter(1, interestRatePeriod);
			rate = query.getSingleResult();
	//		enTr.commit();
		} catch (Exception e) {
	//		enTr.rollback();
			return null;
		}

		return rate;
	}

	public SavingInterestRate getInterestRateById(int id) {
	//	EntityTransaction enTr = entityManager.getTransaction();

		SavingInterestRate rate = new SavingInterestRate();
		try {
	//		enTr.begin();

			TypedQuery<SavingInterestRate> query = entityManager.createQuery(
					"SELECT r FROM SavingInterestRate r WHERE r.id = ?1",
					SavingInterestRate.class);
			query.setParameter(1, id);
			rate = query.getSingleResult();
	//		enTr.commit();
		} catch (Exception e) {
	//		enTr.rollback();
			return null;
		}

		return rate;
	}

	public boolean updateInterestRate(SavingInterestRate rate) {
	//	EntityTransaction enTr = entityManager.getTransaction();

		try {
	//		enTr.begin();
			entityManager.merge(rate);
	//		enTr.commit();
		} catch (Exception e) {
	//		enTr.rollback();
			return false;
		}

		return true;
	}

	public boolean addInterestRate(SavingInterestRate newInterestRate) {
		//EntityTransaction enTr = entityManager.getTransaction();

		try {
		//	enTr.begin();
			entityManager.persist(newInterestRate);
	//		enTr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	//-----------------TEST-----------------------
	public boolean addInterestRate(SavingInterestRate newInterestRate, EntityManager entityManager){
		try{
			entityManager.persist(newInterestRate);
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean updateInterestRate(SavingInterestRate interestRate, EntityManager entityManager){
		try{
			entityManager.merge(interestRate);
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
