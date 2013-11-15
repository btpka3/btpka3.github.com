package me.test.first.redis.cache;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUser extends com.mysema.query.sql.RelationalPathBase<User> {

    private static final long serialVersionUID = 2120969774;

    public static final QUser user = new QUser("USER");

    public final BooleanPath gender = createBoolean("GENDER");

    public final NumberPath<Long> id = createNumber("ID", Long.class);

    public final StringPath name = createString("NAME");

    public final StringPath signature = createString("SIGNATURE");

    public final com.mysema.query.sql.PrimaryKey<User> constraint2 = createPrimaryKey(id);

    public QUser(String variable) {
        super(User.class, forVariable(variable), "PUBLIC", "USER");
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata(), "PUBLIC", "USER");
    }

    public QUser(PathMetadata<?> metadata) {
        super(User.class, metadata, "PUBLIC", "USER");
    }

}

