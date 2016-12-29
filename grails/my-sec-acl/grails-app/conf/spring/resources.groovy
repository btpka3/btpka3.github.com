import me.test.MyGormAclLookupStrategy

// Place your Spring DSL code here
beans = {

    aclLookupStrategy(MyGormAclLookupStrategy) {
        aclAuthorizationStrategy = ref('aclAuthorizationStrategy')
        aclCache = ref('aclCache')
        permissionFactory = ref('aclPermissionFactory')
        auditLogger = ref('aclAuditLogger')
        permissionGrantingStrategy = ref('aclPermissionGrantingStrategy')
    }

    springConfig.addAlias 'aclService', 'myAclService'
}
