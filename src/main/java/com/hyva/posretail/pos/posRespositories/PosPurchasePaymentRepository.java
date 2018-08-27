package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.NewPosPurchasePaymentTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosPurchasePaymentRepository extends JpaRepository<NewPosPurchasePaymentTypes,Long> {
    NewPosPurchasePaymentTypes findAllByPino(String purchaseInvoice);
}
