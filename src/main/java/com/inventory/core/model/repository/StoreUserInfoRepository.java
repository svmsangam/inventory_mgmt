package com.inventory.core.model.repository;

import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.entity.StoreUserInfo;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by dhiraj on 8/10/17.
 */
@Repository
@Transactional(readOnly = true)
public interface StoreUserInfoRepository extends JpaRepository<StoreUserInfo , Long> , JpaSpecificationExecutor<StoreUserInfo> {

    @Lock(LockModeType.OPTIMISTIC)
    StoreUserInfo findById(long storeUserInfoId);

    @Lock(LockModeType.OPTIMISTIC)
    StoreUserInfo findByIdAndStatus(long storeUserInfoId , Status status);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from StoreUserInfo s where s.storeInfo.id = ?1 and s.user.id = ?2 and s.status = ?3")
    StoreUserInfo findByStoreInfoAndUserAndStatus(long storeId , long userId , Status status);

    @Query("select s.user from StoreUserInfo s where s.storeInfo.id = ?1 and s.status = ?2 order by s.user.username asc ")
    List<User> findAllByStoreInfoAndStatus(long storeId , Status status);

    @Query("select s.storeInfo from StoreUserInfo s where s.user.id = ?1 and s.status = ?2 order by s.storeInfo.name asc ")
    List<StoreInfo> findAllByUserAndStatus(long userId ,Status status);
}
