package my.sjcl

class UrlMappings {

    static excludes = [
            '/a.txt',
            '/sitemap.xml'
    ]
    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
