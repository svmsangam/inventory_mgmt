package com.inventory.core.model.repository;

import com.inventory.core.model.entity.SubscriberService;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 1/26/18.
 */
@Repository
public interface SubscriberServiceRepository extends JpaRepository<SubscriberService , Long> {

    List<SubscriberService> findAllByStatusAndSubscriber_Id(Status status , long subscriberId);

    List<SubscriberService> findAllByStatusAndSubscriber_IdAndSelected(Status status , long subscriberId , boolean selected);


}
