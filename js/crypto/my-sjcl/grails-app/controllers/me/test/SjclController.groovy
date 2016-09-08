package me.test

import org.springframework.core.io.Resource
import org.springframework.http.MediaType

class SjclController {
    def assetResourceLocator

    def index() {

        // 在Controller中使用标签方法:
        // asset.pipeline.grails.AssetsTagLib
        // asset.pipeline.grails.AssetMethodTagLib
        // 以下代码也等同于 : grailsApplication.mainContext.getBean("asset.pipeline.grails.AssetMethodTagLib")..assetPath(src: "sjcl.html")
        def htmlPath = g.assetPath(src: "sjcl.html")
        redirect(uri: htmlPath);

        // 该方法因为URL未变更,故不采用
        //render file: myResource.inputStream, contentType: MediaType.TEXT_HTML
    }
}
