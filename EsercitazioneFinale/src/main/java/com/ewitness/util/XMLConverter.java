package com.ewitness.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;
import com.ewitness.repository.InvoiceRepository;

@Service
public class XMLConverter {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	public void readXML(String xmlFile,User user) {
		
		File inputFile=new File(xmlFile);
		DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
		try {
			System.out.println(xmlFile);
			DocumentBuilder db=dbf.newDocumentBuilder();
			Document doc=db.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nd=doc.getElementsByTagName("Invoice");
			Invoice invoice=new Invoice();
			for(int i=0;i<nd.getLength();i++) {
				Node node=nd.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE) {
					
					Element element=(Element) node;
					invoice.setInvoice_code(element.getElementsByTagName("invoice_code").item(0).getTextContent());
					invoice.setName_client(element.getElementsByTagName("name_client").item(0).getTextContent());
					invoice.setProduct_code(element.getElementsByTagName("product_code").item(0).getTextContent());
					invoice.setProduct_description(element.getElementsByTagName("product_description").item(0).getTextContent());
					invoice.setQuantity(Integer.parseInt(element.getElementsByTagName("quantity").item(0).getTextContent()));
					invoice.setTotal_price(Float.parseFloat(element.getElementsByTagName("total_price").item(0).getTextContent()));
					invoice.setUnit_price(Float.parseFloat(element.getElementsByTagName("unit_price").item(0).getTextContent()));
					invoice.setVate_client(element.getElementsByTagName("vate_client").item(0).getTextContent());
					}
					invoice.setUser(user);
					invoiceRepository.save(invoice);
			}			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
			
		}
	}
	
