package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosPurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice,Long> {
    List<PurchaseInvoice> findAllByPiStatusContainingAndPino(String status,String pino);
    List<PurchaseInvoice> findAllByPiStatus(String status);
    List<PurchaseInvoice> findAllByPinoStartingWith(String pino);
    PurchaseInvoice findAllByPino(String pino);
    PurchaseInvoice findAllByPiStatusAndPiId(String status,Long piid);
    List<PurchaseInvoice> findAllByPiIdGreaterThan(Long piid);

}
