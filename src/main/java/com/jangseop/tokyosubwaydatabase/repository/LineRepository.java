package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LineRepository extends JpaRepository<LineEntity, Long> {

    Optional<LineEntity> findByNumber(String number);

    List<LineEntity> findAllByCompany(CompanyEntity companyEntity);
    // List<LineEntity> findAllByCompany(Long companyId);
}
