package com.github.btpka3.first.spring.boot4.ai;

import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;
import org.springframework.ai.transformers.TransformersEmbeddingModel;

import java.util.List;
import java.util.Map;

/**
 * jvm 进程中运行大模型。
 * <p>
 * 需要先 准备 ONNX 模型文件。
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class ONNXTest {

    public void x() throws Exception {
        TransformersEmbeddingModel embeddingModel = new TransformersEmbeddingModel();

        // (optional) defaults to classpath:/onnx/all-MiniLM-L6-v2/tokenizer.json
        embeddingModel.setTokenizerResource("classpath:/onnx/all-MiniLM-L6-v2/tokenizer.json");

        // (optional) defaults to classpath:/onnx/all-MiniLM-L6-v2/model.onnx
        embeddingModel.setModelResource("classpath:/onnx/all-MiniLM-L6-v2/model.onnx");

        // (optional) defaults to ${java.io.tmpdir}/spring-ai-onnx-model
        // Only the http/https resources are cached by default.
        embeddingModel.setResourceCacheDirectory("/tmp/onnx-zoo");

        // (optional) Set the tokenizer padding if you see an errors like:
        // "ai.onnxruntime.OrtException: Supplied array is ragged, ..."
        embeddingModel.setTokenizerOptions(Map.of("padding", "true"));

        embeddingModel.afterPropertiesSet();

        {
            String text = "Spring AI makes embedding simple";
            float[] vector = embeddingModel.embed(text);
        }

        // 示例2： 批量文本
        {
            List<String> texts = List.of(
                    "Hello world",
                    "Spring AI with ONNX",
                    "Embedding models in JVM"
            );
            List<float[]> embeddings = embeddingModel.embed(texts);
            EmbeddingResponse response = embeddingModel.embedForResponse(texts);
            System.out.println("\n完整响应 - Embedding 数量: " + response.getResults().size());
            // 获取元数据（如 token 数、模型信息等）
            EmbeddingResponseMetadata metadata = response.getMetadata();
            System.out.println("响应元数据: " + metadata);

        }
    }

}
