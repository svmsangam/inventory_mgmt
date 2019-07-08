package com.inventory.core.model.repository;

import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.model.entity.SubscriberService;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 1/26/18.
 */
@Repository
public interface SubscriberServiceRepository extends JpaRepository<SubscriberService , Long> {

    List<SubscriberService> findAllByStatusAndSubscriber_Id(Status status , long subscriberId);

    @Query("select ss.serviceInfo from SubscriberService ss where ss.status = ?1 and ss.subscriber.id =?2")
    List<ServiceInfo> findAllServiceByStatusAndSubscriber_Id(Status status , long subscriberId);

    List<SubscriberService> findAllByStatusAndSubscriber_IdAndSelected(Status status , long subscriberId , boolean selected);


}
