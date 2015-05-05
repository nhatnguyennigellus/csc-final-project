package csc.fresher.finalproject.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

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
	
	@Test
	public void testGetInterestRateList() {
		List<SavingInterestRate> allRateList = bankingService.getInterestRateList();
		
		assertNotNull(allRateList);
		assertTrue(allRateList.get(0).getId() == 1);
		assertTrue(allRateList.get(0).getState().equals("Old"));
	}
	
	@Test
	public void testGetCurrentInterestRateList(){
		List<SavingInterestRate> rateList = bankingService.getCurrentInterestRateList();
		
		assertNotNull(rateList);
		assertTrue(rateList.get(0).getId() == 17);
		assertTrue(rateList.get(0).getInterestRate() == 0.8);
	}
	
	@Test
	public void testGetCurrentRateByPeriod() {
		SavingInterestRate rate = bankingService.getCurrentRateByPeriod(12);
		
		assertNotNull(rate);
		assertTrue(rate.getState().equals("Current"));
	}

	@Test
	public void testGetInterestRateById() {
		assertNotNull(bankingService.getInterestRateById(1));
	}

	@Test
	public void testAddInterestRate() {
		SavingInterestRate newInterestRate = new SavingInterestRate();
		newInterestRate.setInterestRate(0.45);
		newInterestRate.setPeriod(69);
		newInterestRate.setState("Current");
		assertTrue(bankingService.addInterestRate(newInterestRate));
		assertNotNull(bankingService.getCurrentRateByPeriod(69));
	}
}
