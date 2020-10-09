package com.space.specification;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ShipSpecification {


    public Specification<Ship> selectByName(String name) {
        return selectString(name, "name");
    }

    public Specification<Ship> selectByPlanet(String planet) {
        return selectString(planet, "planet");

    }

    private Specification<Ship> selectString(String name, String field) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (name == null) return null;
            return criteriaBuilder.like(root.get(field), "%" + name + "%");
        };
    }

    public Specification<Ship> selectByShipType(ShipType shipType) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (shipType == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("shipType"), shipType);
        };
    }

    public Specification<Ship> selectBySpeed(Double min, Double max) {
        String param = "speed";
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return null;
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(param), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(param), min);
            }
            return criteriaBuilder.between(root.get(param), min, max);
        };
    }

    public Specification<Ship> selectByCrewSize(Integer min, Integer max) {
        String param = "crewSize";
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return null;
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(param), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(param), min);
            }
            return criteriaBuilder.between(root.get(param), min, max);
        };
    }

    public Specification<Ship> selectByRating(Double min, Double max) {
        String param = "rating";
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return null;
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(param), max);
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(param), min);
            }
            return criteriaBuilder.between(root.get(param), min, max);
        };
    }

    public Specification<Ship> selectByDate(Long min, Long max) {
        String param = "prodDate";
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return null;
            if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(param), new Date(max));
            }
            if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(param), new Date(min));
            }
            return criteriaBuilder.between(root.get(param), new Date(min), new Date(max));
        };
    }

    public Specification<Ship> selectByUsed(Boolean isUsed) {
        return (Specification<Ship>) (root, query, criteriaBuilder) -> {
            if (isUsed == null) return null;
            return criteriaBuilder.equal(root.get("isUsed"), isUsed);
        };
    }

}
