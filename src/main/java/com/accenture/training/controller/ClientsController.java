package com.accenture.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.training.dto.ClientsDTO;
import com.accenture.training.service.ClientsService;


@RestController
@RequestMapping("/Clients")
public class ClientsController {

	@Autowired
	ClientsService clientsService;
	
	@GetMapping("")
	public List<ClientsDTO> findAll(@RequestParam(value = "name", required=false) String name){
		return clientsService.findAll();
	}
	
	@GetMapping("{clientID}")
	public ClientsDTO findClientByID(@PathVariable("clientID") String clientID){
		return clientsService.findById(clientID);
	}
	
	@GetMapping("find")
	public List<ClientsDTO> findByKeyword(@RequestParam("keyword") String keyword){
		return clientsService.findByKeyword(keyword);
	}
	
	@PostMapping("")
	public ClientsDTO createClient(@RequestBody ClientsDTO client){
		return clientsService.save(client);
	}
	
	@PutMapping("{clientID}")
	public ClientsDTO updateClient(@PathVariable("clientID") String clientID, 
			@RequestBody ClientsDTO client){
		if(!clientID.equals(client.getId())){
			return new ClientsDTO();
		}
		
		return clientsService.save(client);
	}
	
	@DeleteMapping("{clientID}")
	public String deleteClient(@PathVariable("clientID") String clientID){
		return Boolean.toString(clientsService.delete(clientID));
	}
	
}
