package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LineRepository extends CrudRepository<LineEntity, Long> {

    // TODO findby~ 를 메서드명으로 설정하면 자동으로 생성되나? - 이렇게 안했더니 contextLoad() 과정에서 에러남.
    Optional<LineEntity> findByNumber(String number);

    // TODO 회사정보를 통한 노선 조회 파라미터 : 엔티티 vs 외래키
    List<LineEntity> findAllByCompany(CompanyEntity companyEntity);
    // List<LineEntity> findAllByCompany(Long companyId);
}
