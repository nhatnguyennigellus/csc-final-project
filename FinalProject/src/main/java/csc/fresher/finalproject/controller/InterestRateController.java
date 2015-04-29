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
		model = bankingService.getInterestRateList(model);

		return "viewInterestRate";
	}

	@RequestMapping(value = "/changeRate")
	public String changeRate(Model model) {

		return "changeRate";
	}

	@RequestMapping(value = "/changeRate", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String changeRate(HttpServletRequest request, Model model) {

		boolean result = bankingService.updateRate(request, model);

		model = bankingService.getInterestRateList(model);
		
		if(!result){
			model.addAttribute("error", "Cannot update Rate!");
		} else{
			model.addAttribute("error", "Successfully update Rate");
		}

		return "redirect:viewInterestRate";
	}
}
