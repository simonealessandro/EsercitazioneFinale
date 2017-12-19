package com.ewitness.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.ewitness.domain.Invoice;



public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

//	Invoice findFirstByOrderByPostedOnDesc();

	List<Invoice> findByUserId(Long id);

}
