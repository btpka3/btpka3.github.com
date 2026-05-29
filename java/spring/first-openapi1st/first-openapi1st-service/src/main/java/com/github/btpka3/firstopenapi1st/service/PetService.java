package com.github.btpka3.firstopenapi1st.service;

import com.github.btpka3.firstopenapi1st.model.PageMeta;
import com.github.btpka3.firstopenapi1st.model.Pet;
import com.github.btpka3.firstopenapi1st.model.PetListResponse;
import com.github.btpka3.firstopenapi1st.model.PetStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    public Pet getPetById(Long petId) {
        Pet pet = new Pet("doggie", PetStatus.AVAILABLE);
        pet.setId(petId);
        return pet;
    }

    public PetListResponse listPets(Integer pageNum, Integer pageSize, PetStatus status, Long categoryId) {
        Pet pet = new Pet("doggie", PetStatus.AVAILABLE);
        pet.setId(1L);

        PetListResponse response = new PetListResponse();
        response.setData(List.of(pet));
        PageMeta pageMeta = new PageMeta();
        pageMeta.setPageNum(pageNum);
        pageMeta.setPageSize(pageSize);
        pageMeta.setTotalCount(1L);
        pageMeta.setTotalPages(1);
        response.setPage(pageMeta);
        return response;
    }
}
