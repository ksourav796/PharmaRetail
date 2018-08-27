package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.PaymentMethod;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosPaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
    List<PaymentMethod> findAllByStatus(String status);
    PaymentMethod findByPaymentmethodName(String status);
    PaymentMethod findByPaymentmethodNameAndPaymentmethodIdNotIn(String status,Long id);
    List<PaymentMethod> findAllByPaymentmethodNameContainingAndStatus(String name,String status,Pageable pageable);
    List<PaymentMethod> findAllByPaymentmethodNameContainingAndStatus(String name,String status);
    PaymentMethod findFirstByPaymentmethodNameContainingAndStatus(String name,String status,Sort sort);
    PaymentMethod findFirstByStatus(String status,Sort sort);
    List<PaymentMethod> findAllByStatus(String status,Pageable pageable);
}
