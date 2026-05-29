package com.github.btpka3.firstopenapi1st.grpc;

import com.github.btpka3.firstopenapi1st.model.Pet;
import com.github.btpka3.firstopenapi1st.service.PetService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class PetGrpcService extends com.github.btpka3.firstopenapi1st.proto.PetServiceGrpc.PetServiceImplBase {

    private final PetService petService;

    public PetGrpcService(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void getPetById(
            com.github.btpka3.firstopenapi1st.proto.GetPetByIdRequest request,
            StreamObserver<com.github.btpka3.firstopenapi1st.proto.Pet> responseObserver) {
        Pet pet = petService.getPetById(request.getPetId());
        responseObserver.onNext(PetConverter.toProto(pet));
        responseObserver.onCompleted();
    }

    @Override
    public void findPetsByStatus(
            com.github.btpka3.firstopenapi1st.proto.FindPetsByStatusRequest request,
            StreamObserver<com.github.btpka3.firstopenapi1st.proto.FindPetsByStatusResponse> responseObserver) {
        List<Pet> pets = petService.findPetsByStatus(request.getStatusList());
        com.github.btpka3.firstopenapi1st.proto.FindPetsByStatusResponse.Builder builder =
                com.github.btpka3.firstopenapi1st.proto.FindPetsByStatusResponse.newBuilder();
        for (Pet pet : pets) {
            builder.addPets(PetConverter.toProto(pet));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
