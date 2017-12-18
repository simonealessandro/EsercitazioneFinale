package com.ewitness.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;
import com.ewitness.repository.InvoiceRepository;
import com.ewitness.repository.UserRepository;

@Service
public class InvoiceService {

	private InvoiceRepository invoiceRepository;

	@Autowired
	public InvoiceService(InvoiceRepository invoiceRepository){
		this.invoiceRepository = invoiceRepository;
	}

//	public Invoice getLatestInvoice(){
//		return invoiceRepository.findOneByDate();
//	}
//
//	public List<Invoice> listByUserId(Long id) {
//		return invoiceRepository.listByUserId(id);
//	}
//
//
//	public List<Invoice> listByUser(Long id) {
//		return invoiceRepository.findAllByAuthorIdOrderByPostedOnDesc(id);
//	}
	public Invoice get(Long id) {
		return invoiceRepository.findOne(id);
	}
	public Invoice save(Invoice invoice) {

		return invoiceRepository.save(invoice);

	}
	
}
