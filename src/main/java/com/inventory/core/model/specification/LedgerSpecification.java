package com.inventory.core.model.specification;

import com.inventory.core.model.dto.LedgerFilter;
import com.inventory.core.model.dto.LedgerFilterDTO;
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

    private LedgerFilterDTO ledgerFilter;

    public LedgerSpecification(){

    }

    public LedgerSpecification(LedgerFilterDTO ledgerFilter){
        super();
        this.ledgerFilter = ledgerFilter;
    }

    @Override
    public Predicate toPredicate(Root<LedgerInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        final List<Predicate> predicates = new ArrayList<Predicate>();

        if(ledgerFilter.getStatus()!=null){
            predicates.add(cb.equal(root.get("status"),ledgerFilter.getStatus()));
        }

        if(ledgerFilter.getFiscalYearId()!=null){
            predicates.add(cb.equal(root.get("fiscalYearInfo").get("id"),ledgerFilter.getFiscalYearId()));
        }

        if(ledgerFilter.getAccountId()!=null){
            predicates.add(cb.equal(root.get("accountInfo").get("id"),ledgerFilter.getAccountId()));
        }

        if(ledgerFilter.getFrom()!=null){
            predicates.add(cb.greaterThan(root.get("created"),ledgerFilter.getFrom()));
        }

        if(ledgerFilter.getTo()!=null){
            predicates.add(cb.lessThan(root.get("created"),ledgerFilter.getTo()));
        }

        predicates.add(cb.equal(root.get("storeInfo").get("id"),ledgerFilter.getStoreId()));

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
