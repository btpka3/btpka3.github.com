package io.github.btpka3.first.spring.cloud.kubernetes.client;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import lombok.SneakyThrows;

/**
 * @author dangqian.zll
 * @date 2023/11/20
 */
public class WatchTest {

    @SneakyThrows
    public void test() {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        Watch<V1Namespace> watch = Watch.createWatch(
                client,
                api.listNamespaceCall(null, null,
                        null, null, null, 5, null, null,
                        null, null, null),
                new TypeToken<Watch.Response<V1Namespace>>() {
                }.getType()
        );

        for (Watch.Response<V1Namespace> item : watch) {
            System.out.printf("%s : %s%n", item.type, item.object.getMetadata().getName());
        }
    }
}
