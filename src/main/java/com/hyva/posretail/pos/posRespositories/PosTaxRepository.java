package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Tax;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosTaxRepository extends JpaRepository<Tax,Long> {
List<Tax> findByTaxGroup(String taxGroup);
Tax findByTaxGroupAndTaxName(String taxGroup,String taxName);
Tax findByTaxNameAndTaxIdNotIn(String name,Long id);
Tax findByTaxName(String name);
List<Tax> findByTaxStatus(String status);
    List<Tax> findAllByTaxNameContainingAndTaxStatus(String name,String status,Pageable pageable);
    List<Tax> findAllByTaxNameContainingAndTaxStatus(String name,String status);
    Tax findFirstByTaxNameContainingAndTaxStatus(String name,String status,Sort sort);
    Tax findFirstByTaxStatus(String status,Sort sort);
    List<Tax> findAllByTaxStatus(String status,Pageable pageable);
    List<Tax> findByTaxStatusAndTaxNameContaining(String status,String searchText);

}
