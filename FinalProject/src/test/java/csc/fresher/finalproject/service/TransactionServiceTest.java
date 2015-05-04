package csc.fresher.finalproject.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/test-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TransactionServiceTest {
	@Autowired
	public BankingService bankingService;
	
	@Test
	public void testBankingService() {
		assertNotNull(bankingService);
	}
	
	@Test
	public void testSearchTransaction() {
		//testing get all transactions
		List<Transaction> transactions = bankingService.searchTransaction("All", "All", "");
		assertTrue(transactions.size()==2);
		
		//testing get a specific type
		List<Transaction> transactions2 = bankingService.searchTransaction("All", "DEPOSIT","");
		assertTrue(transactions2.size()==1);
		
		//testing get a specific state
		List<Transaction> transactions3 = bankingService.searchTransaction("Approved", "All", "");
		assertTrue(transactions3.size()==1);
		
		//testing get account number
		List<Transaction> transactions4 = bankingService.searchTransaction("All", "All", "2015");
		assertTrue(transactions4.size()>0);
		
		//testing composite search
		List<Transaction> transactions5 = bankingService.searchTransaction("Rejected", "Deposit", "2015");
		assertTrue(transactions5.size()>0);
	}
	
	@Test
	public void testGetTransactionById() {
		//testing get id found
		Transaction transaction = bankingService.getTransactionById(1);
		assertTrue(transaction.getId()==1);
		
		//testing get no id found
		Transaction transaction2 = bankingService.getTransactionById(1000);
		assertTrue(transaction2==null);
	}
}
