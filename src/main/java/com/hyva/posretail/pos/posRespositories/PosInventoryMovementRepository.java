package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosInventoryMovementRepository extends JpaRepository<InventoryMovement,Long> {
}
