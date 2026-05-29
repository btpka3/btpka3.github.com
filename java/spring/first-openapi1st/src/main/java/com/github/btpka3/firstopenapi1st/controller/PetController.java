package com.github.btpka3.firstopenapi1st.controller;

import com.github.btpka3.firstopenapi1st.api.PetApi;
import com.github.btpka3.firstopenapi1st.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController implements PetApi {

    @Override
    public ResponseEntity<Pet> getPetById(Long petId) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("doggie");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        return ResponseEntity.ok(pet);
    }

    @Override
    public ResponseEntity<List<Pet>> findPetsByStatus(List<String> status) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("doggie");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        return ResponseEntity.ok(List.of(pet));
    }
}
