package com.userlogin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userlogin.Model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>
{

}
