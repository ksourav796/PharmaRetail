package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Currency;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosCurrencyRepository extends JpaRepository<Currency,Long> {
    List<Currency> findAllByStatus(String status);
    List<Currency> findByCurrencyId(Long currencyId);
    Currency findByCurrencyName(String currencyName);
    Currency findByCurrencyNameAndCurrencyIdNotIn(String name,Long id);
    List<Currency> findAllByCurrencyNameContainingAndStatus(String name,String status,Pageable pageable);
    List<Currency> findAllByCurrencyNameContainingAndStatus(String name,String status);
    Currency findFirstByCurrencyNameContainingAndStatus(String name,String status,Sort sort);
    Currency findFirstByStatus(String status,Sort sort);
    List<Currency> findAllByStatus(String status,Pageable pageable);
    List<Currency> findAllByStatusAndCurrencyNameContaining(String status,String searchText);
}
