package com.github.btpka3.firstgrpcproto1st.service;

import com.github.btpka3.firstgrpcproto1st.proto.Category;
import com.github.btpka3.firstgrpcproto1st.proto.Pet;
import com.github.btpka3.firstgrpcproto1st.proto.PetStatus;
import com.github.btpka3.firstgrpcproto1st.proto.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    public Pet getPetById(long petId) {
        return Pet.newBuilder()
                .setId(petId)
                .setName("doggie")
                .setCategory(Category.newBuilder().setId(1).setName("Dogs").build())
                .addTags(Tag.newBuilder().setId(1).setName("friendly").build())
                .addPhotoUrls("https://example.com/doggie.jpg")
                .setStatus(PetStatus.PET_STATUS_AVAILABLE)
                .build();
    }

    public List<Pet> findPetsByStatus(List<String> statusList) {
        return List.of(
                Pet.newBuilder()
                        .setId(1L)
                        .setName("doggie")
                        .setCategory(Category.newBuilder().setId(1).setName("Dogs").build())
                        .setStatus(PetStatus.PET_STATUS_AVAILABLE)
                        .build(),
                Pet.newBuilder()
                        .setId(2L)
                        .setName("kitty")
                        .setCategory(Category.newBuilder().setId(2).setName("Cats").build())
                        .setStatus(PetStatus.PET_STATUS_PENDING)
                        .build()
        );
    }
}
