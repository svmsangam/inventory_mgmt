package com.inventory.core.model.repository;

import com.inventory.core.model.entity.ItemInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 8/11/17.
 */
@Repository
public interface ItemInfoRepository extends JpaRepository<ItemInfo , Long> , JpaSpecificationExecutor<ItemInfo>{

    ItemInfo findById(long itemInfoId);

    ItemInfo findByIdAndStatus(long itemInfoId , Status status);

    @Query("select i from ItemInfo i where i.id = ?1 and i.status = ?2 and i.productInfo.id = ?3 ")
    ItemInfo findByIdAndStatusAndProductInfo(long itemInfoId , Status status , long productInfoId);

    @Query("select i from ItemInfo i where i.id = ?1 and i.status = ?2 and i.productInfo.storeInfo.id = ?3 ")
    ItemInfo findByIdAndStatusAndStoreInfo(long itemInfoId , Status status , long storeInfoId);

    @Query("select i from ItemInfo i where i.id = ?1 and i.status = ?2 and i.productInfo.id = ?3 and i.productInfo.storeInfo.id = ?4 ")
    ItemInfo findByIdAndStatusAndProductInfoAndStoreInfo(long itemInfoId , Status status, long productInfoId , long storeInfoId);

    @Query("select i from ItemInfo i where i.status = ?1 and i.productInfo.storeInfo.id = ?2 order by i.id desc ")
    List<ItemInfo> findAllByStatusAndStoreInfo(Status status , long storeInfoId);

    @Query("select i from ItemInfo i where i.status = ?1 and i.productInfo.storeInfo.id = ?2 and i.inStock > 0 order by i.id desc ")
    List<ItemInfo> findAllByStatusAndStoreInfoHavingInStock(Status status , long storeInfoId);

    @Query("select i from ItemInfo i where i.status = ?1 and i.productInfo.storeInfo.id = ?2 and i.productInfo.id = ?3 order by i.id desc ")
    List<ItemInfo> findAllByStatusAndStoreInfoAndProductInfo(Status status , long storeInfoId , long productInfoId);

    @Query("select i from ItemInfo i where (i.status = ?2 and i.productInfo.storeInfo.id = ?3 and i.inStock > 0) and (i.tagInfo.name like concat('%' , ?1) or i.tagInfo.code like concat('%' , ?1) or i.productInfo.name like concat('%' , ?1) or i.productInfo.code like concat('%' , ?1) or i.productInfo.subCategoryInfo.name like concat('%' , ?1) or i.productInfo.subCategoryInfo.code like concat('%' , ?1) or i.productInfo.subCategoryInfo.categoryInfo.name like concat('%' , ?1) or i.productInfo.subCategoryInfo.categoryInfo.code like concat('%' , ?1))")
    List<ItemInfo> search(String query , Status status , long storeInfoId);
}
