package com.jangseop.tokyosubwaydatabase.repository;

import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<StationEntity, Long> {
}
