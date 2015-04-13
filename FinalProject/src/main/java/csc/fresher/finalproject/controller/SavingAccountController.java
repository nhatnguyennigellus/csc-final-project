package csc.fresher.finalproject.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import csc.fresher.finalproject.domain.Customer;
import csc.fresher.finalproject.domain.SavingAccount;
import csc.fresher.finalproject.domain.SavingInterestRate;
import csc.fresher.finalproject.domain.Transaction;
import csc.fresher.finalproject.service.CustomerService;
import csc.fresher.finalproject.service.DateUtils;
import csc.fresher.finalproject.service.InterestRateService;
import csc.fresher.finalproject.service.SavingAccountService;
import csc.fresher.finalproject.service.TransactionService;

@Controller
public class SavingAccountController {
	
	@Autowired
	private SavingAccountService accountService ;
	
	@Autowired
	private CustomerService customerService ;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private InterestRateService rateService ;

	@RequestMapping(value = "/toAddAccount")
	public String toAddCustomer(Model model, HttpServletRequest request) {
		SavingAccount account = new SavingAccount();
		account.setAccountNumber(accountService.generateAccountNumber());

		model.addAttribute("savingAccount", account);
		model.addAttribute("customerId", request.getParameter("customerId"));
		return "addAccount";
	}

	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public String addAccount(
			@ModelAttribute("savingAccount") @Valid SavingAccount savingAccount,
			BindingResult result, Model model, HttpServletRequest request) {
		if (accountService.existedAccountNumber(savingAccount
				.getAccountNumber())) {
			model.addAttribute("addAccError", "This account number exists!");
			return "redirect:toAddAccount";
		}

		int customerId = Integer.parseInt(request.getParameter("customerId"));
		Customer customer = customerService.getCustomerById(customerId);

		double interestRatePeriod = Double.parseDouble(request
				.getParameter("period"));
		SavingInterestRate interestRate = rateService
				.getInterestRateByPeriod(interestRatePeriod);

		savingAccount.setInterestRate(interestRate);

		savingAccount.setCustomer(customer);
		savingAccount.setState("new");

		Calendar cal = Calendar.getInstance();
		savingAccount.setStartDate(cal.getTime());

		if (interestRatePeriod != 0) {
			cal.add(Calendar.MONTH, (int) interestRatePeriod);
			savingAccount.setDueDate(cal.getTime());
		}

		double interest = 0;
		if (interestRatePeriod != 0) {
			int dateDiff = DateUtils.daysBetween(savingAccount.getStartDate()
					.getTime(), savingAccount.getDueDate().getTime());
			interest = savingAccount.getBalanceAmount()
					* interestRate.getInterestRate() / 365 * dateDiff;
		} else {
			interest = savingAccount.getBalanceAmount()
					* interestRate.getInterestRate() / 360 * 30;
		}

		savingAccount.setInterest(interest);

		boolean repeatable = request.getParameter("repeatable") != null;
		savingAccount.setRepeatable(repeatable);

		if (accountService.addSavingAccount(savingAccount)) {
			model.addAttribute("addAccSuccess",
					"Added new account successfully!");
		} else {
			model.addAttribute("addAccError", "Fail to add new account!");
		}
		return "addAccount";
	}
/*
	@Scheduled(cron = "0 32 17 * * ?")
	public void updateAccount() {
		for (SavingAccount account : accountService.getSavingAccounts()) {
			if (account.getInterestRate().getPeriod() != 0) {
				List<Transaction> list = transactionService
						.getWithdrawAll(account);
				Date latestDate = null;
				if (!list.isEmpty()) {
					latestDate = list.get(list.size() - 1).getDate();
				}

				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -1);
				if ((!DateUtils.isSameDay(latestDate, cal.getTime()) && latestDate != null)
						|| list.isEmpty()) {
					System.out.println("task run!");
					if (account.isRepeatable() == true) {
						account.setBalanceAmount(account.getBalanceAmount()
								+ account.getInterest());
						Calendar calAcc = Calendar.getInstance();
						account.setStartDate(calAcc.getTime());

						calAcc.add(Calendar.MONTH, (int) account
								.getInterestRate().getPeriod());
						account.setDueDate(calAcc.getTime());
						int dateDiff = DateUtils.daysBetween(account
								.getStartDate().getTime(), account.getDueDate()
								.getTime());
						double interest = account.getBalanceAmount()
								* account.getInterestRate().getInterestRate()
								/ 365 * dateDiff;
						account.setInterest(interest);
					}
				}

			} else {
				List<Transaction> list = transactionService
						.getInterestWithdraw(account);
				Date latestDate = list.get(list.size() - 1).getDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(latestDate);
				cal.add(Calendar.MONTH, 1);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if (DateUtils.isSameDay(new Date(), cal.getTime())) {
					account.setBalanceAmount(account.getBalanceAmount()
							+ account.getInterest());
					double interest = account.getBalanceAmount()
							* account.getInterestRate().getInterestRate() / 360
							* 30;
					account.setInterest(interest);
				}
			}
			accountService.updateSavingAccount(account);
		}
	}*/
}
