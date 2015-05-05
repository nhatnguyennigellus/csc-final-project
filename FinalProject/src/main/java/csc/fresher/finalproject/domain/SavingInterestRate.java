package csc.fresher.finalproject.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity class for saving interest rate
 * 
 * @author Nhat Nguyen
 *
 */
@Entity
@Table(name = "savinginterestrates")
public class SavingInterestRate {
	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	private Integer period;

	@NotNull
	private double interestRate;

	@NotNull
	private String state;

	@OneToMany(mappedBy = "interestRate")
	private Set<SavingAccount> savingAccounts = new HashSet<SavingAccount>();

	public SavingInterestRate() {
	}

	public Set<SavingAccount> getSavingAccounts() {
		return savingAccounts;
	}

	public void setSavingAccounts(Set<SavingAccount> savingAccounts) {
		this.savingAccounts = savingAccounts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
