package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByItemStatus(String itemStatus);
    List<Item> findByItemStatusAndItemNameContaining(String itemStatus,String itemName);
    Item findByItemName(String name);
    Item findByItemNameAndItemIdNotIn(String name,Long id);
    List<Item> findAllByItemCodeContainingAndItemStatus(String code,String status,Pageable pageable);
    List<Item> findAllByItemCodeContainingAndItemStatus(String code,String status);
    Item findFirstByItemCodeContainingAndItemStatus(String code,String status,Sort sort);
    Item findFirstByItemStatus(String status,Sort sort);
    List<Item> findAllByItemStatus(String status,Pageable pageable);
}
