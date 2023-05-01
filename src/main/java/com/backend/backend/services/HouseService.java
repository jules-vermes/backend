package com.backend.backend.services;

import com.backend.backend.models.House;
import com.backend.backend.models.Owner;
import com.backend.backend.repositories.IHouseRepository;
import com.backend.backend.repositories.IOwnerRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class HouseService {
    private IHouseRepository repository;
    private IOwnerRepository OwnerRepository;


    public HouseService(IHouseRepository repository,IOwnerRepository ownerRepository) {
        super();
        this.repository = repository;
        this.OwnerRepository=ownerRepository;
    }

    public List<House> getAllHouses() throws Exception {
        List<House> result=repository.findAll();
        if (!result.isEmpty()){
            return result;
        }
        throw new Exception("La BDD ne contient pas de maison");
    }

    public List<Owner> getAllOwners() throws Exception {
        List<Owner> result=OwnerRepository.findAll();
        if (!result.isEmpty()){
            return result;
        }
        throw new Exception("La BDD ne contient pas d'owner");
    }
    public House getHouseById(Long id){
        Optional<House> abc=repository.findById(id);
        if(abc.isPresent()){
            return abc.get();
        }
        return null;
    }

    public boolean createHouse(House house){
        if (house.getName().isEmpty() || house.getAdresse().isEmpty() || house.getName().isBlank() || house.getAdresse().isBlank() ||house.getCity().isBlank() || house.getState().isBlank() || house.getOwner() == null) {
            return false;
        }

        //si l'owner est déja existant dans la BDD
        try {
            if (OwnerRepository.findOwnerByName(house.getOwner().getName()).equals(house.getOwner())) {
                Owner getOwner = OwnerRepository.findById(house.getOwner().getId()).get();
                //Si la liste de maison ajoutée est différente de celle déjà existante pour le propriétaire.
                if (!house.getOwner().getHouses().equals(getOwner.getHouses())){
                    System.out.println("L'owner existe en BDD, modification de sa liste de maison");
                    List<House> tempSet=getOwner.getHouses();
                    tempSet.add(house);
                    getOwner.setHouses(tempSet);
                    OwnerRepository.save(getOwner);
                    repository.save(house);
                    return true;
                }
            }
            // Un null pointer Exception est jeté s'il n'existe pas déjà un owner dans la BDD et un NoSuchElementException si l'owner n'existe pas encore
        }catch (NoSuchElementException | NullPointerException e){
            System.out.println("L'owner n'existe pas en BDD, création de celui-ci");
            Owner temp= house.getOwner();
            List<House> housesTemp = temp.getHouses();
            housesTemp.add(house);
            temp.setHouses(housesTemp);

            house.setOwner(temp);
            repository.save(house);
            return true;
        }
        return false;
    }

    public boolean deleteHouse(@NotNull Long id){
        Optional<House> house= repository.findById(id);
        if (house.isPresent()){
            if (OwnerRepository.findOwnerByHousesContains(house.get()) != null){
                Owner getOwner= OwnerRepository.findOwnerByHousesContains(house.get());
                //supprime la maison de la liste de l'owner
                if (getOwner.getHouses().remove(house)) {
                    OwnerRepository.save(getOwner);
                }
            }
            repository.delete(house.get());
            return true;
        }
        else {
            return false;
        }

    }

    public void deleteAll(){
        repository.deleteAll();
    }


}
