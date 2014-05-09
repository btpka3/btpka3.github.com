复制 Spring Security 3.1.1 中 samples/contact. 
来源请参考：https://github.com/spring-projects/spring-security

主要是由于Spring使用Gradle作为构建工具，还需要先学习Groovy，方便熟悉Maven的人先查看。
主要就增加一个pom.xml

FIXME 权限的继承关系如何使用？

FIXME ACL_SID存储的是权限业务场景？类似于SQL中对权限的grant？
      SQL：
        GRANT 
          select  -- acl_entry.mask 
        ON 
          address -- acl_object_identity.(object_id_class, object_id_identity)
        TO
          scott   -- acl_entry.sid 
        -- 前提，当前操作者有对 adress 的管理(?)权限

      ACL_SID 中存储权限A

FIXME 审核机制如何使用？
      个人资料信息的所有者A授权社交应用B可以读取
      微博账户所有者A授权社交应用B可以在自己的微博上发消息

FIXME Cache在更新时能确保下次获取最新么？

FIXME Permission 只能用mask？框架底层会将多条记录合并成一条记录么？

FIXME 什么时候应当使用ACL？什么时候使用普通的功能权限？两者又该如何联合？
      能明确出ACL_OBJECT_IDENTITY时使用ACL？

？ 总院下有多家分院。管理员A对总院有管理权限，则分院1的parent是总院、且允许继承的话，则管理员A也对分院1有管理权限。  
？ 权限继承关系只能应用在Domain Object有层次关系？而无法应用再使用者有组织层次？后者需要自定义关联表实现？
   比如： @PreAuthorize("hasRole('ROLE_USER') or hasPermission(#hospital, 'admin')")

？ 而员工A是分院1的院长，并且是分院2的主任医生，在HIS中登录到不同的医院时会有不同的权限。应当SwitchHospital，然后重新加载权限。  
？ 员工A同时隶属于多个科室，但是在不同科室有不同的权限。使用ACL？  
？ HIS系统新开发了一个收费统计模块，但是需要医院交钱才能使用，而医院购买后，又不是让所有医院员工都可使用该功能，只限定部分管理者可用。  
