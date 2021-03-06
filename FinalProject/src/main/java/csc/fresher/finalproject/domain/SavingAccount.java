package csc.fresher.finalproject.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity class for saving account
 * 
 * @author Nhat Nguyen
 *
 */
@Entity
@Table(name = "savingaccounts")
public class SavingAccount {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer accountId;

	@NotEmpty
	@Length(max = 12)
	private String accountNumber;

	private String accountOwner;

	private double balanceAmount;

	private double interest;

	private Date startDate;

	private Date dueDate;

	private boolean repeatable;

	private String state;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "interestRateId")
	private SavingInterestRate interestRate;

	@OneToMany(mappedBy = "savingAccount")
	@JsonIgnore
	private Set<Transaction> transactions = new HashSet<Transaction>();

	public SavingAccount() {

	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer id) {
		this.accountId = id;
	}

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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
