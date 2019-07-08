package com.inventory.core.model.specification;

import com.inventory.core.model.dto.InvoiceFilterDTO;
import com.inventory.core.model.dto.OrderFilterDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.OrderInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification  implements Specification<OrderInfo> {

    OrderFilterDTO filterDTO;

    public OrderSpecification(){

    }

    public OrderSpecification(OrderFilterDTO filterDTO){
        super();
        this.filterDTO = filterDTO;
    }

    @Override
    public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        final List<Predicate> predicates = new ArrayList<Predicate>();

        if(filterDTO.getStatus()!=null){
            predicates.add(cb.equal(root.get("status"),filterDTO.getStatus()));
        }

        if(filterDTO.getClientId()!=null){
            predicates.add(cb.equal(root.get("clientInfo").get("id"),filterDTO.getClientId()));
        }

        if(filterDTO.getFrom()!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("orderDate"),filterDTO.getFrom()));
        }

        if(filterDTO.getTo()!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("orderDate"),filterDTO.getTo()));
        }

        if(filterDTO.getDlfrom()!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("deliveryDate"),filterDTO.getDlfrom()));
        }

        if(filterDTO.getDlto()!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("deliveryDate"),filterDTO.getDlto()));
        }

        predicates.add(cb.equal(root.get("storeInfo").get("id"),filterDTO.getStoreId()));

        if(filterDTO.getAmountGt()!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("grandTotal"),filterDTO.getAmountGt()));
        }

        if(filterDTO.getAmountLt()!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("grandTotal"),filterDTO.getAmountLt()));
        }


        if(filterDTO.getTrack()!=null){
            predicates.add(cb.equal(root.get("saleTrack"),filterDTO.getTrack()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
