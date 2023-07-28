package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import org.springframework.data.repository.CrudRepository;

public interface LineStationRepository extends CrudRepository<LineEntity, Long> {
}
