package com.github.btpka3.first.spring.boot4.ai.sandbox;

import com.alibaba.opensandbox.codeinterpreter.CodeInterpreter;
import com.alibaba.opensandbox.codeinterpreter.domain.models.execd.executions.SupportedLanguage;
import com.alibaba.opensandbox.sandbox.Sandbox;
import com.alibaba.opensandbox.sandbox.config.ConnectionConfig;
import com.alibaba.opensandbox.sandbox.domain.models.execd.executions.Execution;
import com.alibaba.opensandbox.sandbox.domain.models.execd.executions.OutputMessage;
import com.alibaba.opensandbox.sandbox.domain.models.execd.filesystem.WriteEntry;
import com.alibaba.opensandbox.sandbox.domain.models.sandboxes.SandboxInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Timeout;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * OpenSandbox 客户端（com.alibaba.opensandbox:sandbox / code-interpreter SDK）使用示例。
 *
 * <p>运行前提：本地已启动 OpenSandbox server（控制面），例如：
 * <pre>
 * uvx opensandbox-server init-config ~/.sandbox.toml --example docker
 * uvx opensandbox-server
 * </pre>
 *
 * <p>连接参数默认值（{@link ConnectionConfig}）：
 * <ul>
 *   <li>domain：优先取环境变量 {@code OPEN_SANDBOX_DOMAIN}，缺省 {@code localhost:8080}</li>
 *   <li>apiKey：优先取环境变量 {@code OPEN_SANDBOX_API_KEY}；server 端设置了
 *       {@code server.api_key} 时必须提供</li>
 * </ul>
 *
 * <p>若 server 未启动，本类所有用例自动跳过（skip），不会导致构建失败。
 *
 * @author dangqian.zll
 * @date 2026/8/24
 * @see <a href="https://open-sandbox.ai/getting-started/">OpenSandbox Quick Start</a>
 * @see <a href="https://open-sandbox.ai/sdks/kotlin">Kotlin/Java SDK 文档</a>
 */
@Timeout(value = 10, unit = TimeUnit.MINUTES)
public class OpenSandboxClientTest {

    private static ConnectionConfig config;

    /**
     * 探测 server 的 /health 端点，未启动则跳过全部用例。
     */
    @BeforeAll
    static void checkServerAvailable() {
        config = ConnectionConfig.builder()
                // 也可显式指定：.domain("localhost:8080").protocol("http").apiKey("your-api-key")
                // create 会阻塞等待沙箱就绪，首次拉取镜像时建议放宽请求超时
                .requestTimeout(Duration.ofMinutes(2))
                .build();

        String healthUrl = config.getBaseUrl().replaceFirst("/v1$", "") + "/health";
        boolean reachable = false;
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder(URI.create(healthUrl))
                            .timeout(Duration.ofSeconds(3))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            reachable = response.statusCode() == 200;
        } catch (Exception e) {
            // server 未启动或不可达
        }
        Assumptions.assumeTrue(reachable,
                "OpenSandbox server 未启动（" + healthUrl + "），跳过示例用例");
    }

    /**
     * 创建沙箱并执行 shell 命令。
     */
    @Test
    @DisplayName("创建沙箱并执行 shell 命令")
    void runShellCommand() {
        // try-with-resources 自动调用 close() 释放 SDK 本地资源（HTTP 客户端等）；
        // 远端沙箱实例需要显式 kill() 才会立即销毁
        try (Sandbox sandbox = Sandbox.builder()
                .connectionConfig(config)
                .image("ubuntu")
                .timeout(Duration.ofMinutes(10))
                .readyTimeout(Duration.ofMinutes(2))
                .build()) {

            SandboxInfo info = sandbox.getInfo();
            assertNotNull(info.getId());
            assertEquals("Running", info.getStatus().getState());

            Execution execution = sandbox.commands().run("echo 'Hello OpenSandbox!' && uname -s");
            assertEquals(Integer.valueOf(0), execution.getExitCode());

            String stdout = execution.getLogs().getStdout().stream()
                    .map(OutputMessage::getText)
                    .collect(Collectors.joining());
            assertTrue(stdout.contains("Hello OpenSandbox!"), "stdout: " + stdout);

            sandbox.kill();
        }
    }

    /**
     * 沙箱内文件读写：写入文本文件后读回校验。
     */
    @Test
    @DisplayName("沙箱内文件读写")
    void readWriteFiles() {
        try (Sandbox sandbox = Sandbox.builder()
                .connectionConfig(config)
                .image("ubuntu")
                .timeout(Duration.ofMinutes(10))
                .readyTimeout(Duration.ofMinutes(2))
                .build()) {

            String path = "/tmp/hello.txt";
            String content = "Hello OpenSandbox from first-spring-boot4";

            sandbox.files().writeFile(WriteEntry.builder()
                    .path(path)
                    .data(content)
                    .mode(644)
                    .build());

            assertEquals(content, sandbox.files().readFile(path));

            sandbox.kill();
        }
    }

    /**
     * 通过 sandboxId 重新连接已存在的沙箱（适用于跨进程/跨请求复用）。
     */
    @Test
    @DisplayName("按 sandboxId 重连已存在的沙箱")
    void reconnectExistingSandbox() {
        try (Sandbox sandbox = Sandbox.builder()
                .connectionConfig(config)
                .image("ubuntu")
                .timeout(Duration.ofMinutes(10))
                .readyTimeout(Duration.ofMinutes(2))
                .build()) {

            String sandboxId = sandbox.getInfo().getId();

            try (Sandbox reconnected = Sandbox.connector()
                    .sandboxId(sandboxId)
                    .connectionConfig(config)
                    .connect()) {

                Execution execution = reconnected.commands().run("echo reconnected");
                String stdout = execution.getLogs().getStdout().stream()
                        .map(OutputMessage::getText)
                        .collect(Collectors.joining());
                assertTrue(stdout.contains("reconnected"), "stdout: " + stdout);
            }

            sandbox.kill();
        }
    }

    /**
     * Code Interpreter：在专用镜像中执行 Python 代码。
     * 需要 code-interpreter 专用镜像（内置 Python/Java/Go/Node.js 等运行时）。
     */
    @Test
    @DisplayName("通过 Code Interpreter 执行 Python 代码")
    void runCodeWithCodeInterpreter() {
        try (Sandbox sandbox = Sandbox.builder()
                .connectionConfig(config)
                .image("opensandbox/code-interpreter:v1.1.0")
                .entrypoint("/opt/code-interpreter/code-interpreter.sh")
                .env("PYTHON_VERSION", "3.11")
                .timeout(Duration.ofMinutes(10))
                .readyTimeout(Duration.ofMinutes(2))
                .build()) {

            CodeInterpreter interpreter = CodeInterpreter.builder()
                    .fromSandbox(sandbox)
                    .build();

            Execution execution = interpreter.codes()
                    .run("import sys; print(sys.version_info[:2]); print(2 + 2)",
                            SupportedLanguage.PYTHON);

            String stdout = execution.getLogs().getStdout().stream()
                    .map(OutputMessage::getText)
                    .collect(Collectors.joining());
            assertTrue(stdout.contains("(3, 11)"), "stdout: " + stdout);
            assertTrue(stdout.contains("4"), "stdout: " + stdout);

            sandbox.kill();
        }
    }
}
