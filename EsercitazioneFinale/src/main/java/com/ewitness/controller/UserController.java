package com.ewitness.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

import org.apache.catalina.util.SystemPropertyReplacerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;
import com.ewitness.repository.UserRepository;
import com.ewitness.service.InvoiceService;
import com.ewitness.util.ReadExcel;


@Controller
public class UserController {
	
	@Autowired 
	private InvoiceService invoiceService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private ReadExcel a;
	
	private String directory="D:\\lavoro (spero)\\upload-dir";
	
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
	@RequestMapping(value = "/home/invoice/save", method = RequestMethod.POST )
	public String uploadFile(
	    @RequestParam("file") MultipartFile file, Model model) {
		try {
		    // Get the filename and build the local file path (be sure that the 
		    // application have write permissions on such directory)
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			//userRepository.findByEmail(email);
			User u=userRepository.findByEmail(email);
		    String filename = file.getOriginalFilename();
		    String filepath = Paths.get(directory, filename).toString();
		    // Save the file locally
		    BufferedOutputStream stream =
		        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		    stream.write(file.getBytes());
		    stream.close();
			a.read(filepath, u);
			}
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    model.addAttribute("message", "errore nel caricamento");
		    return "/home/invoice/userFormUpload";
		  }
		    return "home/invoice/userFormUpload";
		}

	@RequestMapping("/home/report")
	@ResponseBody
	public double tot(){
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		double tot=invoiceService.totamount(u.getId());
		System.out.println(tot);
		return tot;
	}
	
	@RequestMapping("/home/client")
	@ResponseBody
	public List totclient(){
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		List tot=invoiceService.totbyclient(u.getId());
		System.out.println(tot);
		return tot;
	}
}
	

