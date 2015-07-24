package me.test.domain.cart

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.grails.datastore.mapping.engine.types.AbstractMappingAwareCustomTypeMarshaller
import org.grails.datastore.mapping.model.PersistentProperty

/**
 *
 */
class ItemListMapType1 extends AbstractMappingAwareCustomTypeMarshaller<Map, DBObject, DBObject> {

    ItemListMapType1() {
        super(Map)
    }

    @Override
    protected Object writeInternal(PersistentProperty property, String key, Map value, DBObject nativeTarget) {
        println " writeInternal_1 : property = " + property
        println " writeInternal_1 : key = " + key
        println " writeInternal_1 : value = " + value
        println " writeInternal_1 : nativeTarget = " + nativeTarget
        if (!value) {
            return value
        }

        def obj = new BasicDBObject()

        value.each { k, v ->

            if (v == null) {
                obj.put(k, null)
                return
            }

            if (v != null && !v) {
                obj.put(k, new BasicDBList())
                return
            }

            def list = new BasicDBList()
            obj.put(k, list)
            list.addAll(v*.id)
        }
        nativeTarget.put(key, obj)
        return value
    }

    @Override
    protected Map readInternal(PersistentProperty property, String key, DBObject nativeSource) {

        println " readInternal_1 : property = " + property
        println " readInternal_1 : key = " + key
        println " readInternal_1 : nativeSource = " + nativeSource

        return nativeSource
    }
}
