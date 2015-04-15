package csc.fresher.finalproject.service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csc.fresher.finalproject.dao.SavingAccountDAO;
import csc.fresher.finalproject.domain.SavingAccount;

@Service("accountService")
public class SavingAccountService {
	@Autowired
	private SavingAccountDAO savingAccountDAO;

	public SavingAccountService() {
	}

	public boolean existedAccountNumber(String accountNo) {
		return savingAccountDAO.getAccountByAccNumber(accountNo) != null;
	}

	public List<SavingAccount> getSavingAccounts() {
		return savingAccountDAO.getAccountList();
	}

	public List<SavingAccount> getSavingAccountByState(String state) {
		return savingAccountDAO.getAccountByState(state);
	}

	public List<SavingAccount> getSavingAccountByCustomer(String customerId) {
		return savingAccountDAO.getAccountByCustomer(customerId);
	}

	public boolean addSavingAccount(SavingAccount account) {
		return savingAccountDAO.addSavingAccount(account);
	}

	public boolean updateSavingAccount(SavingAccount account) {
		return savingAccountDAO.updateSavingAccount(account);
	}
	
	public SavingAccount getSavingAccountByAccNumber(String accNumber) {
		return savingAccountDAO.getAccountByAccNumber(accNumber); 
	}
	
	public String generateAccountNumber() {
		String accountNo = "";
		
		StringBuilder accNoBuilder = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		DecimalFormat fmt2Digit= new DecimalFormat("00");
		
		accNoBuilder.append(String.valueOf(cal.get(Calendar.YEAR)).substring(2));
		accNoBuilder.append(fmt2Digit.format(cal.get(Calendar.MONTH) + 1));
		accNoBuilder.append(fmt2Digit.format(cal.get(Calendar.DAY_OF_MONTH)));
		
		String suffix = "";
		List<SavingAccount> listAcc = this.getSavingAccounts();
		if (listAcc.contains(accNoBuilder.toString() + "000000")) {
			suffix = "000000";
			
		} else {
			SavingAccount account = listAcc.get(listAcc.size() - 1);
			suffix = account.getAccountNumber().substring(6);
			int next = Integer.parseInt(suffix) + 1;
			DecimalFormat fmt6Digits = new DecimalFormat("000000");
			suffix = fmt6Digits.format(next);
		}
		
		accNoBuilder.append(suffix);
		accountNo = accNoBuilder.toString();
		return accountNo;
	}
	
	/**
	 * Call DAO to search Accounts
	 * @param idCard
	 * @param accNumber
	 * @return
	 * @author vinh-tp
	 */
	public List<SavingAccount> searchSavingAccounts(String idCard,String accNumber) {
		return savingAccountDAO.searchSavingAccounts(idCard, accNumber);
	}

	public boolean approve(SavingAccount account) {
		return savingAccountDAO.approve(account);
	}
}
