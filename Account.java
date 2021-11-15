package Infinity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACCOUNT")
public class Account {
	private Long userId;
	private String password;
	private int lastPasswordUpdate;
	

	public Account() {
		super();
	}

	public Account(Long userId ,  String password) {
		super();
		this.userId = userId;
		this.password = password;
		this.lastPasswordUpdate = Calendar.getInstance().get(Calendar.MONTH);
	}



	public int getLastPasswordUpdate() {
		return lastPasswordUpdate;
	}

	public void setLastPasswordUpdate(int lastPasswordUpdate) {
		this.lastPasswordUpdate = lastPasswordUpdate;
	}

	@Id
	@Column(name="USER_ID")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account [userId=" + userId +", password=" + password+"]";
	}
	
	
}
