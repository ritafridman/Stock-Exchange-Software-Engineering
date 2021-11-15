package Infinity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvestorFacadeImplementation implements InvestorFacade{
	@Autowired
	private InvestorDao investorDao;
	
	
	public InvestorFacadeImplementation() {
		super();
	}

	@Override
	@Transactional(readOnly=true)
	public Investor viewInvestorDetails(Long id) {
		return investorDao.findOne(id);	
	}

	@Override
	@Transactional
	public void saveInvestorDetails(Investor investor) {
		investorDao.save(investor);
		
	}


}
