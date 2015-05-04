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

/**
 * This controller handles actions that are related to interest rate
 * 
 * @author Tai Tran
 *
 */
@Controller
public class InterestRateController {
	@Autowired
	BankingService bankingService;

	/**
	 * Redirect to Interest Rate page
	 * 
	 * @author Tai Tran
	 * @param model
	 * @return Interest Rate page
	 */
	@RequestMapping(value = "/viewInterestRate")
	public String viewInterestRate(Model model) {
//		model = bankingService.getInterestRateList(model);
		
		List<SavingInterestRate> rateList = bankingService.getCurrentInterestRateList();
		model.addAttribute("rateList", rateList);
		
		return "viewInterestRate";
	}

	/**
	 * 
	 * 
	 * @author Tai Tran
	 * @param model
	 * @return Interest Rate page
	 */
	@RequestMapping(value = "/changeRate")
	public String changeRate(Model model) {

		return "changeRate";
	}

	/**
	 * Update interest rate
	 * 
	 * @author Tai Tran
	 * @param request
	 * @param model
	 * @return Interest Rate page
	 */
	@RequestMapping(value = "/changeRate", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String changeRate(HttpServletRequest request, Model model) {
		boolean result = false;
		
		List<SavingInterestRate> rateList = bankingService.getCurrentInterestRateList();

		int totalRate = rateList.size();

		int rowCount;
		
		if (request.getParameter("rowCount") == "") {
			rowCount = totalRate;
		} else {
			rowCount = Integer.parseInt(request.getParameter("rowCount"));
		}
		
		for (int i = 1; i <= rowCount; i++) {
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
			
			bankingService.updateRate(i, totalRate, rateList, interestRate, period);
		}

		rateList = bankingService.getCurrentInterestRateList();
		model.addAttribute("rateList", rateList);

		if (!result) {
			model.addAttribute("error", "Cannot update Rate!");
		} else {
			model.addAttribute("error", "Successfully update Rate");
		}

		return "redirect:viewInterestRate";
	}
}
