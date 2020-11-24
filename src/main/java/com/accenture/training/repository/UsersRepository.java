package com.accenture.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.training.domain.UserEntity;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, String> {

	@Query("Select k from UserEntity as k where k.name like :keyword")
    public List<UserEntity> findByKeyword(@Param("keyword") String keyword);
}
