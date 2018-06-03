package projectmanager
import com.ProjectManager.auth.User
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class TaskController {

    TaskService taskService
    CommentService commentService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def index() {
        def result
        User loggedUser = User.get(springSecurityService.currentUserId)

        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
            result = Task.findAll()
        }
        else{
            result = Task.findAllByUserIdOrResponsibleUserId(loggedUser.id, loggedUser.id)
        }
        if (result.isEmpty()) {
            flash.message = "No current tasks available!"
        }
        [CurrentUserTasks: result]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def show(Long id) {

        Task task = Task.findById(id)
        User loggedUser = User.get(springSecurityService.currentUserId)
        if (task == null) {
            notFound()
            return
        }
        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
            respond taskService.get(id)
            return
        }

        if (loggedUser.id == task.userId || loggedUser.id == task.responsibleUserId) {
            respond taskService.get(id)
            return
        }
        else {
            redirect(action: "index", controller: "task")
            flash.message = "Sorry! You are not allowed to access this task!"
            return
        }
        def allComments = task.comments
        [allComments: allComments]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def create() {
        respond new Task(params)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def save(Task task) {
        if (task == null) {
            notFound()
            return
        }
        if (task.project == null){
            redirect(action: 'create', controller: 'task')
            flash.message = "Create some Project first!"
            return
        }
        Project tempProject = Project.findById(task.project.id)

        tempProject.dateLastChange = new Date()

        task.userId = springSecurityService.currentUserId
        task.dateLastChange = new Date()
        try {
            taskService.save(task)
        } catch (ValidationException e) {
            respond task.errors, view: 'create'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                redirect task
            }
            '*' { respond task, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def edit(Long id) {
        respond taskService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def update(Task task) {
        if (task == null) {
            notFound()
            return
        }
        Project tempProject = Project.findById(task.project.id)

        tempProject.dateLastChange = new Date()
        task.dateLastChange = new Date()
        try {
            taskService.save(task)
        } catch (ValidationException e) {
            respond task.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'task.label', default: 'Task'), task.id])
                redirect task
            }
            '*' { respond task, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        Task tempTask = Task.get(id)
        Project tempProject = Project.findById(tempTask.project.id)
        tempProject.dateLastChange = new Date()

        taskService.delete(id)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'task.label', default: 'Task'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'task.label', default: 'Task'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

}
