package projectmanager

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

//        "/"(view:"/home/index")
        "/"(controller: 'project', action: 'index')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }

}
