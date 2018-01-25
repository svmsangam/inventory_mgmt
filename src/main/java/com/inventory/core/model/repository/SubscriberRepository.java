package com.inventory.core.model.repository;

import com.inventory.core.model.entity.Subscriber;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 1/25/18.
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber , Long> {

    Subscriber findByIdAndStatus(long subscriberId , Status status);

    Subscriber findById(long subscriberId);

    List<Subscriber> findAllByStatus(Status status);
}
