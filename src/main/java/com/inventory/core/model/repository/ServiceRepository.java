package com.inventory.core.model.repository;

import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 1/25/18.
 */
@Repository
public interface ServiceRepository extends JpaRepository<ServiceInfo, Long>{

    ServiceInfo findById(long serviceId);

    ServiceInfo findByTitle(String title);

    ServiceInfo findByIdAndStatus(long serviceId , Status status);

    List<ServiceInfo> findAllByStatus(Status status);
}
