package com.inventory.core.model.specification;

import com.inventory.core.model.dto.InvoiceFilterDTO;
import com.inventory.core.model.dto.LedgerFilter;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.LedgerInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiraj on 1/28/18.
 */
public class InvoiceSpecification implements Specification<InvoiceInfo> {

    InvoiceFilterDTO filterDTO;

    public InvoiceSpecification(){

    }

    public InvoiceSpecification(InvoiceFilterDTO filterDTO){
        super();
        this.filterDTO = filterDTO;
    }

    @Override
    public Predicate toPredicate(Root<InvoiceInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<Predicate>();

        if(filterDTO.getStatus()!=null){
            predicates.add(cb.equal(root.get("status"),filterDTO.getStatus()));
        }

        if(filterDTO.getFiscalYearId()!=null){
            predicates.add(cb.equal(root.get("fiscalYearInfo_id"),filterDTO.getFiscalYearId()));
        }

        if(filterDTO.getClientId()!=null){
            predicates.add(cb.equal(root.get("orderInfo_clientInfo_id"),filterDTO.getClientId()));
        }

        if(filterDTO.getFrom()!=null){
            predicates.add(cb.greaterThan(root.get("created"),filterDTO.getFrom()));
        }

        if(filterDTO.getTo()!=null){
            predicates.add(cb.lessThan(root.get("created"),filterDTO.getTo()));
        }

        if(filterDTO.getStoreInfoId()!=null){
            predicates.add(cb.equal(root.get("storeInfo_id"),filterDTO.getStoreInfoId()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
