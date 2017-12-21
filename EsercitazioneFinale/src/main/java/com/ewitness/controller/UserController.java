package com.ewitness.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

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


@Controller
public class UserController {
	
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
			model.addAttribute("message", "File uploaded");
			}
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    model.addAttribute("errormessage", "Uploaded error");
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
		List tot=invoiceService.totbyproduct(u.getId());
		model.addAttribute("products", products);
		model.addAttribute("totals_products", tot);
		return "/home/report/product_amount";
	}
	
	@RequestMapping("/home/report/client_amount")
	public String totclient(Model model){
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		List tot=invoiceService.totbyclient(u.getId());
		List clients=invoiceService.client(u.getId());
		model.addAttribute("clients", clients);
		model.addAttribute("totals", tot);
		return "/home/report/client_amount";
		
		}
	
	
	@RequestMapping(value="/home/report/csv_total")
	public String csv(Model model) throws FileNotFoundException, MailException, MessagingException{
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
		delete("D:\\lavoro (spero)\\upload-dir\\revenue_"+email+".csv");
		model.addAttribute("message", "File sent");
		System.out.println(tot);
		return "/home/index_user";
	}
	
	@RequestMapping(value="/home/report/csv_client")
	public String csvclient(Model model) throws FileNotFoundException, MailException, MessagingException{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		List tot=invoiceService.totbyclient(u.getId());
		List clients=invoiceService.client(u.getId());
		PrintWriter pw = new PrintWriter(new File("D:\\lavoro (spero)\\upload-dir\\revenueforclient_"+email+".csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("clients");
        sb.append(",");
        sb.append("revenue");
        sb.append("\n");
        for (int i=0; i<clients.size(); i++) {
        	sb.append(clients.get(i));
        	sb.append(",");
        	sb.append(tot.get(i));
        	sb.append('\n');
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
        
        servemail.sendNotification(email, "D:\\lavoro (spero)\\upload-dir\\revenueforclient_"+email+".csv");
        model.addAttribute("message", "File sent");
		System.out.println(tot);
		return "/home/index_user";
	}
	
	@RequestMapping(value="/home/report/csv_product")
	public String csvproduct(Model model) throws FileNotFoundException, MailException, MessagingException{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u=userRepository.findByEmail(email);
		List tot=invoiceService.totbyproduct(u.getId());
		List products=invoiceService.product(u.getId());
		PrintWriter pw = new PrintWriter(new File("D:\\lavoro (spero)\\upload-dir\\revenueforproduct_"+email+".csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("clients");
        sb.append(",");
        sb.append("revenue");
        sb.append("\n");
        for (int i=0; i<products.size(); i++) {
        	sb.append(products.get(i));
        	sb.append(",");
        	sb.append(tot.get(i));
        	sb.append('\n');
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
        
        servemail.sendNotification(email, "D:\\lavoro (spero)\\upload-dir\\revenueforproduct_"+email+".csv");
        model.addAttribute("message", "File sent");
		System.out.println(tot);
		return "/home/index_user";
	}
}
	

