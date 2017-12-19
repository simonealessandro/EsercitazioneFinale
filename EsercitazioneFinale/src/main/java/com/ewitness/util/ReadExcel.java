package com.ewitness.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;
import com.ewitness.repository.InvoiceRepository;

@Service
public class ReadExcel {
	
	@Autowired
	InvoiceRepository inv;
	
	public void read(String excelFilePath, User user) throws IOException
		{
		//List<Invoice> listBooks = new ArrayList<>();
		//String excelFilePath = "C:\\Users\\Simone\\Desktop\\a.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            Invoice invoice = new Invoice();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                 
//                switch (cell.getCellType()) {
//                    case Cell.CELL_TYPE_STRING:
//                        System.out.print(cell.getStringCellValue());
//                        break;
//                    case Cell.CELL_TYPE_BOOLEAN:
//                        System.out.print(cell.getBooleanCellValue());
//                        break;
//                    case Cell.CELL_TYPE_NUMERIC:
//                        System.out.print(cell.getNumericCellValue());
//                        break;
//                }
                
                switch (columnIndex) {
                case 0:
                	if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                		invoice.setInvoice_code(cell.getStringCellValue());
                		break;}
	                	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	                		double a=cell.getNumericCellValue();
	                		String tuaString = Double.toString(a);
	                		invoice.setInvoice_code(tuaString);break;
	                	}
                case 1:
                	if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        invoice.setProduct_code(cell.getStringCellValue());
                        break;}
                    	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    		double a=cell.getNumericCellValue();
                    		String tuaString = Double.toString(a);
                    		invoice.setProduct_code(tuaString);break;
                    	}
                    //invoice.setProduct_code(cell.getStringCellValue());
                    break;
                case 2:
                	if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        invoice.setProduct_description(cell.getStringCellValue());
                        break;}
                    	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    		double a=cell.getNumericCellValue();
                    		String tuaString = Double.toString(a);
                    		invoice.setProduct_description(tuaString);break;
                    	}
                    //invoice.setProduct_description(cell.getStringCellValue());
                    break;
                case 3:                
                    invoice.setQuantity((int) cell.getNumericCellValue());
                    break;
                case 4:
                	double a=cell.getNumericCellValue();
            		String tuaString = Double.toString(a);
                	double value = Double.parseDouble( tuaString.replace(",",".") );
                    invoice.setTotal_price((float) value);
                    break;    
                case 5:
                	double b=cell.getNumericCellValue();
            		String String = Double.toString(b);
                	double val = Double.parseDouble( String.replace(",",".") );
                    invoice.setUnit_price((float) val);
                    break;    
//                case 6:
//                    invoice.setUser((int) getNumericCellValue(cell));
//                    break;
                case 6:
                	if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        invoice.setVate_client(cell.getStringCellValue());
                        break;}
                    	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    		double f=cell.getNumericCellValue();
                    		String tua = Double.toString(f);
                    		invoice.setVate_client(tua);break;
                    	}
                    //invoice.setVate_client((String) getCellValue(cell));
                    break;
                }            
                //listBooks.add(invoice);
            }
            invoice.setUser(user);
            inv.save(invoice);
        }        
        workbook.close();
        inputStream.close();
		}
	
//	private Object getCellValue(Cell cell) {
//	    switch (cell.getCellType()) {
//	    case Cell.CELL_TYPE_STRING:
//	        return cell.getStringCellValue();
//	 
//	    case Cell.CELL_TYPE_BOOLEAN:
//	        return cell.getBooleanCellValue();
//	 
//	    case Cell.CELL_TYPE_NUMERIC:
//	        return cell.getNumericCellValue();
//	    }
//	 
//	    return null;
//	}
//	
		}