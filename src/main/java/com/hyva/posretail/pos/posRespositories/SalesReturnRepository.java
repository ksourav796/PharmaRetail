package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.SalesReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesReturnRepository extends JpaRepository<SalesReturn,Long> {
}
