<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <div class="title"><h3>Issue ${issue.name}</h3></div>

            <div class="fieldcontain"><f:field property="type" label="Type: ">${issue.type}</f:field></div>

            <div class="fieldcontain"><f:field property="priority" label="Priority: ">${issue.priority}"</f:field></div>

            <div class="fieldcontain"><f:field property="description"
                                               label="Description: ">< ${issue.description}"</f:field></div>

            <div class="fieldcontain"><f:field property="status" label="Status: ">${issue.status}</f:field></div>

            <div class="fieldcontain"><f:field property="ownerId"
                                               label="Owner: ">${com.ProjectManager.auth.User.get(issue.ownerId).username}</f:field></div>

            <div class="fieldcontain"><f:field property="assigneeId"
                                               label="Assignee:">${com.ProjectManager.auth.User.get(issue.assigneeId).username}</f:field></div>

            <div class="fieldcontain"><f:field property="created" label="Created: ">${issue.created}</f:field></div>

            <div class="fieldcontain"><f:field property="updated" label="Updated: ">${issue.updated}</f:field></div>

        </div>

        <div class="col-md-8">
            <div class="title"><h3>Work Log List</h3></div>
            <div style="display: block;
            max-height: 350px;
            overflow-y: auto;
            -ms-overflow-style: -ms-autohiding-scrollbar;
            ">
                <table class="table table-responsive-md">
                    <thead class="mdb-color lighten-4">
                    <tr>
                        <th>#</th>
                        <th scope="col">User</th>
                        <th scope="col">created</th>
                        <th scope="col">timeSpent</th>
                    </tr>
                    </thead>

                    <tbody>
                    <g:each in="${issue.workLogs.sort { a, b -> b.created <=> a.created }}" status="i" var="workLog">
                        <tr>
                            <th>${i}</th>
                            <td>
                                <g:link method="GET" resource="${workLog}">
                                    <f:display bean="${workLog}" property="id"/>
                                </g:link>
                            </td>
                            <td><f:display bean="${workLog}" property="userId"/></td>
                            <td><f:display bean="${workLog}" property="created"/></td>
                            <td><f:display bean="${workLog}" property="timeSpent"/></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

    <div class="row" style="margin-top: 50px">
        <div class="col-md-12" style="background-color: #888888">
            <g:form resource="${this.issue}" method="DELETE">
                <fieldset class="buttons">
                    <sec:access expression="hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERUSER')">
                        <g:link action="edit" resource="${this.issue}"><g:message
                                code="default.button.edit.label" default="Edit"/></g:link>
                        <input type="submit"
                               value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                               onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </sec:access>
                    <g:link action="create" controller="Comment" params="[issueId: issue.id]">Comemnt</g:link>
                    <g:link action="create" controller="WorkLog" params="[issueId: issue.id]">Log Work</g:link>
                </fieldset>
            </g:form>
        </div>
    </div>

</div>


<div class="col-md-12">
    <div class="col-md-10"><h3 class="right h3">Comments (${issue.comments.size()})</h3></div>

    <div class="col-md-12"
         style="height:350px;width:100%;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;">
        <g:each in="${issue.comments.sort { a, b -> b.created <=> a.created }}" var="c">
            <div class="container">
                <div class="media comment-box">
                    <div class="media-body">
                        <h4 class="media-heading">${com.ProjectManager.auth.User.get(c.userId).username} <small><i><g:formatDate
                                date="${c.created}" type="datetime" style="MEDIUM"></g:formatDate></i></small>
                        </h4>

                        <p class="text">${c.content}</p>
                    </div>
                </div>
            </div>
        </g:each>
    </div>
</div>
</body>
</html>
