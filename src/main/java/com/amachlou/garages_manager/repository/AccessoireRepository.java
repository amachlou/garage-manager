package com.amachlou.garages_manager.repository;

import com.amachlou.garages_manager.model.Accessoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccessoireRepository extends JpaRepository<Accessoire, Long> {
}
