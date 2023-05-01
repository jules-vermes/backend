package com.backend.backend.controllers;


import com.backend.backend.models.House;
import com.backend.backend.models.Owner;
import com.backend.backend.services.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HouseController {
    private HouseService service;

    public HouseController(HouseService service) {
        this.service = service;
    }


    @GetMapping("/HouseId")
    House getHouseById(@RequestParam("id") Long id) {
        return this.service.getHouseById(id);
    }
    @GetMapping("/allHouses")
    List<House> getAllHouses() throws Exception {
        return this.service.getAllHouses();
    }

    @PostMapping("/house")
    ResponseEntity createHouse(@RequestBody House house){
        if (service.createHouse(house)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'objet n'a pas pu etre crée, surêment du à une saisie invalide");
        }
    }
    @DeleteMapping("/house")
    ResponseEntity deleteHouse(@RequestParam Long id){
        if(this.service.deleteHouse(id)){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.internalServerError().body("Couldn't find id or can't delete it");
        }
    }

    @DeleteMapping("/deleteAll")
    ResponseEntity deleteAll(){
        this.service.deleteAll();
        return ResponseEntity.ok().build();

    }

    @GetMapping("/allOwners")
    List<Owner> getAllOwners() throws Exception {
        return this.service.getAllOwners();
    }

}
