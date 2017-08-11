package com.inventory.core.model.repository;

import com.inventory.core.model.entity.CategoryInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/9/17.
 */
@Repository
@Transactional(readOnly = true)
public interface CategoryInfoRepository extends JpaRepository<CategoryInfo , Long> , JpaSpecificationExecutor<CategoryInfo> {

    //@Query("select c from CategoryInfo c where c.id = ?1")
    CategoryInfo findById(long categoryId);

    @Query("select c from CategoryInfo c where c.id = ?1 and c.status = ?2 and c.storeInfo.id = ?3")
    CategoryInfo findByIdAndStatusAndStoreInfo(long categoryId , Status status , long storeId);

    @Query("select c from CategoryInfo c where c.name = ?1 and c.status = ?2 and c.storeInfo.id = ?3")
    CategoryInfo findByNameAndStatusAndStoreInfo(String categoryName , Status status , long storeId);

    @Query("select c from CategoryInfo c where c.status = ?1 and c.storeInfo.id = ?2 order by c.id desc ")
    List<CategoryInfo> findAllByStatusAndStoreInfo(Status status , long storeId);

    @Query("select count (c) from CategoryInfo c where c.status = ?1 and c.storeInfo.id = ?2")
    Long countAllByStatusAndStoreInfo(Status status , long storeId);
}
