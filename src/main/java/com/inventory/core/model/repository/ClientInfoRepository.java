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

    ClientInfo findByIdAndStatusAndStoreInfo_Id(long clientId , Status status , long storeInfoId);

    ClientInfo findByIdAndStatusAndClientTypeAndStoreInfo_Id(long clientId , Status status , ClientType clientType , long storeInfoId);

    long countAllByStatusAndAndClientTypeAndStoreInfo_Id(Status status , ClientType clientType , long storeInfoId);

    List<ClientInfo> findAllByStatusAndClientTypeAndStoreInfo_Id(Status status , ClientType clientType , long storeInfoId  , Pageable pageable);

    ClientInfo findByContactAndStoreInfo_Id(String contact , long storeInfoId );

    ClientInfo findByMobileNumberAndStoreInfo_Id(String mobile , long storeInfoId );

    ClientInfo findByEmailAndStoreInfo_Id(String email , long storeInfoId );

    @Query("select c from ClientInfo c where (c.status = ?1 and c.clientType = ?2 and c.storeInfo.id = ?4) and (c.name like concat('%' , ?3 , '%') or c.companyName like concat('%' , ?3 , '%') or c.contact like concat('%' , ?3 , '%') or c.mobileNumber like concat('%' , ?3 , '%'))")
    List<ClientInfo> findAllByStatusAndClientTypeAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContainsAndStoreInfo(Status status , ClientType clientType , String q , long storeInfoId  , Pageable pageable);

    @Query("select count (c) from ClientInfo c where (c.status = ?1 and c.clientType = ?2 and c.storeInfo.id = ?4) and (c.name like concat('%' , ?3 , '%') or c.companyName like concat('%' , ?3 , '%') or c.contact like concat('%' , ?3 , '%') or c.mobileNumber like concat('%' , ?3 , '%'))")
    Long countAllByStatusAndClientTypeAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContainsAndStoreInfo(Status status , ClientType clientType , String q , long storeInfoId );

    @Query("select c from ClientInfo c where (c.status = ?1 and c.storeInfo.id = ?3) and (c.name like concat('%' , ?2 , '%') or c.companyName like concat('%' , ?2 , '%') or c.contact like concat('%' , ?2 , '%') or c.mobileNumber like concat('%' , ?2 , '%'))")
    List<ClientInfo> findAllByStatusAndNameContainsOrCompanyNameContainsOrContactOrMobileNumberContainsAndStoreInfo(Status status , String q , long storeInfoId , Pageable pageable);
}
