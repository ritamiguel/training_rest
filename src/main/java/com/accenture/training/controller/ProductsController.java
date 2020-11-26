package com.accenture.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.training.dto.ProductsDTO;
import com.accenture.training.service.ProductsService;
//import com.sap.cloud.security.token.Token;

@RestController
@RequestMapping("/Products")
public class ProductsController {

	@Autowired
	ProductsService productService;
	
	@GetMapping("")
	public List<ProductsDTO> findAllProducts(@RequestParam(value = "name", required=false) String name){
		return productService.findAll();
	}
	
	@GetMapping("{productID}")
	public ProductsDTO findProductByID(@PathVariable("productID") String productID){
		return productService.findById(productID);
	}
	
	@GetMapping("find")
	public List<ProductsDTO> findProductByKeyword(@RequestParam("keyword") String keyword){
		return productService.findByKeyword(keyword);
	}
	
	@PostMapping("")
	public ProductsDTO createProduct(@RequestBody ProductsDTO product){
		return productService.save(product);
	}
	
	@PutMapping("{productID}")
	public ProductsDTO updateProduct(@PathVariable("productID") String productID, @RequestBody ProductsDTO product){
		if(!productID.equals(product.getId())){
			return new ProductsDTO();
		}
		
		return productService.save(product);
	}
	
	@DeleteMapping("{productID}")
	public String deleteProduct(@PathVariable("productID") String productID){
		return Boolean.toString(productService.delete(productID));
	}
}
