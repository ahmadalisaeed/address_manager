package com.addresses.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.addresses.manager.api.data.Address;

/**
 * Repository interface for the {@link Address} entities
 * 
 * @author ahmadalisaeed
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
}
