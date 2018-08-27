package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosItemTypeRepository extends JpaRepository<ItemType,Long> {
    ItemType findByItemTypeName(String name);
}
