package com.irrigation.plot.repository;

import com.irrigation.plot.entity.IrrigationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigationStatusRepository extends JpaRepository<IrrigationStatus, Long> {}
