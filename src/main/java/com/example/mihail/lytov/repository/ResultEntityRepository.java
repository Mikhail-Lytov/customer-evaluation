package com.example.mihail.lytov.repository;

import com.example.mihail.lytov.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultEntityRepository extends JpaRepository<ResultEntity, Long> {
}