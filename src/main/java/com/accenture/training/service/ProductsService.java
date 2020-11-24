package com.accenture.training.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.training.domain.ProductsEntity;
import com.accenture.training.dto.ProductsDTO;
import com.accenture.training.repository.ProductsRepository;

@Service
public class ProductsService {
	
	@Autowired
	ProductsRepository rep;
	
	@Autowired
	ModelMapper mapper;

	/*
	 * List all product in database
	 */
	public List<ProductsDTO> findAll(){
		List<ProductsEntity> findAll = rep.findAll();
		return findAll.stream().map( item -> {
			return mapper.map(item,  ProductsDTO.class); 
		}).collect(Collectors.toList());
	}
	
	/*
	 * Find product by ID
	 */
	public ProductsDTO findById(String productID) {
        Optional<ProductsEntity> found = rep.findById(productID);
        if(found.isPresent()){
            return mapper.map(found.get(), ProductsDTO.class);
        }
        return null;
    }
	
	/*
	 * Find product by keyword
	*/
	public List<ProductsDTO> findByKeyword(String keyword) {
        List<ProductsEntity> list_result;
        if(Strings.isEmpty(keyword)){
        	list_result = rep.findAll();
        } else {
        	list_result = rep.findByKeyword("%"+keyword+"%");
        }
        return list_result.stream().map( item -> {
			return mapper.map(item,  ProductsDTO.class); 
		}).collect(Collectors.toList());
       
    }
    
	/*
	 * Create new product into database
	 */
    public ProductsDTO save(ProductsDTO product){
        ProductsEntity save = rep.save(mapper.map(product, ProductsEntity.class));
        return mapper.map(save, ProductsDTO.class);
    }

    /*
     * Delete product from database
     */
    public Boolean delete(String productID){
        rep.deleteById(productID);
        return true;
    }
}
