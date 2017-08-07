package com.inventory.core.model.repository;

import com.inventory.core.model.entity.TagInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 8/6/17.
 */
@Repository
public interface TagInfoRepository extends JpaRepository<TagInfo , Long> , JpaSpecificationExecutor<TagInfo> {

    TagInfo findById(long tagId);

    @Query("select t from TagInfo t where t.id = ?1 and t.status = ?2 and t.storeInfo.id = ?3")
    TagInfo findByIdAndStatusAndStoreInfo(long tagId, Status status, long storeId);

    TagInfo findByName(String tagName);

    @Query("select t from TagInfo t where t.name = ?1 and t.status = ?2 and t.storeInfo.id = ?3")
    TagInfo findByNameAndStatusAndStoreInfo(String tagName, Status status, long storeId);

    @Query("select t from TagInfo t where t.status = ?1 and t.storeInfo.id = ?2")
    List<TagInfo> findAllByStatusAndStoreInfo(Status status, long storeId);
}

