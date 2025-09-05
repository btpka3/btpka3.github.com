package me.test.first.spring.boot.test.grpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import io.grpc.*;
import io.grpc.protobuf.services.ProtoReflectionServiceV1;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import me.test.first.spring.boot.test.grpc.idl.Req;
import me.test.first.spring.boot.test.grpc.idl.RequestServiceGrpc;
import me.test.first.spring.boot.test.grpc.idl.Resp;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2025/7/24
 */
public class GrpcTest {

    int port = 9999;

    /*
# 请检查 gradle 插件 com.google.protobuf 的相关配置，对应的 gradle task = "generateTestProto"
# 会将 src/test/proto/*.proto 编译为 java 文件，生成目录:
# 1. build/generated/sources/proto/test/java/
# 2. build/generated/sources/proto/test/java/

# 需要注意 protoc 的版本，
protoc \
--java_out=first-spring-boot-test/src/test/java \
first-spring-boot-test/src/test/resources/RequestService.proto
     */

    @SneakyThrows
    @Test
    public void startServer() {
        Server server = ServerBuilder.forPort(port)
                .addService(new RequestServiceImpl())
                .addService(ProtoReflectionServiceV1.newInstance())
                .build()
                .start();
        System.out.println("Server started");
        server.awaitTermination();
    }

    public void x() {
        Path socketPath = Path
                .of(System.getProperty("user.home"))
                .resolve("baeldung.socket");
        //UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(socketPath);
    }


    @SneakyThrows
    @Test
    public void startClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .build();

        RequestServiceGrpc.RequestServiceBlockingStub stub = RequestServiceGrpc.newBlockingStub(channel);


        {
            Req req = Req.newBuilder().setName("zhang3")
                    .build();
            Resp resp = stub.request(req);
            System.out.println("======= gRpc client request received : \n" + resp);
        }
        {
            Resp resp = stub.hi(Empty.newBuilder().build());
            System.out.println("======= gRpc client hi received : \n" + resp);
        }

    }

    public static class RequestServiceImpl extends RequestServiceGrpc.RequestServiceImplBase {

        @Override
        public void request(Req request, StreamObserver<Resp> responseObserver) {
            System.out.println("--------- msg received --------- : " + request.getName());
            Map<String, String> demoMap = new HashMap<>(8);
            demoMap.put("k1", "v1");
            demoMap.put("k2", "v2");

            Resp resp = Resp.newBuilder()
                    .setMessage("hello " + request.getName())
                    .setMsgBody(ByteString.copyFromUtf8("你好，世界~"))
                    //.putAllHeaders(demoMap)
                    .addAllCookies(Arrays.asList("cookie1", "cookie2"))
                    .build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }

        @Override
        public void hi(Empty request, StreamObserver<Resp> responseObserver) {
            Req req = Req.newBuilder().setName("li4")
                    .build();
            request(req, responseObserver);
        }
    }

}
