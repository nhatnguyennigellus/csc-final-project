package csc.fresher.finalproject.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import csc.fresher.finalproject.dao.InterestRateDAO;
import csc.fresher.finalproject.domain.SavingInterestRate;

@Service("rateService")
public class InterestRateService {
	private InterestRateDAO rateDAO;

	public InterestRateService() {
		rateDAO = new InterestRateDAO();
	}

	public List<SavingInterestRate> getInterestRateList() {
		return rateDAO.getInterestRateList();
	}

	public SavingInterestRate getInterestRateByPeriod(Integer interestRatePeriod) {
		return rateDAO.getInterestRateByPeriod(interestRatePeriod);
	}
	
	public SavingInterestRate getInterestRateById(int id){
		return rateDAO.getInterestRateById(id);
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

	public boolean addInterestRate(SavingInterestRate newInterestRate) {	
		return rateDAO.addInterestRate(newInterestRate);
		
	}
}
