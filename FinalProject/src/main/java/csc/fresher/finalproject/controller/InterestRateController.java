package csc.fresher.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import csc.fresher.finalproject.domain.SavingInterestRate;
import csc.fresher.finalproject.service.InterestRateService;

@Controller
public class InterestRateController {

	@RequestMapping(value = "/viewInterestRate")
	public String viewInterestRate(Model model){
		InterestRateService interestRateService = new InterestRateService();
		
		List<SavingInterestRate> rateList = interestRateService.getInterestRateList();
		model.addAttribute("rateList", rateList);
		
		return "viewInterestRate";
	}
	
	@RequestMapping(value = "/changeRate")
	public String changeRate(Model model){
		
		return "changeRate";
	}
	
	@RequestMapping(value = "/changeRate", method = RequestMethod.POST)
	public String changeRate(HttpServletRequest request){
		InterestRateService interestRateService = new InterestRateService();
		
		List<SavingInterestRate> rateList = interestRateService.getInterestRateList();
		int totalRate = rateList.size();
		
		for(int i = 1; i <= totalRate; i ++){
			int id = Integer.parseInt(request.getParameter("id" + i));
			double interestRate = Double.parseDouble(request.getParameter("interestRate" + i));
			double period = Double.parseDouble(request.getParameter("period" + i));
			
			rateList.get(i - 1).setInterestRate(interestRate);
			rateList.get(i - 1).setPeriod(period);
		}
		
		interestRateService.updateRate(rateList);
		return "changeRate";
	}
}
