package com.inventory.core.model.repository;

import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	User findByUsername(String username);

	User findByIdAndStatus(long userId , Status status);

	List<User> findAllByStatusAndStoreInfo(Status status , StoreInfo storeInfo);

}
