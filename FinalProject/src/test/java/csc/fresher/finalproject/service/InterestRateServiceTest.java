package csc.fresher.finalproject.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.easymock.EasyMockRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

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
	public void testGetCurrentRateByPeriod() {
		assertNotNull(rateDAO.getCurrentInterestRateByPeriod(12));
	}

	@Test
	public void testGetInterestRateById() {
		assertNotNull(rateDAO.getInterestRateById(1));
	}

	@Test
	public void testUpdateRate() {
		SavingInterestRate interestRate = new SavingInterestRate();
		interestRate.setPeriod(6);
		interestRate.setInterestRate(0.5);
		interestRate.setState("Current");
		assertTrue(rateDAO.updateInterestRate(interestRate));
	}

	@Test
	public void testAddInterestRate() {
		SavingInterestRate newInterestRate = new SavingInterestRate();
		newInterestRate.setInterestRate(0.45);
		newInterestRate.setPeriod(69);
		newInterestRate.setState("Current");
		assertTrue(rateDAO.addInterestRate(newInterestRate));
		assertNotNull(rateDAO.getInterestRateByPeriod(69));
	}
}
