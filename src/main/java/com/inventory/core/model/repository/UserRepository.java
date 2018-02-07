package com.inventory.core.model.repository;

import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    List<User> findAllByUserTypeAndStoreInfo_Id(UserType userType , long storeId);

    User findByIdAndStatus(long userId, Status status);

    User findById(long userId);

    @Query("select u from User u where u.status = ?1 and u.storeInfo.id = ?2 order by u.username asc ")
    List<User> findAllByStatusAndStoreInfo(Status status, StoreInfo storeInfo);

    @Query("select u from User u where u.storeInfo.id in (?1)")
    List<User> findAllByStoreInfo_IdIn(List<Long> storeIdList);

    @Query("select u from User u where u.status = ?1 and u.userType in (?2) order by u.username asc ")
    List<User> findAllByStatusAndUserTypeIn(Status status, List<UserType> userTypeList);

    @Query("select u from User u where u.status = ?1 and u.userType in (?2) and u.storeInfo.id in (?3) order by u.username asc ")
    List<User> findAllByStatusAndUserTypeInAndSuperAdmin(Status status, List<UserType> userTypeList , List<Long> storeList);

    @Query("select u from User u where u.status = ?1 and u.userType in (?2) and u.storeInfo.id = ?3 order by u.username asc ")
    List<User> findAllByStatusAndUserTypeInAndStoreInfo(Status status, List<UserType> userTypeList, long storeId);

    @Query("select count (u) from User u where u.storeInfo.id = ?1 and u.status = ?2 ")
    long countAllByStoreInfoAndStatus(long storeInfoId , Status status);
}
