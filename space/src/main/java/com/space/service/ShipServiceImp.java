package com.space.service;

import com.space.exception.NotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShipServiceImp implements ShipService {
    @Autowired
    private ShipRepository shipRepository;

    private final CheckerShipsParameter checkerShipsParameter = new CheckerShipsParameter();

    Logger logger = Logger.getLogger(ShipServiceImp.class.getName());

    @Override
    public Ship getById(String ids) {
        checkerShipsParameter.checkId(ids);
        long id = Long.parseLong(ids);
        if (!shipRepository.existsById(id)) {
            throw new NotFoundException();
        }
        return shipRepository.findById(id).get();
    }

    @Override
    public Page<Ship> getAll(Specification<Ship> shipSpecification, Pageable pageable) {
        return shipRepository.findAll(shipSpecification, pageable);
    }

    @Override
    public Integer count(Specification<Ship> shipSpecification) {
        return shipRepository.findAll(shipSpecification).size();
    }

    @Override
    public Ship create(Ship ship) {
        checkerShipsParameter.checkName(ship.getName());
        checkerShipsParameter.checkPlanet(ship.getPlanet());
        checkerShipsParameter.checkType(ship.getShipType());
        checkerShipsParameter.checkCrewSize(ship.getCrewSize());
        checkerShipsParameter.checkSpeed(ship.getSpeed());
        checkerShipsParameter.checkData(ship.getProdDate());
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        ship.calculateRating();
        logger.info(ship.toString());
        System.out.println(ship);
        System.out.println(ship.getRating());
        shipRepository.saveAndFlush(ship);
        return ship;
    }


    @Override
    public void delete(String ids) {
        checkerShipsParameter.checkId(ids);
        long id = Long.parseLong(ids);
        if (!shipRepository.existsById(id)) {
            throw new NotFoundException();
        }
        shipRepository.deleteById(id);
    }

    @Override
    public Ship update(String ids, Ship ship) {
        checkerShipsParameter.checkId(ids);
        long id = Long.parseLong(ids);
        if (!shipRepository.existsById(id)) {
            throw new NotFoundException();
        }
        Ship updateShip = shipRepository.findById(id).get();

        if (ship.getName() != null) {
            checkerShipsParameter.checkName(ship.getName());
            updateShip.setName(ship.getName());
        }
        if (ship.getPlanet() != null) {
            checkerShipsParameter.checkName(ship.getPlanet());
            updateShip.setPlanet(ship.getPlanet());
        }
        if (ship.getShipType() != null) {
            updateShip.setShipType(ship.getShipType());
        }
        if (ship.getProdDate() != null) {
            checkerShipsParameter.checkData(ship.getProdDate());
            updateShip.setProdDate(ship.getProdDate());
        }
        if (ship.getUsed() != null) {
            updateShip.setUsed(ship.getUsed());
        }
        if (ship.getCrewSize() != null) {
            checkerShipsParameter.checkCrewSize(ship.getCrewSize());
            updateShip.setCrewSize(ship.getCrewSize());
        }
        if (ship.getSpeed() != null) {
            checkerShipsParameter.checkSpeed(ship.getSpeed());
            updateShip.setSpeed(ship.getSpeed());
        }
        updateShip.calculateRating();
        shipRepository.saveAndFlush(updateShip);
        return updateShip;
    }


}
