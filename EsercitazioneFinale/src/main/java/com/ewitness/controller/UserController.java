package com.ewitness.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;
import com.ewitness.service.InvoiceService;


@Controller
public class UserController {
	
	@Autowired 
	private InvoiceService invoiceService;
	
	private String directory="C:\\Users\\Domenico\\eclipse-workspace\\EsercitazioneFinale\\upload-dir";
	
	public UserController(InvoiceService invoiceService) {
		this.invoiceService=invoiceService;
	}
	//PROFILO UTENTE E INSERIMENTO FATTURA
	@RequestMapping("/home/invoice")
	public String list(Model model) {
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
		return "/home/invoice/userFormUpload";
	}
	
//	@RequestMapping (value= "/home/invoice/save", method= RequestMethod.POST)
//	public String save(@Valid Invoice invoice,Model model) {
//		
//		Invoice savedInvoice=invoiceService.save(invoice);
//		return "redirect:/home/invoice";
//		
//	}
	
	//UPLOAD FATTURA
	@RequestMapping(value = "/home/invoice/save", method = RequestMethod.POST)
	public String uploadFile(
	    @RequestParam("file") MultipartFile file, Model model) {
		try {
		    // Get the filename and build the local file path (be sure that the 
		    // application have write permissions on such directory)
		    String filename = file.getOriginalFilename();
		    String filepath = Paths.get(directory, filename).toString();
		    // Save the file locally
		    BufferedOutputStream stream =
		        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		    stream.write(file.getBytes());
		    stream.close();
		  }
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    model.addAttribute("message", "errore nel caricamento");
		    return "/home/invoice";
		  }
		    return "home/invoice";
		}
	}
	

