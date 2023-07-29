package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LineStationRepository extends JpaRepository<LineEntity, Long> {
}
