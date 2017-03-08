package me.test.first.spring.boot.data.mongo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroovyUser is a Querydsl query type for GroovyUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroovyUser extends EntityPathBase<GroovyUser> {

    private static final long serialVersionUID = 1174270628L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroovyUser groovyUser = new QGroovyUser("groovyUser");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<org.joda.time.DateTime> dateCreated = createDateTime("dateCreated", org.joda.time.DateTime.class);

    public final BooleanPath delete = createBoolean("delete");

    public final org.bson.types.QObjectId id;

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public final DateTimePath<java.util.Date> lastUpdated = createDateTime("lastUpdated", java.util.Date.class);

    public final StringPath memo = createString("memo");

    public final groovy.lang.QMetaClass metaClass;

    public final StringPath name = createString("name");

    public final EnumPath<me.test.first.spring.boot.data.mongo.core.MyGroovyTypeEnum> type = createEnum("type", me.test.first.spring.boot.data.mongo.core.MyGroovyTypeEnum.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QGroovyUser(String variable) {
        this(GroovyUser.class, forVariable(variable), INITS);
    }

    public QGroovyUser(Path<? extends GroovyUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroovyUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroovyUser(PathMetadata metadata, PathInits inits) {
        this(GroovyUser.class, metadata, inits);
    }

    public QGroovyUser(Class<? extends GroovyUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new org.bson.types.QObjectId(forProperty("id")) : null;
        this.metaClass = inits.isInitialized("metaClass") ? new groovy.lang.QMetaClass(forProperty("metaClass")) : null;
    }

}

