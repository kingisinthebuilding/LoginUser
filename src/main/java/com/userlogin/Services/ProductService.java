package com.userlogin.Services;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.userlogin.Helper.Helper;
import com.userlogin.Model.Product;
import com.userlogin.Repository.ProductRepo;

@Service
public class ProductService 
{
	@Autowired
	private ProductRepo productRepo;
	
	public void save(MultipartFile file) throws IOException
	{
		try {
			List<Product> products = Helper.convertExcelToListOfProduct(file.getInputStream());
			this.productRepo.saveAll(products);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public List<Product> getAllProducts()
	{
		return this.productRepo.findAll();
	}
}
