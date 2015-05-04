package csc.fresher.finalproject.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import csc.fresher.finalproject.domain.SavingAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/test-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)

public class AccountServiceTest {
	@Autowired
	private BankingService bankingService;
	@Test
	public void testBankingService() {
		assertNotNull(bankingService);
	}
	
	@Test
	public void testSearchAccount() {
		String idCard="225499940";
		String accNumber="";
		List<SavingAccount> accounts = bankingService.searchSavingAccounts(idCard, accNumber);
//		assertTrue(!accounts.isEmpty());
		assertTrue(accounts.get(0).getCustomer().getIdCardNumber().equals(idCard));
	}
	
	@Test
	public void testSearchAccount2() {
		String idCard="";
		String accNumber="15";
		List<SavingAccount> accounts = bankingService.searchSavingAccounts(idCard, accNumber);
		assertTrue(!accounts.isEmpty());
		assertTrue(accounts.get(0).getAccountNumber().contains(accNumber));
	}
	
	@Test
	public void testGenerateAccountNumber() {
		String genAccountNumber = bankingService.generateAccountNumber();
		assertTrue("Account length is not 12 -> " + genAccountNumber.length(),genAccountNumber.length()==12);
		assertTrue("Wrong year -> "+genAccountNumber.substring(0,2),genAccountNumber.substring(0, 2).equals("15"));
		
	}
	
	@Test
	public void testGetSavingAccounts() {
		List<SavingAccount> accounts = bankingService.getSavingAccounts();
		assertTrue(!accounts.isEmpty());
		assertTrue(accounts.get(0).getAccountNumber().length()>0);
	}
	
	
}
