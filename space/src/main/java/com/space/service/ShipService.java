package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface ShipService {

    Ship getById(String id);

    Page<Ship> getAll(Specification<Ship> shipSpecification, Pageable pageable);

    Ship create(Ship ship);

    Integer count(Specification<Ship> shipSpecification);

    void delete(String id);

    Ship update(String ids, Ship ship);
}
