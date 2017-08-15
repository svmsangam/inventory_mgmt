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

	User findByIdAndStatus(long userId , Status status);

	User findById(long userId);

	@Query("select u from User u where u.status = ?1 and u.storeInfo.id = ?2 order by u.username asc ")
	List<User> findAllByStatusAndStoreInfo(Status status , StoreInfo storeInfo);

	List<User> findAllByStatusAndUserTypeIn(Status status , List<UserType> userTypeList);

	@Query("select u from User u where u.status = ?1 and u.userType in (?2) and u.storeInfo.id = ?3 order by u.username asc ")
	List<User> findAllByStatusAndUserTypeInAndStoreInfo(Status status , List<UserType> userTypeList , long storeId);
}
