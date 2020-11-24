package com.accenture.training.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.training.domain.UserEntity;
import com.accenture.training.dto.UserDTO;
import com.accenture.training.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	UsersRepository rep;
	
	@Autowired
	ModelMapper mapper;

	/*
	 * List all user in database
	 */
	public List<UserDTO> findAll(){
		List<UserEntity> findAll = rep.findAll();
		return findAll.stream().map( item -> {
			return mapper.map(item,  UserDTO.class); 
		}).collect(Collectors.toList());
	}
	
	/*
	 * Find user by ID
	 */
	public UserDTO findById(String userID) {
        Optional<UserEntity> found = rep.findById(userID);
        if(found.isPresent()){
            return mapper.map(found.get(), UserDTO.class);
        }
        return null;
    }
	
	/*
	 * Find user by keyword
	*/
	public List<UserDTO> findByKeyword(String keyword) {
        List<UserEntity> list_result;
        if(Strings.isEmpty(keyword)){
        	list_result = rep.findAll();
        } else {
        	list_result = rep.findByKeyword("%"+keyword+"%");
        }
        return list_result.stream().map( item -> {
			return mapper.map(item,  UserDTO.class); 
		}).collect(Collectors.toList());
       
    }
    
	/*
	 * Create new user into database
	 */
    public UserDTO save(UserDTO user){
    	UserEntity save = rep.save(mapper.map(user, UserEntity.class));
        return mapper.map(save, UserDTO.class);
    }

    /*
     * Delete user from database
     */
    public Boolean delete(String userID){
        rep.deleteById(userID);
        return true;
    }
}
