package com.accenture.training.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.training.domain.ClientsEntity;
import com.accenture.training.dto.ClientsDTO;
import com.accenture.training.repository.ClientsRepository;

@Service
public class ClientsService {
	
	@Autowired
	ClientsRepository rep;
	
	@Autowired
	ModelMapper mapper;

	/*
	 * List all clients into database
	 */
	public List<ClientsDTO> findAll(){
		List<ClientsEntity> findAll = rep.findAll();
		return findAll.stream().map( item -> {
			return mapper.map(item,  ClientsDTO.class); 
		}).collect(Collectors.toList());
	}
	
	/*
	 * Find client by ID
	 */
	public ClientsDTO findById(String clientID) {
        Optional<ClientsEntity> found = rep.findById(clientID);
        if(found.isPresent()){
            return mapper.map(found.get(), ClientsDTO.class);
        }
        return null;
    }
	
	/*
	 * Find client by keyword
	 */
	public List<ClientsDTO> findByKeyword(String keyword) {
        List<ClientsEntity> list_result;
        if(Strings.isEmpty(keyword)){
        	list_result = rep.findAll();
        } else {
        	list_result = rep.findClientByKeyword("%"+keyword+"%");
        }
        return list_result.stream().map( item -> {
			return mapper.map(item,  ClientsDTO.class); 
		}).collect(Collectors.toList());
       
    }
    
	/*
	 * Save new client into database
	 */
    public ClientsDTO save(ClientsDTO client){
        ClientsEntity save = rep.save(mapper.map(client, ClientsEntity.class));
        return mapper.map(save, ClientsDTO.class);
    }


    /*
     * Delete client from database
     */
    public Boolean delete(String clientID){
        rep.deleteById(clientID);
        return true;
    }

}
