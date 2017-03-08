package me.test.first.spring.boot.data.mongo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddr_Street is a Querydsl query type for Street
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAddr_Street extends BeanPath<Addr.Street> {

    private static final long serialVersionUID = 1496208501L;

    public static final QAddr_Street street = new QAddr_Street("street");

    public final StringPath id = createString("id");

    public final StringPath memo = createString("memo");

    public final StringPath name = createString("name");

    public QAddr_Street(String variable) {
        super(Addr.Street.class, forVariable(variable));
    }

    public QAddr_Street(Path<? extends Addr.Street> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddr_Street(PathMetadata metadata) {
        super(Addr.Street.class, metadata);
    }

}

