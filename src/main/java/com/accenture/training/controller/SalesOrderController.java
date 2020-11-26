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

import com.accenture.training.dto.SalesOrderDTO;
import com.accenture.training.service.SalesOrderService;
//import com.sap.cloud.security.token.Token;

@RestController
@RequestMapping("/SalesOrder")
public class SalesOrderController {
	
	@Autowired
	SalesOrderService salesOrderService;
	
	@GetMapping(value = "")
	public List<SalesOrderDTO> getSalesOrder(){
		return salesOrderService.findAll();
	}
	
	@GetMapping("{salesOrderID}")
	public SalesOrderDTO findByID(@PathVariable("salesOrderID") String salesOrderID){
		return salesOrderService.findById(salesOrderID);
	}

	@PostMapping("")
	public SalesOrderDTO createSalesOrder(@RequestBody SalesOrderDTO salesOrder){
		return salesOrderService.save(salesOrder);
	}
	
	@PutMapping("{salesOrderID}")
	public SalesOrderDTO updateSalesOrder(@PathVariable("salesOrderID") String salesOrderID, 
			@RequestBody SalesOrderDTO salesOrder){
		if(!salesOrderID.equals(salesOrder.getId())){
			return new SalesOrderDTO();
		}
		
		return salesOrderService.save(salesOrder);
	}
	
	@DeleteMapping("{salesOrderID}")
	public String deleteSalesOrder(@PathVariable("salesOrderID") String salesOrderID){
		return Boolean.toString(salesOrderService.delete(salesOrderID));
	}

}
