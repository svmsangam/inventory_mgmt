package com.inventory.core.model.repository;

import com.inventory.core.model.entity.TagInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/6/17.
 */
@Repository
@Transactional(readOnly = true)
public interface TagInfoRepository extends JpaRepository<TagInfo, Long>, JpaSpecificationExecutor<TagInfo> {

    TagInfo findById(long tagId);

    @Query("select t from TagInfo t where t.id = ?1 and t.status = ?2 and t.storeInfo.id = ?3")
    TagInfo findByIdAndStatusAndStoreInfo(long tagId, Status status, long storeId);

    TagInfo findByName(String tagName);

    @Query("select t from TagInfo t where t.status = ?2 and (t.name like concat('%'  , ?1) or t.name like  concat( ?1 , '%') or t.name like  concat('%', ?1 , '%') or t.code like concat('%'  , ?1) or t.code like  concat( ?1 , '%') or t.code like  concat('%', ?1 , '%'))")
    List<TagInfo> findAllBySearch(String query , Status status , Pageable pageable);

    //@Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select t from TagInfo t where t.name = ?1 and t.status = ?2 and t.storeInfo.id = ?3")
    TagInfo findByNameAndStatusAndStoreInfo(String tagName, Status status, long storeId);

    @Query("select t from TagInfo t where t.code = ?1 and t.status = ?2 and t.storeInfo.id = ?3")
    TagInfo findByCodeAndStatusAndStoreInfo(String tagCode, Status status, long storeId);

    @Query("select t from TagInfo t where t.status = ?1 and t.storeInfo.id = ?2 order by t.id desc")
    List<TagInfo> findAllByStatusAndStoreInfo(Status status, long storeId);

    @Query("select count (t) from TagInfo t where t.status = ?1 and t.storeInfo.id = ?2")
    Long countAllByStatusAndStoreInfo(Status status, long storeId);
}

