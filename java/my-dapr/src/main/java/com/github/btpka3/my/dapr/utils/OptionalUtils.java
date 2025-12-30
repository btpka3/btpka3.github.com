package com.github.btpka3.my.dapr.utils;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author dangqian.zll
 * @date 2020/8/25
 */
public class OptionalUtils {

    @SafeVarargs
    public static <T> Optional<T> or(
        Optional<T>... opts
    ) {
        for (Optional<T> opt : opts) {
            if (opt.isPresent()) {
                return opt;
            }
        }
        return Optional.empty();
    }

    @SafeVarargs
    public static <T> Optional<T> or(
        Supplier<Optional<T>>... optSuppliers
    ) {
        for (Supplier<Optional<T>> s : optSuppliers) {
            Optional<T> opt = s.get();
            if (opt.isPresent()) {
                return opt;
            }
        }
        return Optional.empty();
    }


}
