package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.DirectSalesInvoiceDetailsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesInvoiceDetailsRepository extends JpaRepository<DirectSalesInvoiceDetailsTemplate,Long> {
    DirectSalesInvoiceDetailsTemplate findBySINoAndItemName(String sino,String itemName);
    List<DirectSalesInvoiceDetailsTemplate> findBySINo(String sino);

}
