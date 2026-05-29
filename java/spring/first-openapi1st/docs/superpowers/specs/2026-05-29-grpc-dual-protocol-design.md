# gRPC 双协议扩展设计

## 目标

在现有 OpenAPI spec-first 项目基础上，新增 gRPC (Protobuf) 通信方式。同一套 Petstore API 同时提供 REST (JSON, HTTP/1.1 on :8080) 和 gRPC (Protobuf, HTTP/2 on :9090) 两种接入，共享同一套业务实现。OpenAPI swagger.json 保持唯一契约源。

## 项目结构（5 modules）

```
first-openapi1st/
├── pom.xml                              # parent pom
├── first-openapi1st-api/                # OpenAPI → Java 接口 + Model（已有，不变）
├── first-openapi1st-proto/              # OpenAPI → .proto → gRPC Java Stub
├── first-openapi1st-service/            # 业务实现（Service 层），被 web 和 grpc 共享
├── first-openapi1st-web/                # REST Controller（已有），依赖 api + service
└── first-openapi1st-grpc/               # gRPC Service 实现，依赖 proto + service
```

## 模块依赖关系

```
api ←── service ←── web    (REST, Tomcat :8080)
proto ←─────────←── grpc   (gRPC, Netty :9090)
            service ←── grpc
```

- **api** — 从 OpenAPI spec 生成 REST 接口 + Model。依赖：Spring Web 注解 + Swagger 注解（provided）。不变。
- **proto** — 从 OpenAPI spec 经 gnostic 转换为 .proto，再经 protoc 生成 gRPC Java Stub。依赖：grpc-protobuf、grpc-stub（provided）。
- **service** — 纯 Java 业务逻辑层。依赖 api module 的 Model 类。不依赖 Spring MVC，不依赖 gRPC。由 Spring Boot 管理 bean 生命周期（通过 @Service 注解）。
- **web** — REST Controller implements PetApi 接口，委托 PetService。依赖 api + service。Tomcat :8080。已有，需重构提取 service。
- **grpc** — gRPC Service extends PetServiceGrpc.PetServiceImplBase，委托 PetService，内部做 api Model ↔ Protobuf Message 转换。依赖 proto + service。gRPC Server :9090，通过 grpc-spring-boot-starter 集成。无独立主类，作为依赖被 web module 引入，同一 Spring Boot 进程内双端口共存。

## 代码生成链

```
swagger.json (https://petstore.swagger.io/v2/swagger.json)
    │
    ├─→ openapi-generator-maven-plugin (api module)
    │       generatorName=spring, interfaceOnly=true, useResponseEntity=false
    │       → Java 接口 (PetApi, StoreApi, UserApi) + Model (Pet, Category, Tag, ...)
    │
    └─→ gnostic + protoc-gen-grpc-java (proto module)
            gnostic 将 OpenAPI 2.0 转换为 .proto 文件
            protobuf-maven-plugin 编译 .proto 为 Java gRPC Stub
            → PetServiceGrpc, PetProto 等
```

两条生成链的唯一源头都是同一份 swagger.json。

## 技术选型

| 组件 | 选择 | 版本策略 |
|------|------|----------|
| Spring Boot | 3.5.14 | parent pom |
| Java | 21 | .sdkmanrc |
| OpenAPI → Java | openapi-generator-maven-plugin 7.22.0 | 已有 |
| OpenAPI → .proto | gnostic (Google) | 构建时通过 exec-maven-plugin 调用 |
| .proto → Java gRPC | protobuf-maven-plugin + protoc-gen-grpc-java | 跟随 grpc-java BOM |
| gRPC Spring 集成 | net.devh:grpc-spring-boot-starter | 最新稳定版 |
| gRPC Server 端口 | 9090 | application.yml 配置 |
| REST Server 端口 | 8080 | 默认 |

## Service 层设计

```java
// first-openapi1st-service module
@Service
public class PetService {
    public Pet getPetById(Long petId) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("doggie");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        return pet;
    }

    public List<Pet> findPetsByStatus(List<String> status) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("doggie");
        pet.setStatus(Pet.StatusEnum.AVAILABLE);
        return List.of(pet);
    }
}
```

Service 层使用 api module 的 Model 类（Pet 等），不依赖协议相关类型。

## REST Controller（web module，重构后）

```java
@RestController
public class PetController implements PetApi {
    private final PetService petService;
    // 委托给 petService，不含业务逻辑
}
```

## gRPC Service（grpc module）

```java
@GrpcService
public class PetGrpcService extends PetServiceGrpc.PetServiceImplBase {
    private final PetService petService;

    @Override
    public void getPetById(GetPetByIdRequest request, StreamObserver<PetResponse> observer) {
        Pet pet = petService.getPetById(request.getPetId());
        observer.onNext(toProto(pet));  // Model → Protobuf 转换
        observer.onCompleted();
    }
}
```

Model ↔ Protobuf Message 转换逻辑封装在 grpc module 内部的 converter 类中。

## 演示范围

只实现 getPetById 和 findPetsByStatus 的双协议版本，返回 mock 数据。验证目标：

1. `mvn clean install` 完整构建（OpenAPI → api, OpenAPI → .proto → gRPC stub）
2. 启动后 REST curl 正常
3. 启动后 grpcurl 正常
4. 两个协议返回的数据语义一致

## 验证命令

```bash
# REST
curl -s http://localhost:8080/pet/1

# gRPC
grpcurl -plaintext -d '{"pet_id": 1}' localhost:9090 petstore.PetService/GetPetById
```

## gnostic 生成 .proto 的局限性

gnostic 从 OpenAPI 生成的 .proto 可能不完美（字段类型映射、嵌套结构等），生成后可能需要少量手动调整。proto module 中保留生成的 .proto 文件（提交到 git），作为可审查的中间产物。后续 OpenAPI spec 变更时重新生成并 diff 审查。
