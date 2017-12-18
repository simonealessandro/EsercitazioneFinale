package com.ewitness.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;
import com.ewitness.service.InvoiceService;


@Controller
public class UserController {
	
	@Autowired 
	private InvoiceService invoiceService;
	
	public UserController(InvoiceService invoiceService) {
		this.invoiceService=invoiceService;
	}
	//PROFILO UTENTE E INSERIMENTO FATTURA
	@RequestMapping("/home/invoice")
	public String list(Model model) {
//		model.addAttribute("invoices", invoiceService.list());
		return "/home/invoice/invoice_list";
	}
	//CARICA LA FATTURA CON ID id
	@RequestMapping("/home/invoice/{id}")
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("invoice", invoiceService.get(id));
		return "/home/invoice/view";
	}
	//CREA FATTURA 
	@RequestMapping("/home/invoice/create")
	public String create(Model model) {
		model.addAttribute("invoice", new Invoice());
//		model.addAttribute("invoice", invoiceService.list());
		return "/home/invoice/userFormUpload";
	}
	//SALVA FATTURA
	@RequestMapping (value= "/home/invoice/save", method= RequestMethod.POST)
	public String save(@Valid Invoice invoice,Model model) {
		
		Invoice savedInvoice=invoiceService.save(invoice);
		return "redirect:/home/invoice";
		
	}
	
	
}
