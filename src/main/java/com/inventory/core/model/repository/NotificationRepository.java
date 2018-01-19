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

    long countAllByStatusAndStoreInfo_IdAndTo_Id(Status status , long storeInfoId , long toUserId);

    long countAllByStatusAndStoreInfo_IdAndTo_IdAndSeenAndSent(Status status , long storeInfoId , long toUserId , boolean seen , boolean sent);

    List<Notification> findAllByStatusAndStoreInfo_IdAndTo_Id(Status status , long storeInfoId , long toUserId , Pageable pageable);
}
