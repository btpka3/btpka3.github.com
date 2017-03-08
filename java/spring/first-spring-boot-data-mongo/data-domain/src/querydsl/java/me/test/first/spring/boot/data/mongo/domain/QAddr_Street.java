package me.test.first.spring.boot.data.mongo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAddr_Street is a Querydsl query type for Street
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAddr_Street extends BeanPath<Addr.Street> {

    private static final long serialVersionUID = 1496208501L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAddr_Street street = new QAddr_Street("street");

    public final org.bson.types.QObjectId id;

    public final StringPath memo = createString("memo");

    public final StringPath name = createString("name");

    public QAddr_Street(String variable) {
        this(Addr.Street.class, forVariable(variable), INITS);
    }

    public QAddr_Street(Path<? extends Addr.Street> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAddr_Street(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAddr_Street(PathMetadata metadata, PathInits inits) {
        this(Addr.Street.class, metadata, inits);
    }

    public QAddr_Street(Class<? extends Addr.Street> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new org.bson.types.QObjectId(forProperty("id")) : null;
    }

}

