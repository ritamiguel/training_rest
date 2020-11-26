package com.accenture.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.training.domain.SalesOrderEntity;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrderEntity, String> {
	
	@Query("Select k from SalesOrderEntity as k where k.status like :status")
    public List<SalesOrderEntity> findByStatus(@Param("status") String status);
	
	@Query("SELECT k FROM SalesOrderEntity as k JOIN FETCH k.items as i")
	public List<SalesOrderEntity> internalFindAll();
	
	@Query("SELECT k FROM SalesOrderEntity as k LEFT JOIN FETCH k.items as i WHERE k.id = :id")
	public SalesOrderEntity internalFindById(@Param("id") String id);

}
