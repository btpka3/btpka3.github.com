package me.test.jdk.java.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author dangqian.zll
 * @date 2026/2/6
 * @see java.lang.CompoundEnumeration
 * @see org.apache.tools.ant.taskdefs.optional.junit.CompoundEnumeration
 * @see net.bytebuddy.dynamic.loading.MultipleParentClassLoader.CompoundEnumeration
 * @see org.assertj.core.internal.bytebuddy.dynamic.loading.MultipleParentClassLoader.CompoundEnumeration
 */
public class CompoundEnumeration<T> implements Enumeration<T> {
    private final List<Enumeration<T>> enumerations;
    private Enumeration<T> current;

    public CompoundEnumeration(List<Enumeration<T>> enumerations) {
        this.enumerations = enumerations;
    }

    public CompoundEnumeration(Enumeration<T>... enumerations) {
        this(enumerations == null || enumerations.length == 0
                ? Collections.emptyList()
                : Arrays.asList(enumerations)
        );
    }

    public boolean hasMoreElements() {
        if (this.current != null && this.current.hasMoreElements()) {
            return true;
        } else if (!this.enumerations.isEmpty()) {
            this.current = this.enumerations.remove(0);
            return this.hasMoreElements();
        } else {
            return false;
        }
    }

    public T nextElement() {
        if (this.hasMoreElements()) {
            return this.current.nextElement();
        } else {
            throw new NoSuchElementException();
        }
    }
}