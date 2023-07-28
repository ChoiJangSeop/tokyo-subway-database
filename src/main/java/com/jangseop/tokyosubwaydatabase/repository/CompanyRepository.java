package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {

    Optional<CompanyEntity> findByName(String name);
}
