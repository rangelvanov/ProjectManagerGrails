package projectmanager

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

class CommentController {

    CommentService commentService
    Long idIssue

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService


    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond commentService.list(params), model: [commentCount: commentService.count()]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def show(Long id) {
        respond commentService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def create() {
        println("Issue id from param __ " + params.issueId)
        idIssue = Long.parseLong(params.issueId)
        println("Issue actual id from param __ " + idIssue)
        respond new Comment(params)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER', 'ROLE_SUPERUSER'])
    def save(Comment comment) {
//        if (comment == null) {
//            notFound()
//            return
//        }

        println("idIssue in safe__ " + idIssue)

        Long loggedUserId = springSecurityService.currentUserId
        def issueTemp = Issue.findById(idIssue)
        println("Issue koeto shte bude komentirano __" + issueTemp.name)
        if (issueTemp == null) {
            redirect(action: 'index', controller: 'Issue')
            return
        }
        if (loggedUserId == issueTemp.ownerId || loggedUserId == issueTemp.assigneeId) {
            comment.setUserId(loggedUserId)
            comment.setCreated(new Date())
            comment.setIssue(issueTemp)
            idIssue = 0
            try {
                commentService.save(comment)
                idIssue = 0
            } catch (ValidationException e) {
                respond comment.errors, view: 'create'
                return
            }
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                    redirect(action: 'show', controller: 'Issue', id: issueTemp.id)
                    //    redirect comment
                }
                '*' { respond comment, [status: CREATED] }
            }

        } else {
            redirect(action: 'index', controller: 'Issue')
            flash.message = "You cannot comment this issue!"
            return
        }


    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def edit(Long id) {
        respond commentService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def update(Comment comment) {
        if (comment == null) {
            notFound()
            return
        }

        try {
            commentService.save(comment)
        } catch (ValidationException e) {
            respond comment.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*' { respond comment, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERUSER'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        commentService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
