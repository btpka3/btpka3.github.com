package com.github.btpka3.firstopenapi1st.controller;

import com.github.btpka3.firstopenapi1st.api.PetApi;
import com.github.btpka3.firstopenapi1st.model.CreatePetRequest;
import com.github.btpka3.firstopenapi1st.model.Pet;
import com.github.btpka3.firstopenapi1st.model.PetListResponse;
import com.github.btpka3.firstopenapi1st.model.PetStatus;
import com.github.btpka3.firstopenapi1st.model.UpdatePetRequest;
import com.github.btpka3.firstopenapi1st.service.PetService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController implements PetApi {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Pet getPetById(Long petId) {
        return petService.getPetById(petId);
    }

    @Override
    public PetListResponse listPets(Integer pageNum, Integer pageSize, PetStatus status, Long categoryId) {
        return petService.listPets(pageNum, pageSize, status, categoryId);
    }

    @Override
    public Pet createPet(CreatePetRequest createPetRequest) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Pet updatePet(Long petId, UpdatePetRequest updatePetRequest) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deletePet(Long petId) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
