package csc.fresher.finalproject.service;

import java.util.List;

import csc.fresher.finalproject.dao.InterestRateDAO;
import csc.fresher.finalproject.domain.SavingInterestRate;

public class InterestRateService {
	private InterestRateDAO rateDAO;

	public InterestRateService() {
		rateDAO = new InterestRateDAO();
	}

	public List<SavingInterestRate> getInterestRateList() {
		return rateDAO.getInterestRateList();
	}

	public SavingInterestRate getInterestRateByPeriod(double interestRatePeriod) {
		return rateDAO.getInterestRateByPeriod(interestRatePeriod);
	}
	
	public boolean updateRate(List<SavingInterestRate> rateList){
		boolean result = true;
		
		while(result){
			for(SavingInterestRate rate : rateList){
				result = rateDAO.updateInterestRate(rate);
				
				if(!result){
					return false;
				}
			}
			
			result = false;
		}
		
		return true;
	}
}
