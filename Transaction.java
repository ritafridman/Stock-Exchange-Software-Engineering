package Infinity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRANSACTION")
public class Transaction {
	
	private Long id;
	private String tType;
	private String shareName;
	private int stockAmount;
	private String tStatus;
	private double minPrice;
	private double maxPrice;
	private Long userId;
	private Long commandId;
	

	public Transaction() {
		super();
	}

	public Transaction(String tType, String shareName, int stockAmount, String tStatus, double minPrice, double maxPrice, Long userId  ) {
		super();
		this.tType = tType;
		this.shareName = shareName;
		this.stockAmount = stockAmount;
		this.tStatus = tStatus;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.userId = userId;
		this.commandId = null;
	
		
	}


	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}

	public String getTType() {
		return tType;
	}

	public void setTType(String tType) {
		this.tType = tType;
	}

	
	public String getShareName(){
		return shareName;
	}

	public void setShareName(String ShareName){
		this.shareName = ShareName;
	}
	
	
	
	public int getStockAmount(){
		return stockAmount;
	}
	
	public void setStockAmount(int stockAmount){
		this.stockAmount = stockAmount;
	}
	
	
	public String getTStatus(){
		return tStatus;
	}
	
	public void setTStatus(String tStatus){
		this.tStatus = tStatus;
	}
	
	public double getMinPrice(){
		return minPrice;
	}
	
	public void setMinPrice(double minPrice){
		this.minPrice = minPrice;
	}
	
	public double getMaxPrice(){
		return maxPrice;
	}
	
	public void setMaxPrice(double maxPrice){
		this.maxPrice = maxPrice;
	}
	
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	
	public Long getCommandId() {
		return commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", transaction type=" + tType + ", share name=" + shareName +", stock amount=" + stockAmount+", transaction status=" + tStatus+", min price=" + minPrice+", max price=" + maxPrice+", user id=" + userId+ "]";
	}
	
	
	
	
}
