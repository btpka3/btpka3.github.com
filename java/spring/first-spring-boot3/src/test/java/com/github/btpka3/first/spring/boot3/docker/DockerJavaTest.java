package com.github.btpka3.first.spring.boot3.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/29
 */
public class DockerJavaTest {

    DockerClientConfig getDockerClientConfig() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://docker.somewhere.tld:2376")
                .withDockerTlsVerify(true)
                .withDockerCertPath("/home/user/.docker")
                .withRegistryUsername("registryUser")
                .withRegistryPassword("registryPass")
                .withRegistryEmail("registryMail")
                .withRegistryUrl("registryUrl")
                .build();
    }

    DockerHttpClient getDockerHttpClient(DockerClientConfig config) {

        return new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(60))
                .build();
    }

    public DockerClient getDockerClient() {
        DockerClientConfig config = getDockerClientConfig();
        DockerHttpClient httpClient = getDockerHttpClient(config);
        return DockerClientImpl.getInstance(config, httpClient);
    }

    @SneakyThrows
    public void request1() {
        DockerClientConfig config = getDockerClientConfig();
        DockerHttpClient httpClient = getDockerHttpClient(config);
        DockerHttpClient.Request request = DockerHttpClient.Request.builder()
                .method(DockerHttpClient.Request.Method.GET)
                .path("/_ping")
                .build();

        try (DockerHttpClient.Response response = httpClient.execute(request)) {

            Assertions.assertEquals(200, response.getStatusCode());
            Assertions.assertEquals("OK", IOUtils.toString(response.getBody(), StandardCharsets.UTF_8));
        }
    }


    void testDockerClient() {

        DockerClientConfig custom = getDockerClientConfig();

        DockerClient dockerClient = getDockerClient();
        String containerName = "xxxPrefix-" + UUID.randomUUID();

        // 创建命令
        CreateContainerCmd cmd = dockerClient.createContainerCmd("xxxImage")
                .withName(containerName)
                .withTty(false)
                .withStdinOpen(true)
                .withAttachStdin(true)
                .withAttachStdout(true)
                .withAttachStderr(true)
                // 容器内保持长驻，等待命令
                .withCmd("sh", "-c", "while true; do sleep 1; done")
                // 环境变量
                .withEnv("ENV_KEY1", "ENV_VALUE1");

        // 只读根文件系统 + tmpfs 可写目录
        boolean isReadOnlyRootfs = true;
        if (isReadOnlyRootfs) {
            cmd.withReadonlyRootfs(true);
            cmd.withHostConfig(HostConfig.newHostConfig()
                    .withTmpFs(Map.of("/tmp", "size=" + 128 + "m")));
        }

        // 网络隔离
        HostConfig hostConfig = cmd.getHostConfig() != null ? cmd.getHostConfig() : HostConfig.newHostConfig();
        boolean disableNetwork = true;
        if (disableNetwork) {
            hostConfig.withNetworkMode("none");
        }

        // 资源限制
        hostConfig
                .withNanoCPUs((long) (1 * 1e9))
                .withMemory(1024 * 1024 * 1024L)
                .withPidsLimit(1024L);

        // gVisor 运行时（如果启用）
        boolean isUseGvisor = true;
        if (isUseGvisor) {
            hostConfig.withRuntime("runsc");
        }

        cmd.withHostConfig(hostConfig);

        // 执行创建 + 启动
        CreateContainerResponse response = cmd.exec();
        dockerClient.startContainerCmd(response.getId()).exec();

        String id = response.getId();
    }
}
