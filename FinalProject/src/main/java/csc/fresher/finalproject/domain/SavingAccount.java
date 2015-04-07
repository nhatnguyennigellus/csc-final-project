package csc.fresher.finalproject.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "savingaccount")
public class SavingAccount {
	@Id
	@NotEmpty
	@Length(max = 12)
	private String accountNumber;
	
	@NotEmpty
	private String accountOwner;
	
	@NotEmpty
	@Min(value = 1000000)
	private double balanceAmount;
	
	@NotEmpty
	private double interest;
	
	@NotEmpty
	private boolean repeatable;
	
	@NotEmpty
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private SavingInterestRate interestRate;
	
	@OneToMany(mappedBy = "savingAccount")
	private Set<Transaction> transactions = new HashSet<Transaction>();

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public boolean isRepeatable() {
		return repeatable;
	}

	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SavingInterestRate getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(SavingInterestRate interestRate) {
		this.interestRate = interestRate;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
}
