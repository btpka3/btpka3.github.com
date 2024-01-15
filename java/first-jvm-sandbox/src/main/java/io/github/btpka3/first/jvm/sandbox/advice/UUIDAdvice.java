package io.github.btpka3.first.jvm.sandbox.advice;

import com.alibaba.jvm.sandbox.api.ProcessController;
import com.alibaba.jvm.sandbox.api.listener.ext.Advice;
import com.alibaba.jvm.sandbox.api.listener.ext.AdviceListener;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author dangqian.zll
 * @date 2024/1/15
 */
public class UUIDAdvice extends AdviceListener {

    private static ObjectPool<SecureRandom> pool = null;

    static {
        GenericObjectPoolConfig<SecureRandom> config = new GenericObjectPoolConfig<>();
        config.setMinIdle(100);
        config.setMaxIdle(200);
        config.setMaxTotal(400);
        pool = new GenericObjectPool<>(
                new SecureRandomPooledObjectFactory(),
                config
        );
    }

    @Override
    protected void before(Advice advice) throws Throwable {
        UUID uuid = randomUUID();
        ProcessController.returnImmediately(uuid);
    }

    protected static UUID withSecureRandom(Function<SecureRandom, UUID> f) {
        SecureRandom random = null;
        try {
            random = pool.borrowObject();
            return f.apply(random);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != random) {
                    pool.returnObject(random);
                }
            } catch (Exception e) {
                // ignored
            }
        }
    }

    /**
     * @param random
     * @return
     * @see java.util.UUID#randomUUID()
     */
    protected static UUID randomUUID(SecureRandom random) {

        // see : java.util.UUID#randomUUID()
        byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f;  /* clear version        */
        randomBytes[6] |= 0x40;  /* set to version 4     */
        randomBytes[8] &= 0x3f;  /* clear variant        */
        randomBytes[8] |= 0x80;  /* set to IETF variant  */

        // see : java.util.UUID#UUID(byte[])
        long msb = 0;
        long lsb = 0;
        //assert randomBytes.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (randomBytes[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (randomBytes[i] & 0xff);
        long mostSigBits = msb;
        long leastSigBits = lsb;

        return new UUID(mostSigBits, leastSigBits);
    }

    public static UUID randomUUID() {
        return withSecureRandom(UUIDAdvice::randomUUID);
    }

    public static class SecureRandomPooledObjectFactory extends BasePooledObjectFactory<SecureRandom> {

        @Override
        public SecureRandom create() throws Exception {
            String argo = "DRBG";
            return SecureRandom.getInstance(argo);
        }

        @Override
        public PooledObject<SecureRandom> wrap(SecureRandom obj) {
            return new DefaultPooledObject<>(obj);
        }
    }
}
