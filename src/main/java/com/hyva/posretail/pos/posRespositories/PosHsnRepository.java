package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Msiccode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosHsnRepository extends JpaRepository<Msiccode,Long> {
    List<Msiccode> findByStatus(String status);
    Msiccode findByCode(String code);
    Msiccode findByCodeAndMscidNotIn(String code,Long id);
    List<Msiccode> findAllByCodeContainingAndStatus(String code,String status,Pageable pageable);
    List<Msiccode> findAllByCodeContainingAndStatus(String code,String status);
    Msiccode findFirstByCodeContainingAndStatus(String code,String status,Sort sort);
    Msiccode findFirstByStatus(String status,Sort sort);
    List<Msiccode> findAllByStatus(String status,Pageable pageable);
    List<Msiccode> findByStatusAndCodeContaining(String status,String searchText);
}
