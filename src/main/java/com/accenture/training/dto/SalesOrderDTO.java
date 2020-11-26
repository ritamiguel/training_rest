package com.accenture.training.dto;

import java.util.List;

public class SalesOrderDTO {
	
	private String id;
	private String status;
	private List<SalesOrderItemDTO> items;
	private UserDTO userID;
	private ClientsDTO clientID;
	private String createdBy;
	private String createdAt;
	private String modifiedBy;
	private String modifiedAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public UserDTO getUserID() {
		return userID;
	}
	public void setUserID(UserDTO userID) {
		this.userID = userID;
	}
	public ClientsDTO getClientID() {
		return clientID;
	}
	public void setClientID(ClientsDTO clientID) {
		this.clientID = clientID;
	}
	public List<SalesOrderItemDTO> getItems() {
		return items;
	}
	public void setItems(List<SalesOrderItemDTO> items) {
		this.items = items;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	
	
}
