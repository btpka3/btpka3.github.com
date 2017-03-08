package me.test.first.spring.boot.data.mongo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAddr is a Querydsl query type for Addr
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAddr extends EntityPathBase<Addr> {

    private static final long serialVersionUID = -803832356L;

    public static final QAddr addr = new QAddr("addr");

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<org.joda.time.DateTime> dateCreated = createDateTime("dateCreated", org.joda.time.DateTime.class);

    public final BooleanPath delete = createBoolean("delete");

    public final StringPath id = createString("id");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public final DateTimePath<java.util.Date> lastUpdated = createDateTime("lastUpdated", java.util.Date.class);

    public final StringPath name = createString("name");

    public final ListPath<Addr.Street, QAddr_Street> streetList = this.<Addr.Street, QAddr_Street>createList("streetList", Addr.Street.class, QAddr_Street.class, PathInits.DIRECT2);

    public QAddr(String variable) {
        super(Addr.class, forVariable(variable));
    }

    public QAddr(Path<? extends Addr> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddr(PathMetadata metadata) {
        super(Addr.class, metadata);
    }

}

