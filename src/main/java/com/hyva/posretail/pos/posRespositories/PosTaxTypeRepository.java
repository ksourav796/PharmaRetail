package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.TaxType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosTaxTypeRepository extends JpaRepository<TaxType,Long> {
    TaxType findByTaxTypeName(String taxTypeName);
    TaxType findByTaxTypeNameAndTaxTypeIdNotIn(String taxTypeName,Long id);
    List<TaxType> findAllByTaxTypeNameContaining(String typeName, Pageable pageable);
    List<TaxType> findAllByTaxTypeNameContaining(String typeName);
    TaxType findFirstByTaxTypeNameContaining(String typeName,Sort sort);
    TaxType findFirstBy(Sort sort);
    List<TaxType> findAllBy(Pageable pageable);
}
