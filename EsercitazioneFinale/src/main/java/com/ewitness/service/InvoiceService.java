package com.ewitness.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewitness.domain.Invoice;
import com.ewitness.repository.InvoiceRepository;

@Service
public class InvoiceService {

	private InvoiceRepository invoiceRepository;
	
	private Logger log= LoggerFactory.getLogger(InvoiceService.class);

	@Autowired
	public InvoiceService(InvoiceRepository invoiceRepository){
		this.invoiceRepository = invoiceRepository;
	}

//	public Invoice getLatestInvoice(){
//		return invoiceRepository.findOneByDate();
//	}
//
	public List<Invoice> findByUserId(Long id) {
		return invoiceRepository.findByUserId(id);
	}
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
	
	public double totamount(long id) {
		Iterator<Invoice> iterator = invoiceRepository.findByUserId(id).iterator();
		Invoice f;
		double total=0;
		while(iterator.hasNext()) {
			f=iterator.next();
			total+=f.getTotal_price();
		}
		return total;
	}
	
	public List totbyclient(long id) {
		List<Invoice> invoicelist = invoiceRepository.findByUserId(id);
		Iterator<Invoice> iterator = invoicelist.iterator();
		Invoice f = new Invoice();
		List<String> list= new ArrayList<>();
		List<Double> totali= new ArrayList<>();
		
		while(iterator.hasNext()) {
			f=iterator.next();
			if(!list.contains(f.getVate_client())) {
				list.add(f.getVate_client());
			}
		}			
//		String a=list.toString();
//		return a;	
		for (int i=0; i<list.size(); i++) {
			double total=0;
			iterator=invoicelist.iterator();
			while(iterator.hasNext()) {
				f=iterator.next();
				if (f.getVate_client().equals(list.get(i))) {
				total+=f.getTotal_price();
				}
			}
			totali.add(total);
		}
		return totali;
	}
}
