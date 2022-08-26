package com.github.btpka3.first.spring.jooq;

import org.jooq.Converter;
import org.jooq.Geography;

/**
 * @author dangqian.zll
 * @date 2022/8/26
 */
public class GeographyConverter implements Converter<Geography, byte[]> {
    @Override
    public byte[] from(Geography databaseObject) {
        return new byte[0];
    }

    @Override
    public Geography to(byte[] userObject) {
        return null;
    }

    @Override
    public Class<Geography> fromType() {
        return null;
    }

    @Override
    public Class<byte[]> toType() {
        return null;
    }
}
