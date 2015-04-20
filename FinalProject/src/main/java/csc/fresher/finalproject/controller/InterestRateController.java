package csc.fresher.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import csc.fresher.finalproject.domain.SavingInterestRate;
import csc.fresher.finalproject.service.BankingService;

@Controller
public class InterestRateController {
	@Autowired
	BankingService bankingService;

	@RequestMapping(value = "/viewInterestRate")
	public String viewInterestRate(Model model) {

		List<SavingInterestRate> rateList = bankingService
				.getInterestRateList();
		model.addAttribute("rateList", rateList);

		return "viewInterestRate";
	}

	@RequestMapping(value = "/changeRate")
	public String changeRate(Model model) {

		return "changeRate";
	}

	@RequestMapping(value = "/changeRate", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String changeRate(HttpServletRequest request, Model model) {

		List<SavingInterestRate> rateList = bankingService
				.getInterestRateList();
		int totalRate = rateList.size();

		int rowCount;

		if (request.getParameter("rowCount") == "") {
			rowCount = totalRate;
		} else {
			rowCount = Integer.parseInt(request.getParameter("rowCount"));
		}

		for (int i = 1; i <= rowCount; i++) {
			int id = Integer.parseInt(request.getParameter("id" + i));

			// Validate Interest Rates
			if (request.getParameter("interestRate" + i) == ""
					|| request.getParameter("period" + i) == "") {
				model.addAttribute("rateList", rateList);
				model.addAttribute("notify",
						"<font color = 'red'>Please enter value!</font>");
			}

			double interestRate = Double.parseDouble(request
					.getParameter("interestRate" + i));
			Integer period = Integer.parseInt(request
					.getParameter("period" + i));

			if (i <= totalRate) {
				rateList.get(i - 1).setInterestRate(interestRate);
				rateList.get(i - 1).setPeriod(period);
			} else {
				SavingInterestRate newInterestRate = new SavingInterestRate();
				newInterestRate.setPeriod(period);
				newInterestRate.setInterestRate(interestRate);
				bankingService.addInterestRate(newInterestRate);
			}
		}

		bankingService.updateRate(rateList);

		rateList = bankingService.getInterestRateList();
		model.addAttribute("rateList", rateList);

		return "redirect:viewInterestRate";
	}
}
