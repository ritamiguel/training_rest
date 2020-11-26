package com.accenture.training.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "\"TRAINING_SALESORDER_TBLSALESORDER\"")
public class SalesOrderEntity {
	
	@Id
	@Column(name = "\"ID\"")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	@Column(name = "\"STATUS\"")
    private String status;
	
	@OneToMany(
			mappedBy = "salesOrder", 
			cascade = {CascadeType.ALL})
	private List<SalesOrderItemEntity> items;
	
	@ManyToOne
	@JoinColumn(name = "\"USER_ID\"")
	private UserEntity userID;

	@ManyToOne
	@JoinColumn(name = "\"CLIENT_ID\"")
	private ClientsEntity clientID;
	
	@Column(name = "\"CREATEDBY\"")
	private String createdBy;
	
	@Column(name = "\"CREATEDAT\"")
	private LocalDateTime createdAt;
	
	@Column(name = "\"MODIFIEDBY\"")
	private String modifiedBy;
	
	@Column(name = "\"MODIFIEDAT\"")
	private LocalDateTime modifiedAt;
	
	
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
	
	

	public UserEntity getUserID() {
		return userID;
	}

	public void setUserID(UserEntity userID) {
		this.userID = userID;
	}

	public ClientsEntity getClientID() {
		return clientID;
	}

	public void setClientID(ClientsEntity clientID) {
		this.clientID = clientID;
	}

	public List<SalesOrderItemEntity> getItems() {
		return items;
	}

	public void setItems(List<SalesOrderItemEntity> items) {
		this.items = items;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
}
