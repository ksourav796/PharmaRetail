package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.NewPosPaymentTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewPosPaymentRepository extends JpaRepository<NewPosPaymentTypes,Long> {
    List<NewPosPaymentTypes> findAllBySiNo(String salesInvoiceTemplate);
}
