package Infinity;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionDao extends JpaRepository<Transaction, Long>{
	
	
	@Query("SELECT s FROM Transaction AS s WHERE s.userId = :userId")
	public List<Transaction> findAllByUserId(@Param ("userId") Long userId);
}
