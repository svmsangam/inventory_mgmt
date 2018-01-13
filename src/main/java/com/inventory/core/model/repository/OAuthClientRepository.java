package com.inventory.core.model.repository;

import com.inventory.core.model.entity.OAuthClient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OAuthClientRepository extends CrudRepository<OAuthClient,String>,JpaSpecificationExecutor<OAuthClient> {

}
