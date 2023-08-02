package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LineStationRepository extends JpaRepository<LineStationEntity, Long> {

    List<LineStationEntity> findAllByLine(Long lineId);

    List<LineStationEntity> findAllByStation(Long stationId);
}
