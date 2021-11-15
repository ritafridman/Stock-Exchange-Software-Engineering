package Infinity;

import java.util.List;

public interface TransactionFacade {
	
	public Transaction viewTransaction(Long id);
	public void saveTransaction(Transaction transaction);
	public List<Transaction> getAllTransaction();
	public List<Transaction> getAllTransactionByUserId(Long userId);

}
