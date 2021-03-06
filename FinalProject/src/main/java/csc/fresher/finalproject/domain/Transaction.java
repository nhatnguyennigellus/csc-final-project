package csc.fresher.finalproject.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Entity class for transaction
 * 
 * @author Nigellus
 *
 */
@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue
	private Integer id;

	private String type;

	private double amount;

	private Date date;

	private String state;

	@ManyToOne
	@JoinColumn(name = "accountId")
	private SavingAccount savingAccount;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usertransaction", joinColumns = { @JoinColumn(name = "transaction_id") }, inverseJoinColumns = { @JoinColumn(name = "username") })
	private Set<User> users = new HashSet<User>();

	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public SavingAccount getSavingAccount() {
		return savingAccount;
	}

	public void setSavingAccount(SavingAccount savingAccount) {
		this.savingAccount = savingAccount;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
