package com.inventory.core.model.repository;

import com.inventory.core.model.entity.AgentInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentInfoRepository extends JpaRepository<AgentInfo , Long> {

    AgentInfo findByIdAndStatus(long agentId , Status status);

    AgentInfo findByStatusAndUser_Id(Status status , long userId);

    List<AgentInfo> findAllByStatus(Status status , Pageable pageable);
}
