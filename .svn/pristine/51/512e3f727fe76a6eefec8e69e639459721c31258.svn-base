package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosCustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findAllByStatus(String status);
    Customer findAllByCustomerName(String name);
    Customer findAllByCustomerNameAndCustomerIdNotIn(String name,Long id);
    List<Customer> findAllByCustomerNameContainingAndStatus(String name,String status,Pageable pageable);
    List<Customer> findAllByCustomerNameContainingAndStatus(String name,String status);
    Customer findFirstByCustomerNameContainingAndStatus(String name,String status,Sort sort);
    Customer findFirstByStatus(String status,Sort sort);
    List<Customer> findAllByStatus(String status,Pageable pageable);
    List<Customer> findByStatusAndCustomerNameContaining(String status,String searchText);
}
