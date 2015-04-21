package csc.fresher.finalproject.service;

import java.text.DateFormat;

import javax.swing.text.DateFormatter;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import csc.fresher.finalproject.controller.EntityManagerFactoryUtil;
import csc.fresher.finalproject.domain.SavingAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/test-context.xml"})
public class AccountServiceTest {
	@Autowired
	private BankingService bankingService;
	
	@Before
	public void setUp() {
		EntityManagerFactoryUtil.setEntityManagerFactory();
	}
	
	@Test
	public void testAccountService() {
		Assert.assertNotNull(bankingService);
		bankingService.existedAccountNumber("2015");
	}
}
