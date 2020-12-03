package com.addresses.manager.api.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.addresses.manager.api.data.Address;

public class AddressSpecification implements Specification<Address>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SearchCriteria criteria;
	
	public AddressSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(criteria.getKey())), 
                    criteria.getValue().toLowerCase() 
                );
	}

}
