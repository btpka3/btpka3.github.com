package com.github.btpka3.firstopenapi1st.controller;

import com.github.btpka3.firstopenapi1st.api.PetApi;
import com.github.btpka3.firstopenapi1st.model.ModelApiResponse;
import com.github.btpka3.firstopenapi1st.model.Pet;
import com.github.btpka3.firstopenapi1st.service.PetService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public List<Pet> findPetsByStatus(List<String> status) {
        return petService.findPetsByStatus(status);
    }

    @Override
    public void addPet(Pet body) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deletePet(Long petId, String apiKey) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<Pet> findPetsByTags(List<String> tags) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void updatePet(Pet body) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void updatePetWithForm(Long petId, String name, String status) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ModelApiResponse uploadFile(Long petId, String additionalMetadata, MultipartFile file) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
