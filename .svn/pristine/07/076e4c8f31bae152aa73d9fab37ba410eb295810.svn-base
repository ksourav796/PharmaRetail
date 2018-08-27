package com.hyva.posretail.pos.posRespositories;

import com.hyva.posretail.pos.posEntities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosAgentRepository extends JpaRepository<Agent,Long> {
    List<Agent> findAllByStatus(String status);
    Agent findAllByAgentName(String name);
    Agent findAllByAgentNameAndAgentIdNotIn(String name,Long id);
}
