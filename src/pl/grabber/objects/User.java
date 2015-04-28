package pl.grabber.objects;

import java.util.Date;

public class User {
	 
	private String username;
	private String email;
	private String password;
	
	private boolean smsNotification;
	private boolean emailNotification;
	private Date lastLoginDate;
	
	public User(){
	}
 
	public User(String email, String password){
		this.email = email;
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isSmsNotification() {
		return smsNotification;
	}

	public void setSmsNotification(boolean smsNotification) {
		this.smsNotification = smsNotification;
	}

	public boolean isEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	
	
	
 
}
