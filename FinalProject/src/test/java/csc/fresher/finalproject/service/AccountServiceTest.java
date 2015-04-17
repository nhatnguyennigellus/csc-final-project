package csc.fresher.finalproject.service;

import java.text.DateFormat;

import javax.swing.text.DateFormatter;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import csc.fresher.finalproject.domain.SavingAccount;

@RunWith(EasyMockRunner.class)
public class AccountServiceTest {
	@Autowired
	private SavingAccountService accountService;
	
	@Test
	public void testAccountService() {
//		SavingAccount savingAccount = new SavingAccount();
//		savingAccount.setAccountNumber("150417000001");
//		savingAccount.setAccountOwner("Vinh TP");
//		savingAccount.setBalanceAmount(10000000);
//		savingAccount.setInterest();
//		savingAccount.setDueDate(new Date());
	}
}
