package com.ewitness.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ewitness.domain.Invoice;
import com.ewitness.domain.User;

public class ReadExcel {

	public static void read(String excelFilePath) throws IOException
		{
		List<Invoice> listBooks = new ArrayList<>();
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
                case 1:
                    invoice.setInvoice_code(getCellValue(cell));
                    break;
                case 2:
                    invoice.setProduct_code( getCellValue(cell));
                    break;
                case 3:
                    invoice.setProduct_description( getCellValue(cell));
                    break;
//                case 4:
//                    invoice.setQuantity((int) getCellValue(cell));
//                    break;
//                case 5:
//                    invoice.setTotal_price((float) getCellValue(cell));
//                    break;    
//                case 6:
//                    invoice.setUnit_price((float) getCellValue(cell));
//                    break;    
//                case 7:
//                    invoice.setUser((User) getCellValue(cell));
//                    break;
//                case 8:
//                    invoice.setVate_client((String) getCellValue(cell));
//                    break;
                }
                listBooks.add(invoice);
                System.out.println(invoice.getInvoice_code());
               
            }
            System.out.println();
        }
         
        workbook.close();
        inputStream.close();
		}
	
	private String getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
//	    case Cell.CELL_TYPE_BOOLEAN:
//	        return cell.getBooleanCellValue();
//	 
//	    case Cell.CELL_TYPE_NUMERIC:
//	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
		}