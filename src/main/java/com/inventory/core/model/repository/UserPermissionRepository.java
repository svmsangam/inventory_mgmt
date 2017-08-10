package com.inventory.core.model.repository;

import com.inventory.core.model.entity.UserPermission;
import com.inventory.core.model.enumconstant.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 8/10/17.
 */

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission , Long> , JpaSpecificationExecutor<UserPermission>{

    UserPermission findById(long userPermissionId);

    @Query("select u from UserPermission u where u.id = ?1 and u.user.id = ?2")
    UserPermission findByIdAndUser(long userPermissionId , long userId);

    @Query("select u from UserPermission u where u.user.id = ?1")
    UserPermission findByUser(long userId);

    @Query("select u from UserPermission u where u.user.id = ?1 and u.permissionList in (?2)")
    UserPermission findByUserAndPermissionList(long userId , List<Permission> permissionList);
}
