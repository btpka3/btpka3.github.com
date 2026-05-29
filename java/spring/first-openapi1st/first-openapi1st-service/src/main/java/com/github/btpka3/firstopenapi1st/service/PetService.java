package com.github.btpka3.firstopenapi1st.service;

import com.github.btpka3.firstopenapi1st.model.Pet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    public Pet getPetById(Long petId) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("doggie");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        return pet;
    }

    public List<Pet> findPetsByStatus(List<String> status) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("doggie");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        return List.of(pet);
    }
}
