package me.test.first.spring.boot.data.mongo.audit

import org.springframework.data.domain.AuditorAware

/**
 * 模拟，随机生成
 */
class MyAuditorAware implements AuditorAware<String> {

    private static final auditors = ["xxx", "yyy", "zzz"]
    private static final Random random = new Random(System.currentTimeMillis());

    @Override
    String getCurrentAuditor() {
        return auditors[random.nextInt() % auditors.size()]
    }
}
