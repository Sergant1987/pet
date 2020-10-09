package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.specification.ShipSpecification;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest/ships")
public class ShipController {

    private final Logger logger = Logger.getLogger(ShipController.class.getName());

    private final ShipSpecification specification = new ShipSpecification();

    @Autowired
    ShipService shipService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Ship> list(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planet", required = false) String planet,
            @RequestParam(name = "shipType", required = false) ShipType shipType,
            @RequestParam(name = "after", required = false) Long after,
            @RequestParam(name = "before", required = false) Long before,
            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
            @RequestParam(name = "minSpeed", required = false) Double minSpeed,
            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "maxRating", required = false) Double maxRating,
            @RequestParam(name = "order", required = false, defaultValue = "ID") ShipOrder order,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        Specification<Ship> shipSpecification =
                Specification.where(specification.selectByName(name)
                        .and(specification.selectByPlanet(planet))
                        .and(specification.selectByShipType(shipType))
                        .and(specification.selectByDate(after, before))
                        .and(specification.selectByUsed(isUsed))
                        .and(specification.selectBySpeed(minSpeed, maxSpeed))
                        .and(specification.selectByCrewSize(minCrewSize, maxCrewSize))
                        .and(specification.selectByRating(minRating, maxRating)));
        return shipService.getAll(shipSpecification, pageable).getContent();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ship get(@PathVariable("id") String id) {
        return shipService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Ship create(@RequestBody Ship ship) {
        return shipService.create(ship);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ship update(@PathVariable("id") String id, @RequestBody Ship ship) {
        return shipService.update(id, ship);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getCount(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planet", required = false) String planet,
            @RequestParam(name = "shipType", required = false) ShipType shipType,
            @RequestParam(name = "after", required = false) Long after,
            @RequestParam(name = "before", required = false) Long before,
            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
            @RequestParam(name = "minSpeed", required = false) Double minSpeed,
            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "maxRating", required = false) Double maxRating
    ) {

        Specification<Ship> shipSpecification =
                Specification.where(specification.selectByName(name)
                        .and(specification.selectByPlanet(planet))
                        .and(specification.selectByShipType(shipType))
                        .and(specification.selectByDate(after, before))
                        .and(specification.selectByUsed(isUsed))
                        .and(specification.selectBySpeed(minSpeed, maxSpeed))
                        .and(specification.selectByCrewSize(minCrewSize, maxCrewSize))
                        .and(specification.selectByRating(minRating, maxRating)));

        return shipService.count(shipSpecification);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {

        shipService.delete(id);
    }
}
