package com.inventory.core.model.repository;

import com.inventory.core.model.entity.Notification;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 1/19/18.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification , Long>{

    long countAllByStatusAndTo_Id(Status status ,long toUserId);

    long countAllByStatusAndTo_IdAndSeenAndSent(Status status , long toUserId , boolean seen , boolean sent);

    List<Notification> findAllByStatusAndTo_Id(Status status , long toUserId , Pageable pageable);
}
