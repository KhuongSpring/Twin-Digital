package org.example.staticspecservice.repository;

import org.example.staticspecservice.domain.entity.StaticSpecParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaticSpecParameterRepository extends JpaRepository<StaticSpecParameter, Long> {

  List<StaticSpecParameter> findByGroupId(Long groupId);

  Optional<StaticSpecParameter> findByParamName(String paramName);

  Optional<StaticSpecParameter> findByParamNameAndGroupId(String paramName, Long groupId);

  boolean existsByParamNameAndGroupId(String paramName, Long groupId);
}