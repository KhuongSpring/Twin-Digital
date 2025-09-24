package org.example.staticspecservice.repository;

import org.example.staticspecservice.domain.entity.StaticSpecGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaticSpecGroupRepository extends JpaRepository<StaticSpecGroup, Long> {

  Optional<StaticSpecGroup> findByGroupName(String groupName);

  boolean existsByGroupName(String groupName);
}