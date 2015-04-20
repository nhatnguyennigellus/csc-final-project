package csc.fresher.finalproject.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.easymock.EasyMockRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.transaction.TransactionConfiguration;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.dao.InterestRateDAO;
import csc.fresher.finalproject.domain.SavingInterestRate;

@RunWith(EasyMockRunner.class)
@TransactionConfiguration(defaultRollback=true)
public class InterestRateServiceTest {
	BankingService bankingService;
	
	public InterestRateServiceTest(){
		bankingService = new BankingService();
		EntityManagerFactoryUtil.setEntityManagerFactory();
	}
	

	@Test
	public void testGetInterestRateList() {
		assertNotNull(bankingService.getInterestRateList());
	}
	
	@Test
	public void testGetInterestRateByPeriod() {
		assertNotNull(bankingService.getInterestRateByPeriod(6));
	}

	@Test
	public void testGetInterestRateById() {
		assertNotNull(bankingService.getInterestRateById(1));
	}

	@Test
	public void testUpdateRate() {
		SavingInterestRate interestRate = new SavingInterestRate(1, 6, 0.5);
		List<SavingInterestRate> rateList = new ArrayList<SavingInterestRate>();
		rateList.add(interestRate);
		
		assertTrue(bankingService.updateRate(rateList));
		
		
	}

	@Test
	public void testAddInterestRate() {
		InterestRateDAO rateDAO = new InterestRateDAO();
		EntityManager en = EntityManagerFactoryUtil.createEntityManager();
		SavingInterestRate newInterestRate = new SavingInterestRate(5,1, 0.2);
		en.getTransaction().begin();
		assertTrue(rateDAO.addInterestRate(newInterestRate, en));
		en.getTransaction().rollback();
	}

}
