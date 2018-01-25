package com.inventory.core.model.repository;

import com.inventory.core.model.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dhiraj on 1/25/18.
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber , Long> {
}
