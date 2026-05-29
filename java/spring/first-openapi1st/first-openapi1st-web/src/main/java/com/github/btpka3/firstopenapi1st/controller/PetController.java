package com.github.btpka3.firstopenapi1st.controller;

import com.github.btpka3.firstopenapi1st.api.PetApi;
import com.github.btpka3.firstopenapi1st.model.Pet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController implements PetApi {

    @Autowired
    private HttpServletResponse response;

    @Override
    public Pet getPetById(Long petId) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("doggie");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        return pet;
    }

    @Override
    public List<Pet> findPetsByStatus(List<String> status) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("doggie");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        return List.of(pet);
    }
}
