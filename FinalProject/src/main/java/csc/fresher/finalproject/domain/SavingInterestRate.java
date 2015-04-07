package csc.fresher.finalproject.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;



@Entity
@Table(name = "savinginterestrate")
public class SavingInterestRate {
	@Id
	@NotEmpty
	private Integer id;

	@NotEmpty
	private String period;

	@NotEmpty
	private double interestRate;

	@OneToMany(mappedBy = "interestRate")
	private Set<SavingAccount> savingAccounts = new HashSet<SavingAccount>();

	public Set<SavingAccount> getSavingAccounts() {
		return savingAccounts;
	}

	public void setSavingAccounts(Set<SavingAccount> savingAccounts) {
		this.savingAccounts = savingAccounts;
	}

	public SavingInterestRate() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}
