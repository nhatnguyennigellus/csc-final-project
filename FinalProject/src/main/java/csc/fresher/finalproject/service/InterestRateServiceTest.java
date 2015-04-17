package csc.fresher.finalproject.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.SavingInterestRate;

public class InterestRateServiceTest {
	InterestRateService rateService;
	
	public InterestRateServiceTest(){
		rateService = new InterestRateService();
		EntityManagerFactoryUtil.setEntityManagerFactory();
	}
	

	@Test
	public void testGetInterestRateList() {
		assertNotNull(rateService.getInterestRateList());
	}
	
	@Test
	public void testGetInterestRateByPeriod() {
		assertNotNull(rateService.getInterestRateByPeriod(6));
	}

	@Test
	public void testGetInterestRateById() {
		assertNotNull(rateService.getInterestRateById(1));
	}

	@Test
	public void testUpdateRate() {
		SavingInterestRate interestRate = new SavingInterestRate(1, 6, 0.5);
		List<SavingInterestRate> rateList = new ArrayList<SavingInterestRate>();
		rateList.add(interestRate);
		
		assertTrue(rateService.updateRate(rateList));
	}

	@Test
	public void testAddInterestRate() {
		SavingInterestRate interestRate = new SavingInterestRate(2, 10, 0.27);
		assertTrue(rateService.addInterestRate(interestRate));
	}

}
