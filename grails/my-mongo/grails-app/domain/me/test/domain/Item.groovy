package me.test.domain

import org.bson.types.ObjectId

@groovy.transform.ToString(includeNames = true)
class Item {

    ObjectId id
    String name
    int count
    Date createTime
}
