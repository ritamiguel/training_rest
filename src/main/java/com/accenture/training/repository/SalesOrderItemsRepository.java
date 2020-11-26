package com.accenture.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.training.domain.SalesOrderItemEntity;

@Repository
public interface SalesOrderItemsRepository extends JpaRepository<SalesOrderItemEntity, String>{

}
