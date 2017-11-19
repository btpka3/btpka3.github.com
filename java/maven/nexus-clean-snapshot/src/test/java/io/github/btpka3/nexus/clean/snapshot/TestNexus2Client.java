package io.github.btpka3.nexus.clean.snapshot;

import org.junit.*;
import org.sonatype.nexus.client.core.*;
import org.sonatype.nexus.client.core.condition.*;
import org.sonatype.nexus.client.core.spi.*;
import org.sonatype.nexus.client.core.subsystem.repository.*;
import org.sonatype.nexus.client.internal.rest.jersey.subsystem.repository.*;
import org.sonatype.nexus.client.rest.*;
import org.sonatype.nexus.client.rest.jersey.*;
import org.sonatype.nexus.client.rest.jersey.subsystem.*;

import java.net.*;
import java.util.*;

public class TestNexus2Client {

    public static class A implements SubsystemFactory<JerseyRepositoriesFactory, JerseyNexusClient> {

        JerseyRepositoriesFactory fac;

        @Override
        public Condition availableWhen() {
            return NexusStatusConditions.any20AndLater();
        }

        @Override
        public Class<JerseyRepositoriesFactory> getType() {
            return JerseyRepositoriesFactory.class;
        }

        @Override
        public JerseyRepositoriesFactory create(JerseyNexusClient nexusClient) {
            return fac;
        }
    }

    public static class B implements SubsystemFactory<JerseyContentSubsystemFactory, JerseyNexusClient> {

        JerseyContentSubsystemFactory fac;

        @Override
        public Condition availableWhen() {
            return NexusStatusConditions.any20AndLater();
        }

        @Override
        public Class<JerseyContentSubsystemFactory> getType() {
            return JerseyContentSubsystemFactory.class;
        }

        @Override
        public JerseyContentSubsystemFactory create(JerseyNexusClient nexusClient) {
            return fac;
        }
    }

    @Test
    public void test01() throws MalformedURLException {
        BaseUrl baseUrl = BaseUrl.baseUrlFrom("http://mvn.kingsilk.xyz");
        AuthenticationInfo authInfo = new UsernamePasswordAuthenticationInfo("admin", "admin123");

        JerseyRepositoriesFactory fac1 = new JerseyRepositoriesFactory(
                new HashSet<>(Arrays.asList(
                        new JerseyVirtualRepositoryFactory(),
                        new JerseyHostedRepositoryFactory(),
                        new JerseyGroupRepositoryFactory(),
                        new JerseyProxyRepositoryFactory()
                ))
        );
        JerseyContentSubsystemFactory fac2 = new JerseyContentSubsystemFactory();

        A a = new A();
        a.fac = fac1;
        B b = new B();
        b.fac = fac2;


        JerseyNexusClientFactory factory = new JerseyNexusClientFactory(
                a, b
        );
        JerseyNexusClient client = (JerseyNexusClient) factory.createFor(baseUrl, authInfo);

        JerseyRepositoriesFactory repoFac = client.getSubsystem(JerseyRepositoriesFactory.class);
        Repositories repositories = repoFac.create(client);
        repositories.get().stream().forEach(r -> {

            System.out.println("------------------------");
            System.out.println("\tid            = " + r.id());
            System.out.println("\tname          = " + r.name());
            System.out.println("\tcontentUri    = " + r.contentUri());

        });
    }
}
