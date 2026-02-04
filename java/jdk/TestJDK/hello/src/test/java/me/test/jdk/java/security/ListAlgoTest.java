package me.test.jdk.java.security;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Set;
import java.util.TreeSet;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

/* shell:
[source,shell]
----
# 先确认JDK默认配置允许通过JVM属性完全覆盖。true-允许覆盖
grep "security.overridePropertiesFile" $JAVA_HOME/conf/security/java.security

# 将JDK默认的 java.security 导出
grep -v -E '^#' $JAVA_HOME/conf/security/java.security |grep -v '^$' > /tmp/java.security

# 确认已经配置了多少个 security.provider
grep '^security.provider.' /tmp/java.security

# 按序号增加一个新自定义 provider（下面的case假设已经配置了13个）
# TIP : value的格式是 "${providerName} ${providerArg}"
# @see java.security.Provider#configure(String)
echo security.provider.14=btpka3 >> /tmp/java.security
----
*/

/**
 * 默认加载 $JAVA_HOME/conf/security/java.security 配置的 provider 列表。
 * 且其中配置了 security.overridePropertiesFile==true时，
 * 可通过 JVM属性 -Djava.security.properties=/tmp/java.security
 * 指定额外的 provider
 *
 * @author dangqian.zll
 * @date 2024/8/19
 * @see Security#initialize()
 * @see MyProvider
 * @see Security#addProvider(Provider)
 * @see BouncyCastleProvider
 * @see sun.security.jca.Providers#providerList
 * @see sun.security.jca.ProviderList#fromSecurityProperties()
 */
public class ListAlgoTest {

    @Test
    public void listAlgo01() {
        Set<String> algs = new TreeSet<>();
        for (Provider provider : Security.getProviders()) {
            provider.getServices().stream()
                    .filter(s -> "Cipher".equals(s.getType()))
                    .map(Service::getAlgorithm)
                    .forEach(algs::add);
        }
        algs.forEach(System.out::println);
    }

    @Test
    public void listProviders() {
        Set<String> s = new TreeSet<>();
        for (Provider provider : Security.getProviders()) {
            s.add(provider.getName());
        }
        s.forEach(System.out::println);

        Provider myProvider = Security.getProvider(MyProvider.NAME);
        // 如果有 -Djava.security.properties=/tmp/java.security ，则结果非null；如果未设置则为null
        System.out.println("=========== myProvider is " + myProvider);
    }
}
