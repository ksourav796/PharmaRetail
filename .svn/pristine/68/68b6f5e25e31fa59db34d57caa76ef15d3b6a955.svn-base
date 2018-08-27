package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosBankRepository extends JpaRepository<Bank,Long> {
        List<Bank> findAllByStatus(String status);
        Bank findAllByBankName(String name);
        Bank findByBankNameAndBankIdNotIn(String name,Long id);
}
