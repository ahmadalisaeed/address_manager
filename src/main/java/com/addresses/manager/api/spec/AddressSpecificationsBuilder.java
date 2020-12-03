package com.addresses.manager.api.spec;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.addresses.manager.api.data.Address;

public final class AddressSpecificationsBuilder {
	
    private final List<SearchCriteria> params;

    public AddressSpecificationsBuilder() {
        params = new ArrayList<>();
    }
    
    public final AddressSpecificationsBuilder with(String key, String value) { 
        params.add(new SearchCriteria(key, value));
        return this;
    }
	
    public Specification<Address> build() {
        if (params.isEmpty()) {
            return null;
        }

        List<Specification<Address>> specs = params.stream()
          .map(AddressSpecification::new)
          .collect(Collectors.toList());

        Specification<Address> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).or(specs.get(i));
        }

        return result;
    }

}