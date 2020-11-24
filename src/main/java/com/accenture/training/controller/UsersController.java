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

import com.accenture.training.dto.UserDTO;
import com.accenture.training.service.UsersService;



@RestController
@RequestMapping("/Users")
public class UsersController {

	@Autowired
	UsersService usersService;
	
	@GetMapping("")
	public List<UserDTO> findAllUsers(@RequestParam(value = "name", required=false) String name){
		return usersService.findAll();
	}
	
	@GetMapping("{productID}")
	public UserDTO findUserByID(@PathVariable("productID") String userID){
		return usersService.findById(userID);
	}
	
	@GetMapping("find")
	public List<UserDTO> findUserByKeyword(@RequestParam("keyword") String keyword){
		return usersService.findByKeyword(keyword);
	}
	
	@PostMapping("")
	public UserDTO createProduct(@RequestBody UserDTO user){
		return usersService.save(user);
	}
	
	@PutMapping("{productID}")
	public UserDTO updateProduct(@PathVariable("productID") String userID, @RequestBody UserDTO user){
		if(!userID.equals(user.getId())){
			return new UserDTO();
		}
		
		return usersService.save(user);
	}
	
	@DeleteMapping("{productID}")
	public String deleteProduct(@PathVariable("productID") String productID){
		return Boolean.toString(usersService.delete(productID));
	}
}
