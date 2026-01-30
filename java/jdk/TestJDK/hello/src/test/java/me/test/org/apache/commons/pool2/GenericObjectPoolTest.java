package me.test.org.apache.commons.pool2;

import lombok.SneakyThrows;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.commons.pool2.impl.SoftReferenceObjectPool;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/30
 */
public class GenericObjectPoolTest {

    @SneakyThrows
    public void test1() {

        PooledObjectFactory<Object> pooledObjectFactory = new MyPooledObjectFactory();

        GenericObjectPoolConfig<Object> config = new GenericObjectPoolConfig<>();

        {
            GenericObjectPool<Object> p = new GenericObjectPool<>(pooledObjectFactory, config);

            p.addObject();
            Object obj = p.borrowObject();
            p.invalidateObject(obj);
            p.returnObject(obj);
        }
        {
            SoftReferenceObjectPool<Object> p = new SoftReferenceObjectPool<>(pooledObjectFactory);
            p.addObject();
            Object obj = p.borrowObject();
            p.invalidateObject(obj);
            p.returnObject(obj);
        }

    }

    public static class MyPooledObjectFactory implements PooledObjectFactory<Object> {

        @Override
        public void activateObject(PooledObject<Object> p) throws Exception {
            //
        }

        @Override
        public void destroyObject(PooledObject<Object> p) throws Exception {

        }

        @Override
        public PooledObject<Object> makeObject() throws Exception {
            return null;
        }

        @Override
        public void passivateObject(PooledObject<Object> p) throws Exception {

        }

        @Override
        public boolean validateObject(PooledObject<Object> p) {
            return false;
        }
    }

}
