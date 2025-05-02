package app.main.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="user_name", nullable = false, unique = true)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(unique = true)
	private String email;
	
	@Column(name="created_at")
	private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

	public User(Long id, String userName, String password, String email, LocalDateTime createdAt) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
	}
	
	public User(String userName, String password, String email, LocalDateTime createdAt) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
	}
	
	public User() {
		
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public java.time.LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCreatedAt(java.time.LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
