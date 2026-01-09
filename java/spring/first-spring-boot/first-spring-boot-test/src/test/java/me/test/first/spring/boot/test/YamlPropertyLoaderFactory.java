package me.test.first.spring.boot.test;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/9
 */
public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        List<PropertySource<?>> list = new YamlPropertySourceLoader()
                .load(resource.getResource().getFilename(), resource.getResource());
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return new MapPropertySource(name, new HashMap<>());
    }
}