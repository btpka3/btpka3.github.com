package com.github.btpka3.first.spring.jooq;

import org.jooq.Converters;
import org.jooq.Geometry;
import org.jooq.impl.AbstractConverter;

/**
 *
 * @author dangqian.zll
* @date 2026/1/14
 * @see Converters
 */
public class MyConverter extends AbstractConverter<Geometry, String> {

    public MyConverter() {
        super(Geometry.class, String.class);
    }

    @Override
    public String from(Geometry databaseObject) {
        return null;
    }

    @Override
    public Geometry to(String userObject) {
        return null;
    }
}
