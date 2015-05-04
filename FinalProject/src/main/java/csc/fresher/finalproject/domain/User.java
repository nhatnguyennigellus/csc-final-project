package csc.fresher.finalproject.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity class for user
 * 
 * @author Nigellus
 *
 */
@Entity
@Table(name = "user")
public class User {
	@Id
	private String username;

	private String password;

	private boolean enable;

	@ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
	private Set<Transaction> transactions = new HashSet<Transaction>();

	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

}
