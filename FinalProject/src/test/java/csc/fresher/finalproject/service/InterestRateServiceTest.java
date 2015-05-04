package csc.fresher.finalproject.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.easymock.EasyMockRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.dao.InterestRateDAO;
import csc.fresher.finalproject.domain.SavingInterestRate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/test-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class InterestRateServiceTest {
	@Autowired
	BankingService bankingService;
	
	@Autowired
	InterestRateDAO rateDAO;
	
	@Test
	public void testGetInterestRateList() {
		assertNotNull(rateDAO.getInterestRateList());
	}
	
	@Test
	public void testGetInterestRateByPeriod() {
		assertNotNull(rateDAO.getInterestRateByPeriod(1));
	}

	@Test
	public void testGetInterestRateById() {
		assertNotNull(rateDAO.getInterestRateById(1));
	}

	@Test
	public void testUpdateRate() {
		InterestRateDAO rateDAO = new InterestRateDAO();
		EntityManager en = EntityManagerFactoryUtil.createEntityManager();
		en.getTransaction().begin();
		SavingInterestRate interestRate = new SavingInterestRate();
		interestRate.setId(1);
		interestRate.setPeriod(6);
		interestRate.setInterestRate(0.5);
		assertTrue(rateDAO.updateInterestRate(interestRate, en));
		en.getTransaction().rollback();
		en.close();
	}

	@Test
	public void testAddInterestRate() {
	//	InterestRateDAO rateDAO = new InterestRateDAO();
	//	EntityManager en = EntityManagerFactoryUtil.createEntityManager();
		SavingInterestRate newInterestRate = new SavingInterestRate();
		newInterestRate.setId(3);
		newInterestRate.setInterestRate(0.45);
		newInterestRate.setPeriod(3);
	//	en.getTransaction().begin();
		assertTrue(bankingService.addInterestRate(newInterestRate));
	//	en.getTransaction().rollback();
	//	en.close();
	}
}
