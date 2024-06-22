package com.userlogin.Helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import com.userlogin.Model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "*")
public class Helper {


    //check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType != null && contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) 
        {
            try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
                XSSFSheet sheet = workbook.getSheet("Sheet1");

                if (sheet != null) 
                {
//                    int rowCount = sheet.getLastRowNum();
//                    if (rowCount > 200) 
//                    {
//                        System.out.println("Excel file contains more than 200 Records");
//                        return false;
//                    }
                    return true;
                } 
                else 
                {
                    System.out.println("Sheet not found or empty workbook");
                    return false;
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                // Handle other exceptions here
                return false;
            }
        } 
        else 
        {
            System.out.println("Please Upload File");
            return false;
        }

    }

    //convert excel to list of products

    public static List<Product> convertExcelToListOfProduct(InputStream is) 
    {
    	
        List<Product> list = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) 
        {
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            if (sheet != null) 
            {
            	
//            	int rowCount = sheet.getLastRowNum();
//                int maxRecords = Math.min(rowCount, 200);
            	
                Iterator<Row> iterator = sheet.iterator();
                int rowNumber = 0;

                while (iterator.hasNext()) 
                {
                    Row row = iterator.next();

                    if (rowNumber == 0) 
                    {
                        rowNumber++;
                        continue;
                    }
//                    if (rowCount > 200) 
//                    {
//                        throw new ExcelDataLimitExceededException("Excel file contains more than 200 Records. Limit exceeded.");
//                    }

                    Iterator<Cell> cells = row.iterator();
                    int cid = 0;
                    Product p = new Product();

                    while (cells.hasNext()) 
                    {
                        Cell cell = cells.next();

                        switch (cid) 
                        {
                            case 0:
                                p.setProductId((int) cell.getNumericCellValue());
                                break;
                            case 1:
                                p.setProductName(cell.getStringCellValue());
                                break;
                            case 2:
                                p.setProductDesc(cell.getStringCellValue());
                                break;
                            case 3:
                                p.setProductPrice(cell.getNumericCellValue());
                                break;
                            default:
                                break;
                        }
                        cid++;
                    }

                    list.add(p);
                }
            } else 
            {
                System.out.println("Sheet not found or empty workbook");
                // Handle the case when the sheet is null or not found
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
            // Handle other exceptions here
        }
        return list;
    }

    public static boolean exceedsRowLimit(MultipartFile file, int limit) 
    {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) 
        {
            int rowCount = workbook.getSheetAt(0).getPhysicalNumberOfRows();
            return rowCount > limit;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            // Handle the IOException or return false indicating the file doesn't exceed the limit
            return false;
        }
    }
    
    
    
    public static int getRowCount(MultipartFile file) throws IOException 
    {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) 
        {
            return workbook.getSheetAt(0).getPhysicalNumberOfRows();
        }
    }
}