package com.inventory.core.model.specification;

import com.inventory.core.model.dto.LedgerFilter;
import com.inventory.core.model.entity.LedgerInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiraj on 12/27/17.
 */

public class LedgerSpecification implements Specification<LedgerInfo> {

    private LedgerFilter ledgerFilter;

    public LedgerSpecification(){

    }

    public LedgerSpecification(LedgerFilter ledgerFilter){
        super();
        this.ledgerFilter = ledgerFilter;
    }

    @Override
    public Predicate toPredicate(Root<LedgerInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        final List<Predicate> predicates = new ArrayList<Predicate>();

        if(ledgerFilter.getStatus()!=null){
            predicates.add(cb.equal(root.get("status"),ledgerFilter.getStatus()));
        }

        if(ledgerFilter.getFiscalYearInfo()!=null){
            predicates.add(cb.equal(root.get("fiscalYearInfo"),ledgerFilter.getFiscalYearInfo()));
        }

        if(ledgerFilter.getAccountInfo()!=null){
            predicates.add(cb.equal(root.get("accountInfo"),ledgerFilter.getAccountInfo()));
        }

        if(ledgerFilter.getFrom()!=null){
            predicates.add(cb.greaterThan(root.get("created"),ledgerFilter.getFrom()));
        }

        if(ledgerFilter.getTo()!=null){
            predicates.add(cb.lessThan(root.get("created"),ledgerFilter.getTo()));
        }

        if(ledgerFilter.getStoreInfo()!=null){
            predicates.add(cb.equal(root.get("storeInfo"),ledgerFilter.getStoreInfo()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
