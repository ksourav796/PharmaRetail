package com.hyva.posretail.pos.posRespositories;
import com.hyva.posretail.pos.posEntities.FormSetUp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosFormSetupRepository extends JpaRepository<FormSetUp,Long> {
    FormSetUp findAllByTypename(String type);
    FormSetUp findAllByTypenameAndFormsetupIdNotIn(String type,Long id);
    List<FormSetUp> findAllByTypenameContaining(String typeName, Pageable pageable);
    FormSetUp findFirstByTypenameContaining(String typeName,Sort sort);
    FormSetUp findFirstBy(Sort sort);
    List<FormSetUp> findAllBy(Pageable pageable);
}
