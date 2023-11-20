package io.github.btpka3.first.spring.cloud.kubernetes.client;

import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.PatchUtils;
import io.kubernetes.client.util.wait.Wait;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author dangqian.zll
 * @date 2023/11/20
 */
public class DeployRolloutRestartExample {

    @Test
    public static void test() throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1Api appsV1Api = new AppsV1Api(client);

        String deploymentName = "example-nginx";
        String imageName = "nginx:1.21.6";
        String namespace = "default";

        // Create an example deployment
        V1DeploymentSpec deploymentSpec = new V1DeploymentSpec()
                .replicas(1)
                .selector(new V1LabelSelector().putMatchLabelsItem("name", deploymentName))
                .template(new V1PodTemplateSpec()
                        .metadata(new V1ObjectMeta().putLabelsItem("name", deploymentName))
                        .spec(new V1PodSpec().containers(Collections.singletonList(
                                new V1Container()
                                        .name(deploymentName)
                                        .image(imageName))
                        ))
                );
        V1DeploymentBuilder deploymentBuilder =
                new V1DeploymentBuilder()
                        .withApiVersion("apps/v1")
                        .withKind("Deployment")
                        .withMetadata(new V1ObjectMeta().name(deploymentName).namespace(namespace))
                        .withSpec(deploymentSpec);

        appsV1Api.createNamespacedDeployment(
                namespace, deploymentBuilder.build(), null, null, null, null);

        // Wait until example deployment is ready
        Wait.poll(
                Duration.ofSeconds(3),
                Duration.ofSeconds(60),
                () -> {
                    try {
                        System.out.println("Waiting until example deployment is ready...");
                        int readyReplicas = appsV1Api
                                .readNamespacedDeployment(deploymentName, namespace, null)
                                .getStatus()
                                .getReadyReplicas();
                        return readyReplicas > 0;
                    } catch (ApiException e) {
                        e.printStackTrace();
                        return false;
                    }
                });
        System.out.println("Created example deployment!");

        // Trigger a rollout restart of the example deployment
        V1Deployment runningDeployment =
                appsV1Api.readNamespacedDeployment(deploymentName, namespace, null);

        // Explicitly set "restartedAt" annotation with current date/time to trigger rollout when patch
        // is applied
        runningDeployment
                .getSpec()
                .getTemplate()
                .getMetadata()
                .putAnnotationsItem("kubectl.kubernetes.io/restartedAt", LocalDateTime.now().toString());
        try {
            String deploymentJson = client.getJSON().serialize(runningDeployment);

            PatchUtils.patch(
                    V1Deployment.class,
                    () ->
                            appsV1Api.patchNamespacedDeploymentCall(
                                    deploymentName,
                                    namespace,
                                    new V1Patch(deploymentJson),
                                    null,
                                    null,
                                    "kubectl-rollout",
                                    null,
                                    null,
                                    null),
                    V1Patch.PATCH_FORMAT_STRATEGIC_MERGE_PATCH,
                    client);

            // Wait until deployment has stabilized after rollout restart
            Wait.poll(
                    Duration.ofSeconds(3),
                    Duration.ofSeconds(60),
                    () -> {
                        try {
                            System.out.println("Waiting until example deployment restarted successfully...");
                            return appsV1Api
                                    .readNamespacedDeployment(deploymentName, namespace, null)
                                    .getStatus()
                                    .getReadyReplicas()
                                    > 0;
                        } catch (ApiException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
            System.out.println("Example deployment restarted successfully!");
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
