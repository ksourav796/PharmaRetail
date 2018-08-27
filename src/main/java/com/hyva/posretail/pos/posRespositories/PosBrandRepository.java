package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.ItemBrandName;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosBrandRepository extends JpaRepository<ItemBrandName,Long> {
    List<ItemBrandName> findByStatus(String status);
    ItemBrandName findFirstByBrandNameContainingAndStatus(String searchText,String status, Sort sort);
    ItemBrandName findByBrandName(String brandName);
    ItemBrandName findFirstByStatus(String status, Sort sort);
    List<ItemBrandName> findAllByStatus(String status, Pageable pageable);
    List<ItemBrandName> findAllByStatus(String status);
    List<ItemBrandName> findAllByBrandNameContainingAndStatus(String searchText,String status, Pageable pageable);
    List<ItemBrandName> findAllByBrandNameContainingAndStatus(String searchText,String status);
    ItemBrandName findByBrandNameAndBrandIdNotIn(String name,Long id);
}
