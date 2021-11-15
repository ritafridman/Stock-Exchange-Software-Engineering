package Infinity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionFacadeImplementatiom  implements TransactionFacade{
	@Autowired
	private TransactionDao transactionDao;

	
	public TransactionFacadeImplementatiom() {
		super();
	}

	@Override
	@Transactional
	public void saveTransaction(Transaction transaction) {
		transactionDao.save(transaction);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Transaction viewTransaction(Long id) {
		return transactionDao.findOne(id);
	}
	

	@Override
	@Transactional(readOnly=true)
	public List<Transaction> getAllTransaction(){
		return transactionDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Transaction> getAllTransactionByUserId(Long userId){
		return transactionDao.findAllByUserId(userId);
	}


}
