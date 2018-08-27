package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.ItemBrandName;
import com.hyva.posretail.pos.posEntities.ItemCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosCategoryRepository extends JpaRepository<ItemCategory,Long> {
    ItemCategory findFirstByItemCategoryNameContainingAndStatus(String searchText, String status, Sort sort);
    List<ItemCategory> findAllByItemCategoryNameContainingAndStatus(String searchText,String status,Pageable pageable);
//    List<ItemCategory> findAllByItemCategoryNameContainingAndStatus(String searchText,String searchText1,String status);
    ItemCategory findFirstByStatus(String status,Sort sort);
    List<ItemCategory> findAllByStatus(String status,Pageable pageable);
    List<ItemCategory> findAllByStatus(String status);
    List<ItemCategory> findByStatusAndItemCategoryName(String status,String searchText);
    ItemCategory findByItemCategoryName(String name);
    ItemCategory findByItemCategoryNameAndItemCategoryIdNotIn(String name,Long id);

}
