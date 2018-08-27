package com.hyva.posretail.pos.posRespositories;
import com.hyva.posretail.pos.posEntities.PurchaseInvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosPurchaseInvoiceDetailsRepository extends JpaRepository<PurchaseInvoiceDetails,Long> {
    List<PurchaseInvoiceDetails> findAllByPINo(String purchaseInvoiceNo);
    PurchaseInvoiceDetails findAllByPINoAndItemName(String purchaseInvoice,String itemName);
}
