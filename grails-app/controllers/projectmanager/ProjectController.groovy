package projectmanager
import com.ProjectManager.auth.User
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.SpringSecurityUtils

class ProjectController {

    ProjectService projectService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def index() {
        def result
        User loggedUser = User.get(springSecurityService.currentUserId)

        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
            result = Project.findAll()
        }
        else{
            result = Project.getAllByTRU(loggedUser.id)
        }
        if(result.isEmpty()){
            flash.message = "No current projects available!"
        }
            [myProjects: result]
    }
    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def show(Long id) {
        Project project = Project.findById(id)
        User loggedUser = User.get(springSecurityService.currentUserId)
        if (project == null) {
            notFound()
            return
        }
        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
            respond projectService.get(id)
            return
        }
        if(project.userId == loggedUser.id){
            respond projectService.get(id)
            return
        }
        else {
            for(Task task : project.tasks){

                if(task.userId == loggedUser.id || task.responsibleUserId == loggedUser.id){
                    respond projectService.get(id)
                    return
                }
            }
        }

        redirect(action: "index", controller: "project")
        flash.message = "Sorry! You are not allowed to access this project!"
        }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def create() {
        respond new Project(params)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def save(Project project) {
        if (project == null) {
            notFound()
            return
        }
        project.userId = springSecurityService.currentUserId
        project.dateLastChange = new Date()
        try {
            projectService.save(project)
        } catch (ValidationException e) {
            respond project.errors, view:'create'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), project.id])
                redirect project
            }
            '*' { respond project, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def edit(Long id) {
        respond projectService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def update(Project project) {
        if (project == null) {
            notFound()
            return
        }
        project.dateLastChange = new Date()
        try {
            projectService.save(project)
        } catch (ValidationException e) {
            respond project.errors, view:'edit'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), project.id])
                redirect project
            }
            '*'{ respond project, [status: OK] }
        }
    }

    @Secured('ROLE_ADMIN')
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        projectService.delete(id)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'project.label', default: 'Project'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

}
