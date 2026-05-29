package com.github.btpka3.firstgrpcproto1st.grpc;

import com.github.btpka3.firstgrpcproto1st.proto.FindPetsByStatusRequest;
import com.github.btpka3.firstgrpcproto1st.proto.FindPetsByStatusResponse;
import com.github.btpka3.firstgrpcproto1st.proto.GetPetByIdRequest;
import com.github.btpka3.firstgrpcproto1st.proto.Pet;
import com.github.btpka3.firstgrpcproto1st.proto.PetServiceGrpc;
import com.github.btpka3.firstgrpcproto1st.service.PetService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class PetGrpcService extends PetServiceGrpc.PetServiceImplBase {

    private final PetService petService;

    public PetGrpcService(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void getPetById(GetPetByIdRequest request, StreamObserver<Pet> responseObserver) {
        Pet pet = petService.getPetById(request.getPetId());
        responseObserver.onNext(pet);
        responseObserver.onCompleted();
    }

    @Override
    public void findPetsByStatus(FindPetsByStatusRequest request, StreamObserver<FindPetsByStatusResponse> responseObserver) {
        List<Pet> pets = petService.findPetsByStatus(request.getStatusList());
        FindPetsByStatusResponse response = FindPetsByStatusResponse.newBuilder()
                .addAllPets(pets)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
