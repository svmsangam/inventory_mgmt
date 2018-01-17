package com.inventory.core.model.repository;

import com.inventory.core.model.entity.ReturnItemInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dhiraj on 1/17/18.
 */
@Repository
public interface ReturnItemInfoRepository extends JpaRepository<ReturnItemInfo , Long> {
}
