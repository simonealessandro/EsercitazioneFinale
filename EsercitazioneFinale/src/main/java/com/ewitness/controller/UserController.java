package com.ewitness.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;
import com.ewitness.repository.UserRepository;
import com.ewitness.service.InvoiceService;
import com.ewitness.service.NotificationService;
import com.ewitness.util.ReadExcel;
import com.ewitness.util.XMLConverter;

@Controller
public class UserController {
	
	@Autowired
	private XMLConverter xmlConverter;
	@Autowired 
	private InvoiceService invoiceService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private ReadExcel a;
	
	@Autowired
	private NotificationService servemail;
		
	private String directory="D:\\lavoro (spero)\\upload-dir";
	
	public UserController(InvoiceService invoiceService) {
		this.invoiceService=invoiceService;
	}
	
	@RequestMapping("/home/invoice/")
	public String list(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		model.addAttribute("invoices", invoiceService.findByUserId(u.getId()));
		return "/home/invoice/invoice_list";
	}
	//CREA FATTURA 
	@RequestMapping("/home/invoice/create")
	public String create(Model model) {
		model.addAttribute("invoice", new Invoice());
		return "/home/invoice/userFormUpload";
	}	
//	//UPLOAD FATTURA EXCEL
//	@RequestMapping(value = "/home/invoice/saveknkn", method = RequestMethod.POST )
//	public String uploadFile(
//	    @RequestParam("file") MultipartFile file, Model model, Locale loc) {
//		try {
//		    // Get the filename and build the local file path (be sure that the 
//		    // application have write permissions on such directory)
//			String email = SecurityContextHolder.getContext().getAuthentication().getName();
//			//userRepository.findByEmail(email);
//			User u=userRepository.findByEmail(email);
//		    String filename = file.getOriginalFilename();
//		    String filepath = Paths.get(directory, filename).toString();
//		    // Save the file locally
//		    BufferedOutputStream stream =
//		        new BufferedOutputStream(new FileOutputStream(new File(filepath)));
//		    stream.write(file.getBytes());
//		    stream.close();
//			a.read(filepath, u);
//			if(loc.getLanguage().equals("en"))
//				model.addAttribute("message", "File uploaded with success");
//			if(loc.getLanguage().equals("it"))
//				model.addAttribute("message", "File caricato con successo");
//			}
//		  catch (Exception e) {
//		    System.out.println(e.getMessage());
//		    if(loc.getLanguage().equals("en"))
//		    	model.addAttribute("errormessage", "Uploaded error");
//		    if(loc.getLanguage().equals("it"))
//		    	model.addAttribute("errormessage", "Errore nel caricamento");
//		    return "/home/invoice/userFormUpload";
//		  }
//		    return "home/invoice/userFormUpload";
//		}
//
	
	
	
	//UPLOAD FATTURA XML
		@RequestMapping(value = "/home/invoice/save", method = RequestMethod.POST )
		public String uploadFileXML(
		    @RequestParam("file") MultipartFile file, Model model, Locale loc) {
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
			    if(file.getOriginalFilename().endsWith(".xml")) {
			    	xmlConverter.readXML(filepath, u);
			    }
			    else if(file.getOriginalFilename().endsWith(".xlsx") || file.getOriginalFilename().endsWith("xls")) {
			    	a.read(filepath, u);
				}
			    else {
			    	   if(loc.getLanguage().equals("en"))
					    	model.addAttribute("errormessage", "File not supported");
					    if(loc.getLanguage().equals("it"))
					    	model.addAttribute("errormessage", "Formato file non supportato");
					 	
			    }
			    if(loc.getLanguage().equals("en"))
					model.addAttribute("message", "File uploaded with success");
				if(loc.getLanguage().equals("it"))
					model.addAttribute("message", "File caricato con successo");
				}
			  catch (Exception e) {
			    System.out.println(e.getMessage());
			    if(loc.getLanguage().equals("en"))
			    	model.addAttribute("errormessage", "Uploaded error");
			    if(loc.getLanguage().equals("it"))
			    	model.addAttribute("errormessage", "Errore nel caricamento");
			    return "/home/invoice/userFormUpload";
			  }
			    return "home/invoice/userFormUpload";
			}
	
	@RequestMapping(value="/home/report/total_amount")
	public String tot(Model model){
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		double tot=invoiceService.totamount(u.getId());
		model.addAttribute("total", tot);
		return "/home/report/total_amount";
	}
	
	@RequestMapping("/home/report/product_amount")
	public String totproduct(Model model){
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		List products= invoiceService.product(u.getId());
		List productsnames= invoiceService.productname(u.getId());
		List tot=invoiceService.totbyproduct(u.getId());
		model.addAttribute("products", products);
		model.addAttribute("totals_products", tot);
		model.addAttribute("productsnames", productsnames);
		return "/home/report/product_amount";
	}
	
	@RequestMapping("/home/report/client_amount")
	public String totclient(Model model){
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		List tot=invoiceService.totbyclient(u.getId());
		List clientsnames= invoiceService.clientname(u.getId());
		List clients=invoiceService.client(u.getId());
		model.addAttribute("clients", clients);
		model.addAttribute("totals", tot);
		model.addAttribute("clientsnames", clientsnames);
		return "/home/report/client_amount";
		
		}
	
	@RequestMapping(value="/home/report/csv_total")
	public String csv(Model model, Locale loc) throws FileNotFoundException, MailException, MessagingException{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		double tot=invoiceService.totamount(u.getId());
		PrintWriter pw = new PrintWriter(new File("D:\\lavoro (spero)\\upload-dir\\revenue_"+email+".csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("Totale fatturato: ");
        sb.append(",");
        sb.append(tot);
        sb.append('\n');

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
       
        servemail.sendNotification(email, "D:\\lavoro (spero)\\upload-dir\\revenue_"+email+".csv");
		if(loc.getLanguage().equals("en"))
			model.addAttribute("message", "File sent with success");
		if(loc.getLanguage().equals("it"))
			model.addAttribute("message", "File inviato con successo");
		System.out.println(tot);
		return "/home/index_user";
	}
	
	@RequestMapping(value="/home/report/csv_client")
	public String csvclient(Model model, Locale loc) throws FileNotFoundException, MailException, MessagingException{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		List tot=invoiceService.totbyclient(u.getId());
		List clients=invoiceService.client(u.getId());
		List clientsnames= invoiceService.clientname(u.getId());
		PrintWriter pw = new PrintWriter(new File("D:\\lavoro (spero)\\upload-dir\\revenueforclient_"+email+".csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("Client_name");
        sb.append(",");
        sb.append("Client");
        sb.append(",");
        sb.append("Revenue");
        sb.append("\n");
        for (int i=0; i<clients.size(); i++) {
        	sb.append(clientsnames.get(i));
        	sb.append(",");
        	sb.append(clients.get(i));
        	sb.append(",");
        	sb.append(tot.get(i));
        	sb.append('\n');
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
        
        servemail.sendNotification(email, "D:\\lavoro (spero)\\upload-dir\\revenueforclient_"+email+".csv");
        if(loc.getLanguage().equals("en"))
			model.addAttribute("message", "File sent with success");
		if(loc.getLanguage().equals("it"))
			model.addAttribute("message", "File inviato con successo");
		System.out.println(tot);
		return "/home/index_user";
	}
	
	@RequestMapping(value="/home/report/csv_product")
	public String csvproduct(Model model, Locale loc) throws FileNotFoundException, MailException, MessagingException{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		List tot=invoiceService.totbyproduct(u.getId());
		List products=invoiceService.product(u.getId());
		List productsnames= invoiceService.productname(u.getId());
		PrintWriter pw = new PrintWriter(new File("D:\\lavoro (spero)\\upload-dir\\revenueforproduct_"+email+".csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("Product_name");
        sb.append(",");
        sb.append("Product");
        sb.append(",");
        sb.append("Revenue");
        sb.append("\n");
        for (int i=0; i<products.size(); i++) {
        	sb.append(productsnames.get(i));
        	sb.append(",");
        	sb.append(products.get(i));
        	sb.append(",");
        	sb.append(tot.get(i));
        	sb.append('\n');
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
        
        servemail.sendNotification(email, "D:\\lavoro (spero)\\upload-dir\\revenueforproduct_"+email+".csv");
        if(loc.getLanguage().equals("en"))
			model.addAttribute("message", "File sent with success");
		if(loc.getLanguage().equals("it"))
			model.addAttribute("message", "File inviato con successo");
		System.out.println(tot);
		return "/home/index_user";
	}
}