package groovy.lang;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMetaClass is a Querydsl query type for MetaClass
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QMetaClass extends BeanPath<MetaClass> {

    private static final long serialVersionUID = -1940748237L;

    public static final QMetaClass metaClass = new QMetaClass("metaClass");

    public final QMetaObjectProtocol _super = new QMetaObjectProtocol(this);

    public final SimplePath<org.codehaus.groovy.ast.ClassNode> classNode = createSimple("classNode", org.codehaus.groovy.ast.ClassNode.class);

    public final ListPath<MetaMethod, SimplePath<MetaMethod>> metaMethods = this.<MetaMethod, SimplePath<MetaMethod>>createList("metaMethods", MetaMethod.class, SimplePath.class, PathInits.DIRECT2);

    public final ListPath<MetaMethod, SimplePath<MetaMethod>> methods = this.<MetaMethod, SimplePath<MetaMethod>>createList("methods", MetaMethod.class, SimplePath.class, PathInits.DIRECT2);

    public final ListPath<MetaProperty, SimplePath<MetaProperty>> properties = this.<MetaProperty, SimplePath<MetaProperty>>createList("properties", MetaProperty.class, SimplePath.class, PathInits.DIRECT2);

    //inherited
    public final SimplePath<Class> theClass = _super.theClass;

    public QMetaClass(String variable) {
        super(MetaClass.class, forVariable(variable));
    }

    public QMetaClass(Path<? extends MetaClass> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMetaClass(PathMetadata metadata) {
        super(MetaClass.class, metadata);
    }

}

