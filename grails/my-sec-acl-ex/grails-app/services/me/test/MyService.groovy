package me.test

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize


class MyService {

    @PreAuthorize("hasAnyRole('USER')")
    String user() {
        return "MyService#user()"
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    String admin() {
        return "MyService#admin()"
    }

    @PreAuthorize("hasPermission(#org,'READ')")
    boolean testRead(Org org) {
        return true
    }

    @PostFilter("hasPermission(filterObject, 'read')")
    List<Org> orgs() {
        // 需要排除没有 ACL (即 AclObjectIdentity 中没有记录）的数据，这里是 —— 威海
        // MongoDb 返回的是 MongoQuery$MongoResultList 类型，不能直接修改。
        List<Org> orgList = Org.createCriteria().list {
            ne("name", "威海")
        }

        // 不转变类型的话，DefaultMethodSecurityExpressionHandler#filter()
        // 执行时会抛出 UnsupportedOperationException
        List<Org> editableList = []
        editableList.addAll(orgList)
        return editableList
    }
}
