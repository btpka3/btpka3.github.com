package me.test.domain.cart

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.grails.datastore.mapping.engine.types.AbstractMappingAwareCustomTypeMarshaller
import org.grails.datastore.mapping.model.PersistentProperty

/**
 * 如何处理自定义的 Map<String, List<YouClass>>? 比较麻烦。
 * 请参考 http://stackoverflow.com/questions/11921985/how-to-support-embedded-maps-with-custom-value-types-in-mongodb-gorm/12234641#12234641
 */
class ItemListMapType extends AbstractMappingAwareCustomTypeMarshaller<Map, DBObject, DBObject> {

    ItemListMapType() {
        super(Map)
    }

    @Override
    protected Object writeInternal(PersistentProperty property, String key, Map value, DBObject nativeTarget) {
        println " writeInternal : property = " + property
        println " writeInternal : key = " + key
        println " writeInternal : value = " + value
        println " writeInternal : nativeTarget = " + nativeTarget
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

        println " readInternal : property = " + property
        println " readInternal : key = " + key
        println " readInternal : nativeSource = " + nativeSource

        // TODO

        return null
    }
}
