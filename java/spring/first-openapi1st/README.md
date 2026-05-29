
演示使用 spec first 基于已有的 openAPI json/yaml 生成 使用 spring mvc 的 java 后端的 http api

要求：创建maven项目，使用 https://petstore.swagger.io/v2/swagger.json 这个openAPI json文件。



## 测试

```shell
# 编译、启动
mvn clean install -DskipTests
mvn -pl first-openapi1st-web spring-boot:run


# 测试

# 1. 根据 ID 查询宠物
curl -s http://localhost:8080/pet/1 | jq

# 2. 根据状态查询宠物
curl -s "http://localhost:8080/pet/findByStatus?status=available" | jq

# 3. 查询不存在的宠物（验证默认返回）
curl -s http://localhost:8080/pet/999 | jq

#这两个是 PetController 中手写实现的方法。其余 PetApi 接口方法未实现，会返回默认的 501 Not Implemented，例如：

# 4. 添加宠物（未实现，预期 501）
curl -s -X POST http://localhost:8080/pet \
  -H "Content-Type: application/json" \
  -d '{"id":10,"name":"fido","status":"available","photoUrls":["url1"]}' \
  -w "\nHTTP_STATUS: %{http_code}\n"

# 5. 删除宠物（未实现，预期 500）
curl -s -X DELETE http://localhost:8080/pet/1 \
  -w "\nHTTP_STATUS: %{http_code}\n"
  
# Protobuf 验证  
grpcurl -plaintext -d '{"pet_id":42}' localhost:9090 petstore.PetService/GetPetById 
```