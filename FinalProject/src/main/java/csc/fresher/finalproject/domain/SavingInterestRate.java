package csc.fresher.finalproject.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "savinginterestrate")
public class SavingInterestRate {
	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	private Integer period;

	@NotNull
	private double interestRate;

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
}
