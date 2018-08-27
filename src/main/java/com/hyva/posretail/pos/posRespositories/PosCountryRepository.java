package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosCountryRepository extends JpaRepository<Country,Long> {
    List<Country> findByCountryId(Long countryId);
    Country findByCountryName(String countryName);
    List<Country> findAllByStatus(String status);
    Country findByCountryNameAndCountryIdNotIn(String name,Long id);
    List<Country> findAllByCountryNameContainingAndStatus(String name,String status,Pageable pageable);
    List<Country> findAllByCountryNameContainingAndStatus(String name,String status);
    Country findFirstByCountryNameContainingAndStatus(String name,String status,Sort sort);
    Country findFirstByStatus(String status,Sort sort);
    List<Country> findAllByStatus(String status,Pageable pageable);
    List<Country> findAllByStatusAndCountryNameContaining(String status,String searchText);
}
