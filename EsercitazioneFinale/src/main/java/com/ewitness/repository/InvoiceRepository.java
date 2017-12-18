package com.ewitness.repository;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;


@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

//	Invoice findFirstByOrderByPostedOnDesc();

//	List<Invoice> listByUserId(Long id);

	
}
