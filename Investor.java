package Infinity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INVESTOR")
public class Investor {
	//private Long id;
	private String userName;
	private Long userId;
	private String gender;
	

	public Investor() {
		super();
	}

	public Investor(String userName, Long userId, String gender ) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.gender = gender;
		
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String UserName) {
		this.userName = UserName;
	}

	@Id
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public String getGender(){
		return gender;
	}

	public void setGender(String gender){
		this.gender = gender;
	}
	
	

	@Override
	public String toString() {
		return "Investor [User name=" + userName + ", User id=" + userId +", gender=" + gender+" ]";
	}
	
	
}
