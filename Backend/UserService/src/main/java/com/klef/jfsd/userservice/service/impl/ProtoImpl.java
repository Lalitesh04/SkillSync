package com.klef.jfsd.userservice.service.impl;

import com.klef.jfsd.proto.user.UserInternalServiceGrpc;
import com.klef.jfsd.proto.user.UserServiceProto;
import com.klef.jfsd.proto.user.VerifyUserByEmailRequest;
import com.klef.jfsd.proto.user.VerifyUserByEmailResponse;
import com.klef.jfsd.userservice.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class ProtoImpl  extends UserInternalServiceGrpc.UserInternalServiceImplBase {

    private final  UserRepository userRepository;

    @Override
    public void verifyUserByEmail(VerifyUserByEmailRequest request, StreamObserver<VerifyUserByEmailResponse> responseObserver) {
       boolean isExists = userRepository.existsByEmail(request.getEmail());

       VerifyUserByEmailResponse response =
               VerifyUserByEmailResponse.newBuilder()
                       .setExists(isExists)
                       .build();
       responseObserver.onNext(response);
       responseObserver.onCompleted();
    }
}
