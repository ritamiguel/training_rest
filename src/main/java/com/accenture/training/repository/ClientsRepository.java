package com.accenture.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.training.domain.ClientsEntity;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsEntity, String> {
	
	@Query("SELECT c FROM ClientsEntity AS c WHERE c.name like :keyword")
    public List<ClientsEntity> findClientByKeyword(@Param("keyword") String keyword);
	
}
