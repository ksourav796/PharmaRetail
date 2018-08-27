package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.DirectSalesInvoiceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesInvoiceRepository extends JpaRepository<DirectSalesInvoiceTemplate,Long> {
    List<DirectSalesInvoiceTemplate> findAllBySiStatus(String status);
    DirectSalesInvoiceTemplate findAllByDSIId(Long dsiid);
    DirectSalesInvoiceTemplate findAllBySiNo(String dsiid);
    List<DirectSalesInvoiceTemplate> findAllBySiNoStartingWith(String sino);
    List<DirectSalesInvoiceTemplate> findAllByDSIIdGreaterThan(Long siid);
    List<DirectSalesInvoiceTemplate> findAllBySiStatusContainingAndSiNo(String status,String sino);
}
