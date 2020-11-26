package com.accenture.training.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.training.domain.SalesOrderEntity;
import com.accenture.training.dto.SalesOrderDTO;
import com.accenture.training.dto.SalesOrderItemDTO;
import com.accenture.training.repository.SalesOrderItemsRepository;
import com.accenture.training.repository.SalesOrderRepository;


@Service
public class SalesOrderService {
	
	@Autowired
	SalesOrderRepository rep;
	
	@Autowired
	SalesOrderItemsRepository item_rep;
	
	@Autowired
	ModelMapper mapper;

	public List<SalesOrderDTO> findAll() {
        List<SalesOrderEntity> list_result = rep.internalFindAll();
        
        return list_result.stream().map(item -> {
        	SalesOrderDTO salesOrder = mapper.map(item, SalesOrderDTO.class);
            return salesOrder;
        }).collect(Collectors.toList());
       
    }

	
	/*
	 * Find sales order by ID
	 */
	public SalesOrderDTO findById(String salesOrderID) {		
		SalesOrderEntity findById = rep.internalFindById(salesOrderID);
		if(findById != null){
			SalesOrderDTO salesOrder = mapper.map(findById, SalesOrderDTO.class);
			
			List<SalesOrderItemDTO> collect_items = salesOrder.getItems().stream().map(salesOrderItem -> {
				return mapper.map(salesOrderItem, SalesOrderItemDTO.class);
			}).collect(Collectors.toList());
			
			salesOrder.setItems(collect_items);
			
			return salesOrder;
		}
		return null;
    }
	
	
	/*
	 * Create new sales order into database
	 */
    public SalesOrderDTO save(SalesOrderDTO salesOrder){
        SalesOrderEntity sales = mapper.map(salesOrder, SalesOrderEntity.class);
        
        if(Strings.isEmpty(sales.getId())){
        	sales.setCreatedBy("rita");
        	sales.setCreatedAt(LocalDateTime.now());
        }
        
        sales.setModifiedBy("rita");
        sales.setModifiedAt(LocalDateTime.now());
        
        SalesOrderEntity savedEntity = rep.save(sales);
        sales.getItems().stream().forEach(item -> item.setSalesOrder(savedEntity));
        item_rep.saveAll(sales.getItems());
        
        return mapper.map(savedEntity, SalesOrderDTO.class);
    }

    /*
     * Delete sales order from database
     */
    public Boolean delete(String salesOrderID){
    	try {
			rep.deleteById(salesOrderID);
			return true;
		}
		catch(Exception e){
			return false;
		}
    }
	

}
