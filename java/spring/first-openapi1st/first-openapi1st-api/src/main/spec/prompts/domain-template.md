# AI 生成域 Spec 的 Prompt 模板

## 使用方式

将以下模板填入 AI 对话，附上交互图和需求描述，AI 会生成对应域的 spec 文件。

---

## Prompt 模板

```
你是一个 OpenAPI 3.1 规范专家。请根据以下需求和交互图，生成/更新指定域的 OpenAPI spec。

## 约束
- 输出格式：YAML
- 遵循 OpenAPI 3.1.0 规范
- 只输出该域相关的 paths 和 models，不要输出其他域的内容
- 共享类型（分页、错误、通用ID）使用 $ref 引用 ../../components/schemas/ 下的定义
- operationId 使用 camelCase，全局唯一
- 每个接口必须有 summary
- 错误响应统一引用 ErrorResponse
- 新增的域私有 model 放在同目录的 models.yaml 中

## 当前域结构
- 域名：{domain_name}
- 已有接口：{existing_operations}
- 已有 model：{existing_models}

## 可用的共享组件
- components/schemas/pagination.yaml: PageMeta, PageRequest
- components/schemas/error.yaml: ErrorResponse, FieldError  
- components/schemas/common.yaml: ResourceId, Timestamp, SortOrder
- components/parameters/common-params.yaml: PageNum, PageSize, TraceId

## 需求描述
{requirement_text}

## 交互图
{interaction_diagram_description}

## 输出
请分别输出两个文件内容：
1. domains/{domain_name}/{domain_name}.yaml — paths 定义
2. domains/{domain_name}/models.yaml — 该域私有 model 定义
```

---

## 示例输入

需求：给 Pet 域添加"上传宠物照片"接口
- POST /pets/{petId}/photos
- 支持多图上传（multipart/form-data）
- 返回上传后的图片 URL 列表

## 注意事项

1. 每次生成后，需要手动更新 openapi-full.yaml 中的 paths $ref（或由 CI 自动扫描）
2. 如果需求涉及新增共享 model，需单独说明并更新 components/
3. 生成结果需经过 `scripts/bundle.sh` 验证通过才能合入
