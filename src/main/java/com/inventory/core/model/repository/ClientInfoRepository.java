package com.inventory.core.model.repository;

import com.inventory.core.model.entity.ClientInfo;
import com.inventory.core.model.enumconstant.ClientType;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo , Long> , JpaSpecificationExecutor<ClientInfo> {

    ClientInfo findById(long clientId);

    ClientInfo findByIdAndStatus(long clientId , Status status);

    ClientInfo findByIdAndStatusAndClientType(long clientId , Status status , ClientType clientType);

    List<ClientInfo> findAllByStatusAndClientType(Status status , ClientType clientType , Pageable pageable);

    ClientInfo findByContact(String contact);

    ClientInfo findByMobileNumber(String mobile);

    ClientInfo findByEmail(String email);

    @Query("select c from ClientInfo c where (c.status = ?1 and c.clientType = ?2 ) and (c.name like concat('%' , ?3 , '%') or c.companyName like concat('%' , ?3 , '%') or c.contact like concat('%' , ?3 , '%') or c.mobileNumber like concat('%' , ?3 , '%'))")
    List<ClientInfo> findAllByStatusAndClientTypeAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContains(Status status , ClientType clientType , String q , Pageable pageable);
}
