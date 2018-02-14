package com.inventory.core.model.repository;

import com.inventory.core.model.entity.SubCategoryInfo;
import com.inventory.core.model.enumconstant.CategoryType;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.CategoryDomain;
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
public interface SubCategoryInfoRepository extends JpaRepository<SubCategoryInfo, Long>, JpaSpecificationExecutor<SubCategoryInfo> {

    SubCategoryInfo findById(long subCategoryId);

    SubCategoryInfo findByIdAndStatusAndStoreInfo_Id(long subCategoryId, Status status, long storeId);

    @Query("select new com.inventory.core.model.liteentity.CategoryDomain(s.id , s.name , s.code , s.description , s.depth , p.id , p.name) from SubCategoryInfo s left join s.parent  as p where s.id = ?1 and s.status = ?2 and s.storeInfo.id = ?3")
    CategoryDomain findLiteCategoryByIdAndStatusAndStoreInfo_Id(long subCategoryId, Status status, long storeId);

    SubCategoryInfo findByName(String name);

    @Query("select s from SubCategoryInfo s where s.name = ?1 and s.status = ?2 and s.storeInfo.id = ?3")
    SubCategoryInfo findByNameAndStatusAndStoreInfo(String name, Status status, long storeId);

    @Query("select s from SubCategoryInfo s where s.code = ?1 and s.status = ?2 and s.storeInfo.id = ?3")
    SubCategoryInfo findByCodeAndStatusAndStoreInfo(String code, Status status, long storeId);

    @Query("select sc from SubCategoryInfo s join s.childList as sc where s.status = ?1 and s.storeInfo.id = ?2 and s.id = ?3 order by s.id desc")
    List<SubCategoryInfo> findAllChildByStatusAndStoreInfoAndCategoryInfo(Status status, long storeId, long categoryId);

    @Query("select new com.inventory.core.model.liteentity.CategoryDomain(s.id , s.name , s.code , s.description , s.depth , p.id , p.name) from SubCategoryInfo s left join s.parent  as p where s.status = ?1 and s.storeInfo.id = ?2 order by s.id desc")
    List<CategoryDomain> findAllByStatusAndStoreInfo(Status status, long storeId);

    @Query("select new com.inventory.core.model.liteentity.CategoryDomain(s.id , s.name , s.code , s.description , s.depth , p.id , p.name) from SubCategoryInfo s left join s.parent  as p where s.status = ?1 and s.storeInfo.id = ?2 and s.type = ?3 order by s.id desc")
    List<CategoryDomain> findAllByStatusAndStoreInfoAndType(Status status, long storeId , CategoryType type);

}
