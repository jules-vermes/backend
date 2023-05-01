package com.backend.backend.repositories;

import com.backend.backend.models.House;
import com.backend.backend.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IOwnerRepository extends JpaRepository<Owner,Long> {
    Owner findOwnerByName(String name);
    Owner findOwnerByHousesContains(House house);
}

