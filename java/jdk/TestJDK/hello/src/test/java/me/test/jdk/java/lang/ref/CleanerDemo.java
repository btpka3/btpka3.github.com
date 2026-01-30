package me.test.jdk.java.lang.ref;


import java.lang.ref.Cleaner;
import java.util.zip.Deflater;

/**
 * @author dangqian.zll
 * @date 2023/12/18
 * @see jdk.internal.ref.CleanerFactory
 * @see Deflater.DeflaterZStreamRef#DeflaterZStreamRef(Deflater, long)
 */
public class CleanerDemo implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;

    public CleanerDemo() {
        this.cleanable = cleaner.register(this, () -> {

        });
    }

    public void close() {
        cleanable.clean();
    }
}

