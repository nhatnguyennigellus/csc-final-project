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
import org.springframework.test.context.transaction.TransactionConfiguration;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.dao.InterestRateDAO;
import csc.fresher.finalproject.domain.SavingInterestRate;

@RunWith(EasyMockRunner.class)
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations={"classpath:/META-INF/test-context.xml"})
public class InterestRateServiceTest {
	@Autowired
	BankingService bankingService;
	
	@BeforeClass
	public static void setUp() {
		EntityManagerFactoryUtil.setEntityManagerFactory();
	}
	

	@Test
	public void testGetInterestRateList() {
		InterestRateDAO rateDAO = new InterestRateDAO();
		assertNotNull(rateDAO.getInterestRateList());
	}
	
	@Test
	public void testGetInterestRateByPeriod() {
		InterestRateDAO rateDAO = new InterestRateDAO();
		assertNotNull(rateDAO.getInterestRateByPeriod(1));
	}

	@Test
	public void testGetInterestRateById() {
		InterestRateDAO rateDAO = new InterestRateDAO();
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
		InterestRateDAO rateDAO = new InterestRateDAO();
		EntityManager en = EntityManagerFactoryUtil.createEntityManager();
		SavingInterestRate newInterestRate = new SavingInterestRate();
		newInterestRate.setInterestRate(0.45);
		newInterestRate.setPeriod(3);
		en.getTransaction().begin();
		assertTrue(rateDAO.addInterestRate(newInterestRate, en));
		en.getTransaction().rollback();
		en.close();
	}
}
