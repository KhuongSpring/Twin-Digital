package com.example.dynamic_spec_service.repository;

import com.example.dynamic_spec_service.domain.entity.DynamicParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicParameterRepository extends JpaRepository<DynamicParameter, Long> {
}
