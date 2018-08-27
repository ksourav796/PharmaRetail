package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Supplier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosSupplierRepository extends JpaRepository<Supplier,Long> {
    List<Supplier> findAllByStatus(String status);
    Supplier findBySupplierName(String name);
    Supplier findBySupplierNameAndSupplierIdNotIn(String name,Long id);
    List<Supplier> findAllBySupplierNameContainingAndStatus(String name,String status,Pageable pageable);
    List<Supplier> findAllBySupplierNameContainingAndStatus(String name,String status);
    Supplier findFirstBySupplierNameContainingAndStatus(String name,String status,Sort sort);
    Supplier findFirstByStatus(String status,Sort sort);
    List<Supplier> findAllByStatus(String status,Pageable pageable);
    List<Supplier> findByStatusAndSupplierNameContaining(String status,String searchText);
}
