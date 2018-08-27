package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.PurchaseReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseReturnRepository extends JpaRepository<PurchaseReturn,Long> {
}
