package com.inventory.core.model.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.inventory.core.model.dto.ProductFilterDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.ProductInfo;

/**
 * Created by dhiraj on 1/28/18.
 */
public class ProductSpecification implements Specification<ProductInfo> {

	ProductFilterDTO filterDTO;

	public ProductSpecification() {

	}

	public ProductSpecification(ProductFilterDTO filterDTO) {
		super();
		this.filterDTO = filterDTO;
	}

	@Override
	public Predicate toPredicate(Root<ProductInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (filterDTO.getStatus() != null) {
			predicates.add(cb.equal(root.get("status"), filterDTO.getStatus()));
		}

		if(filterDTO.getStoreInfoId()!=null){
            predicates.add(cb.equal(root.get("storeInfo").get("id"),filterDTO.getStoreInfoId()));
        }
		
		if (filterDTO.getName() != null) {
			predicates.add(cb.like(root.get("name"), filterDTO.getName() + "%"));
		}

		if (filterDTO.getTrendingLevel() != null) {
			predicates.add(cb.equal(root.get("trendingLevel"), filterDTO.getTrendingLevel()));
		}

		if (filterDTO.getSubCategoryId() > 0) {
			predicates.add(cb.equal(root.get("subCategoryInfo").get("id"), filterDTO.getSubCategoryId()));
		}

		/*
		 * if(filterDTO.getGreaterThanInStock()>0){
		 * predicates.add(cb.greaterThanOrEqualTo(root.get("invoiceDate"),filterDTO.
		 * getFrom())); }
		 * 
		 * if(filterDTO.getLessThanInStock()>0ull){
		 * predicates.add(cb.lessThanOrEqualTo(root.get("invoiceDate"),filterDTO.getTo()
		 * )); }
		 */

		return cb.and(predicates.toArray(new Predicate[] {}));
	}
}
