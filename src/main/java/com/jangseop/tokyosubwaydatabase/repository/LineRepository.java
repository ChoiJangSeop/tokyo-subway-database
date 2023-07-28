package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LineRepository extends CrudRepository<LineEntity, Long> {

    Optional<LineEntity> findByNumber(String number);

    // FIXME 회사정보를 통한 노선 조회 파라미터 : 엔티티 vs 외래키
    List<LineEntity> findAllByCompany(CompanyEntity companyEntity);
    // List<LineEntity> findAllByCompany(Long companyId);
}
