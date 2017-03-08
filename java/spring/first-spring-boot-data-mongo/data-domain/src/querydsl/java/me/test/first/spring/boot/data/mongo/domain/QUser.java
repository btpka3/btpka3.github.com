package me.test.first.spring.boot.data.mongo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -803222090L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final ListPath<Addr, QAddr> arrList = this.<Addr, QAddr>createList("arrList", Addr.class, QAddr.class, PathInits.DIRECT2);

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<org.joda.time.DateTime> dateCreated = createDateTime("dateCreated", org.joda.time.DateTime.class);

    public final BooleanPath delete = createBoolean("delete");

    public final org.bson.types.QObjectId id;

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public final DateTimePath<java.util.Date> lastUpdated = createDateTime("lastUpdated", java.util.Date.class);

    public final StringPath memo = createString("memo");

    public final StringPath name = createString("name");

    public final EnumPath<me.test.first.spring.boot.data.mongo.core.MyTypeEnum> type = createEnum("type", me.test.first.spring.boot.data.mongo.core.MyTypeEnum.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new org.bson.types.QObjectId(forProperty("id")) : null;
    }

}

