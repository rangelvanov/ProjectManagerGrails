<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-task" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-task" class="content" role="main">
            <div class="col-md-12">
                <h1><g:message code="default.show.label" args="[entityName]" /></h1>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                <f:field property="name" label="Name: "> ${issue.name}</f:field>
                <f:field property="type" label="Type: "> ${issue.type}</f:field>
                <f:field property="priority" label="Priority: "><g:formatDate date="${issue.priority}" type="datetime" style="MEDIUM"></g:formatDate></f:field>
                <f:field property="description" label="Description: "><g:formatDate date="${issue.description}" type="datetime" style="MEDIUM"></g:formatDate></f:field>
                <f:field property="status" label="Status: "> ${issue.status}</f:field>
                <f:field property="ownerId" label="Owner: "> ${ com.ProjectManager.auth.User.get(issue.ownerId).username}</f:field>
                <f:field property="assigneeId" label="Assignee:">${com.ProjectManager.auth.User.get(issue.AssigneeId).username}</f:field>
                <f:field property="created" label="Created: "> ${issue.created}</f:field>
                <f:field property="updated" label="Updated: "> ${issue.updated}</f:field>
            </div>
            <div class="col-md-12" style="background-color: #888888">
                <g:form resource="${this.task}" method="DELETE">
                    <fieldset class="buttons">
                    <sec:access expression="hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERUSER')">
                        <g:link class="edit" action="edit" resource="${this.task}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </sec:access>
                        <g:link action="create" controller="Comment" params="[taskId: task.id]">Comemnt</g:link>
                    </fieldset>
                </g:form>
            </div>
        </div>
        <div class="col-md-12">
            <div class="col-md-10"><h3 class="right h3">Comments (${task.comments.size()})</h3></div>
            <div class="col-md-12"  style="height:350px;width:100%;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;">
                <g:each in="${task.comments.sort{ a, b -> b.date <=> a.date } }" var="c">
                    <div class="container">
                        <div class="media comment-box">
                            <div class="media-body">
                                <h4 class="media-heading">${com.ProjectManager.auth.User.get(c.userId).username} <small><i><g:formatDate date="${c.date}" type="datetime" style="MEDIUM"></g:formatDate></i></small>
                                </h4>
                                <p class="text">${c.commentContent}</p>
                            </div>
                        </div>
                    </div>
                </g:each>
            </div>
        </div>
    </body>
</html>
