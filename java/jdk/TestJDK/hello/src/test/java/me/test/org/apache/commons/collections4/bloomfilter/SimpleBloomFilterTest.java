package me.test.org.apache.commons.collections4.bloomfilter;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.commons.collections4.bloomfilter.BloomFilter;
import org.apache.commons.collections4.bloomfilter.EnhancedDoubleHasher;
import org.apache.commons.collections4.bloomfilter.Shape;
import org.apache.commons.collections4.bloomfilter.SimpleBloomFilter;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/1/8
 * @see <a href="https://commons.apache.org/proper/commons-collections/bloomFilters.html">Bloom filters</a>
 */
public class SimpleBloomFilterTest {
    public void merge(BloomFilter<?> filter, Serializable o) {
        byte[] bytes = SerializationUtils.serialize(o);
        long[] hash = MurmurHash3.hash128(bytes);
        filter.merge(new EnhancedDoubleHasher(hash[0], hash[1]));
    }

    public boolean search(BloomFilter<?> filter, Serializable o) {
        byte[] bytes = SerializationUtils.serialize(o);
        long[] hash = MurmurHash3.hash128(bytes);
        return filter.contains(new EnhancedDoubleHasher(hash[0], hash[1]));
    }

    @Test
    public void test01() {
        Shape shape = Shape.fromNP(10000, 0.01);
        BloomFilter<SimpleBloomFilter> filter = new SimpleBloomFilter(shape);
        List<String> items = List.of("aaa", "bbb", "ccc");

        items.forEach(i -> merge(filter, i));

        List<String> searchItems = List.of("aaa", "bbb", "ccc", "ddd");
        searchItems.forEach(i -> {
            boolean contains = search(filter, i);
            System.out.println("" + i + " : " + contains);
        });
    }
}
