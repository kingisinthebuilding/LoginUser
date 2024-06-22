package com.userlogin.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.userlogin.Characters.ExcelDataLimitExceededException;
import com.userlogin.Helper.Helper;
import com.userlogin.Model.Product;
import com.userlogin.Services.ProductService;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            if (!Helper.checkExcelFormat(file)) {
                return ResponseEntity.badRequest().body("Please upload an Excel file");
            }

            int rowCount = Helper.getRowCount(file);
            if (rowCount > 200) {
                System.out.println("Excel file contains more than 200 Records");
                return ResponseEntity.ok(Map.of("message", "Excel file contains more than 200 Records"));
            }

            this.productService.save(file);
            System.out.println("File uploaded and data saved to the database");
            return ResponseEntity.ok(Map.of("message", "File uploaded and data saved to the database"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process the file due to an IOException");
        } catch (ExcelDataLimitExceededException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while processing the file");
        }
    }

    @GetMapping("/product")
    public List<Product> getAllProduct() {
        try {
            return this.productService.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            // Returning an empty list in case of an exception
            return new ArrayList<>();
        }
    }

    public static List<Product> extractProductsFromExcel(MultipartFile file) throws IOException {
        List<Product> products = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet("Sheet1");
            if (sheet != null) {
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        try {
                            Product product = new Product();
                            product.setProductName(row.getCell(0).getStringCellValue());
                            product.setProductPrice(row.getCell(1).getNumericCellValue());
                            // Set other properties accordingly
                            products.add(product);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Handle or log the exception, continue processing other rows
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("An error occurred while extracting products from Excel", e);
        }
        return products;
    }
}
