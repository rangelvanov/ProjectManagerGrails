package projectmanager

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

class WorkLogController {

    WorkLogService workLogService
    Long idIssue

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond workLogService.list(params), model: [workLogCount: workLogService.count()]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def show(Long id) {
        respond workLogService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def create() {
        println("Issue id from param __ " + params.issueId)
        idIssue = Long.parseLong(params.issueId)
        println("Issue actual id from param __ " + idIssue)
        respond new WorkLog(params)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def save(WorkLog workLog) {
        if (workLog == null) {
            notFound()
            return
        }

        if(workLog.comment.equals(null) || workLog.timeSpent.equals(null)){
            redirect(action: 'create', controller: 'WorkLog',  params: [issueId: idIssue])
            return
        }

        println("idIssue in safe__ " + idIssue)

        Long loggedUserId = springSecurityService.currentUserId
        def issueTemp = Issue.findById(idIssue)
        println("Issue koeto shte bude komentirano __" + issueTemp.name)
        if (issueTemp == null) {
            redirect(action: 'index', controller: 'Issue')
            return
        }
        if (loggedUserId == issueTemp.ownerId || loggedUserId == issueTemp.assigneeId) {
            workLog.setUserId(loggedUserId)
            workLog.setCreated(new Date())
            workLog.setIssue(issueTemp)
            idIssue = 0

            try {
                workLogService.save(workLog)
                idIssue = 0
            } catch (ValidationException e) {
                respond workLog.errors, view: 'create'
                return
            }

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'workLog.label', default: 'WorkLog'), workLog.id])
                    redirect(action: 'show', controller: 'Issue', id: issueTemp.id)
                }
                '*' { respond workLog, [status: CREATED] }
            }
        }else {
            redirect(action: 'index', controller: 'Issue')
            flash.message = "You cannot log wok this issue!"
            return
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def edit(Long id) {
        respond workLogService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def update(WorkLog workLog) {
        if (workLog == null) {
            notFound()
            return
        }

        try {
            workLogService.save(workLog)
        } catch (ValidationException e) {
            respond workLog.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workLog.label', default: 'WorkLog'), workLog.id])
                redirect workLog
            }
            '*' { respond workLog, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        workLogService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workLog.label', default: 'WorkLog'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workLog.label', default: 'WorkLog'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
