package projectmanager

import com.ProjectManager.auth.User
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException


import static org.springframework.http.HttpStatus.*

class IssueController {

    IssueService issueService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def index() {
        def result
        User loggedUser = User.get(springSecurityService.currentUserId)

        if (SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            result = Issue.findAll()
        } else {
            result = Issue.findAllByOwnerIdOrAssigneeId(loggedUser.id, loggedUser.id)
        }
        if (result.isEmpty()) {
            flash.message = "No current tasks available!"
        }
        [CurrentUserIssue: result]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def show(Long id) {

        Issue issue = Issue.findById(id)

        def allComments = issue.comments
        def allWorkLog = issue.workLogs

        render view: "show", model: [issue: issueService.get(id),allComments: allComments, workLogs: allWorkLog]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def create() {
        respond new Issue(params)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def save(Issue issue) {

        if (issue == null) {
            notFound()
            return
        }
        if (issue.project == null) {
            redirect(action: 'create', controller: 'issue')
            flash.message = "Create some Project first!"
            return
        }

        issue.setCreated(new Date())
        issue.setUpdated(new Date())
        issue.setOwnerId(springSecurityService.currentUserId)


        try {
            issueService.save(issue)
        } catch (ValidationException e) {
            respond issue.errors, view: 'create'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'issue.label', default: 'Issue'), issue.id])
                redirect issue
            }
            '*' { respond issue, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def edit(Long id) {
        respond issueService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def update(Issue issue) {
        if (issue == null) {
            notFound()
            return
        }
        issue.updated = new Date()
        try {
            issueService.save(issue)
        } catch (ValidationException e) {
            respond issue.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'issue.label', default: 'Issue'), issue.id])
                redirect issue
            }
            '*' { respond issue, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        issueService.delete(id)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'issue.label', default: 'Issue'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'issue.label', default: 'Issue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

}
