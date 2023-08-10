package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.FarePolicyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FarePolicyRepository extends JpaRepository<FarePolicyEntity, Long> {

    List<FarePolicyEntity> findAllByLine(LineEntity line);
}
