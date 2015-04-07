package csc.fresher.finalproject.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue
	private Integer customerId;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String middleName;

	@NotEmpty
	private String address1;

	private String address2;

	@NotEmpty
	@Length(max = 15, min = 10)
	private String phone1;

	@Length(max = 15, min = 10)
	private String phone2;

	@NotEmpty
	private String email;

	@OneToMany(mappedBy = "customer")
	private Set<SavingAccount> savingAccounts = new HashSet<SavingAccount>();

	public Set<SavingAccount> getSavingAccounts() {
		return savingAccounts;
	}

	public void setSavingAccounts(Set<SavingAccount> savingAccounts) {
		this.savingAccounts = savingAccounts;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return customerId;
	}

	public void setId(Integer id) {
		this.customerId = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
