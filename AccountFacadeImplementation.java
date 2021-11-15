package Infinity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountFacadeImplementation implements AccountFacade{
	@Autowired
	private AccountDao accountDao;
	
	
	public AccountFacadeImplementation() {
		super();
	}

	@Override
	@Transactional
	public void saveAccountDetails(Account account) {
		accountDao.save(account);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Account viewAccount(Long id) {
		return accountDao.findOne(id); 
		
	}

	
}
