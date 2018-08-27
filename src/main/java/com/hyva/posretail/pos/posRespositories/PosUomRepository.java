package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.UnitOfMeasurement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosUomRepository extends JpaRepository<UnitOfMeasurement,Long> {
    List<UnitOfMeasurement> findAllByUnitOfMeasurementStatus(String unitOfMeasurementStatus);
    UnitOfMeasurement findByUnitOfMeasurementName(String name);
    UnitOfMeasurement findByUnitOfMeasurementNameAndUnitOfMeasurementIdNotIn(String name,Long id);
    List<UnitOfMeasurement> findAllByUnitOfMeasurementNameContainingAndUnitOfMeasurementStatus(String name,String status,Pageable pageable);
    List<UnitOfMeasurement> findAllByUnitOfMeasurementNameContainingAndUnitOfMeasurementStatus(String name,String status);
    UnitOfMeasurement findFirstByUnitOfMeasurementNameContainingAndUnitOfMeasurementStatus(String name,String status,Sort sort);
    UnitOfMeasurement findFirstByUnitOfMeasurementStatus(String status,Sort sort);
    List<UnitOfMeasurement> findAllByUnitOfMeasurementStatus(String status,Pageable pageable);
    List<UnitOfMeasurement> findAllByUnitOfMeasurementStatusAndUnitOfMeasurementNameContaining(String status,String searchText);
}
