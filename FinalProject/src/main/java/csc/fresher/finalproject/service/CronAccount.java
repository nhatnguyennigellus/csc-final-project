package csc.fresher.finalproject.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import csc.fresher.finalproject.domain.SavingAccount;

@Service
public class CronAccount {
	@Autowired
	private SavingAccountService accountService;
	@Autowired
	private TransactionService transactionService;

	@Scheduled(cron = "0 0 0 * * ?")
	public void updateAccount() {
		/* System.out.println("task run!"); */

		for (SavingAccount account : accountService.getSavingAccounts()) {
			System.out.println(account.getAccountNumber());
			if (account.getInterestRate().getPeriod() != 0) {

				List<Date> list = new ArrayList<Date>();
				try {
					list = transactionService.getWithdrawAll(account);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Date latestDate = new Date();
				if (!list.isEmpty()) {
					latestDate = list.get(list.size() - 1);
				}
				System.out.println(list);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -1);
				if ((!DateUtils.isSameDay(latestDate, cal.getTime()) || list
						.isEmpty())
						&& DateUtils.isSameDay(cal.getTime(),
								account.getDueDate())) {

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
				List<Date> list = transactionService
						.getInterestWithdraw(account);
				Date latestDate = new Date();
				if (!list.isEmpty()) {
					latestDate = list.get(list.size() - 1);
				}
				Calendar cal = Calendar.getInstance();
				int todayDay = cal.get(Calendar.DAY_OF_MONTH);
				cal.setTime(latestDate);
				cal.add(Calendar.MONTH, 1);
				cal.add(Calendar.DAY_OF_MONTH, 1);

				// - compare current date and the day
				// a-month-and-a-day-later-from-latest
				// - if not the same day, or there is no latest date, account
				// did not withdraw interest, and exactly a month => update
				Calendar calStart = Calendar.getInstance();
				calStart.setTime(account.getStartDate());
				if ((!DateUtils.isSameDay(new Date(), cal.getTime())
						|| list.isEmpty())
						&& todayDay - 1 == calStart.get(Calendar.DAY_OF_MONTH)) {
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
	}

}
