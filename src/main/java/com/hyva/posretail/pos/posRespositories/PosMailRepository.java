package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosMailRepository extends JpaRepository<Mail, Long> {
    Mail findByUserName(String name);
}
