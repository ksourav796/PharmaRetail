package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.State;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosStateRepository extends JpaRepository<State,Long> {
  List<State> findAllByCountryName(String countryName);
  State findByStateName(String stateName);
  State findByStateNameAndIdNotIn(String stateName,Long id);
  List<State> findAllByStatusAndCountryNameContainingOrStateNameContaining(String status,String name,String statename,Pageable pageable);
  List<State> findAllByStatusAndCountryNameContainingOrStateNameContaining(String status,String name,String statename);
  State findFirstByStatusAndCountryNameContainingOrStateNameContaining(String status,String name,String statename,Sort sort);
  State findFirstByStatus(String status,Sort sort);
  List<State> findAllByStatus(String status,Pageable pageable);
  List<State> findAllByStatus(String status);
  List<State> findAllByStatusAndStateNameContaining(String status,String searchtext);
}
