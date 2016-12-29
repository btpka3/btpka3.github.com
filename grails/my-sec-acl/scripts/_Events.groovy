eventPluginInstalled = { String pluginName ->

    //println "==================== eventPluginInstalled : $pluginName ,$grailsSettings " // $projectPluginsDir

    if (pluginName.startsWith("spring-security-acl")) {
        //ant.delete(dir: "$grailsSettings.projectPluginsDir/$pluginName/grails-app/domain")
    }

}

