package com.github.btpka3.firstopenapi1st.grpc;

import com.github.btpka3.firstopenapi1st.model.Pet;
import com.github.btpka3.firstopenapi1st.proto.PetStatus;

public final class PetConverter {

    private PetConverter() {
    }

    public static com.github.btpka3.firstopenapi1st.proto.Pet toProto(Pet pet) {
        com.github.btpka3.firstopenapi1st.proto.Pet.Builder builder =
                com.github.btpka3.firstopenapi1st.proto.Pet.newBuilder();

        if (pet.getId() != null) {
            builder.setId(pet.getId());
        }
        if (pet.getName() != null) {
            builder.setName(pet.getName());
        }
        if (pet.getStatus() != null) {
            builder.setStatus(toProtoStatus(pet.getStatus()));
        }
        if (pet.getPhotoUrls() != null) {
            builder.addAllPhotoUrls(pet.getPhotoUrls());
        }
        return builder.build();
    }

    private static PetStatus toProtoStatus(Pet.StatusEnum status) {
        return switch (status) {
            case AVAILABLE -> PetStatus.PET_STATUS_AVAILABLE;
            case PENDING -> PetStatus.PET_STATUS_PENDING;
            case SOLD -> PetStatus.PET_STATUS_SOLD;
        };
    }
}
