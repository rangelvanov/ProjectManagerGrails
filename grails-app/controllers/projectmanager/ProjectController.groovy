package projectmanager

import com.ProjectManager.auth.User
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

class ProjectController {

    ProjectService projectService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def index() {
        def result
        User loggedUser = User.get(springSecurityService.currentUserId)

        if (SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            result = Project.findAll()
        } else {
            result = Project.getAllByTRU(loggedUser.id)
        }
        if (result.isEmpty()) {
            flash.message = "No current projects available!"
        }
        [myProjects: result]

    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def show(Long id) {
        def issuesToUse
       def project = Project.findById(id)
        long loggedUser = springSecurityService.currentUserId
//        issuesToUse = Issue.findAllByOwnerIdAndAssigneeIdAndProject(loggedUser, loggedUser, projectService.get(id))
        issuesToUse = Issue.findAllByProject(project)

        render view: "show", model: [project: projectService.get(id), issues: issuesToUse]

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
        project.ownerId = springSecurityService.currentUserId

        try {
            projectService.save(project)
        } catch (ValidationException e) {
            respond project.errors, view: 'create'
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

        try {
            projectService.save(project)
        } catch (ValidationException e) {
            respond project.errors, view: 'edit'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), project.id])
                redirect project
            }
            '*' { respond project, [status: OK] }
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
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

}
