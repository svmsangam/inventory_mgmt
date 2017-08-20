package com.inventory.core.model.repository;

import com.inventory.core.model.entity.UserPermission;
import com.inventory.core.model.enumconstant.Permission;
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
public interface UserPermissionRepository extends JpaRepository<UserPermission , Long> , JpaSpecificationExecutor<UserPermission>{

    @Lock(LockModeType.OPTIMISTIC)
    UserPermission findById(long userPermissionId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select u from UserPermission u where u.id = ?1 and u.user.id = ?2")
    UserPermission findByIdAndUser(long userPermissionId , long userId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select u from UserPermission u where u.user.id = ?1")
    UserPermission findByUser(long userId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select u from UserPermission u where u.user.id = ?1 and u.permissionList in (?2)")
    UserPermission findByUserAndPermissionList(long userId , List<Permission> permissionList);
}
