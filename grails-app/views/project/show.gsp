<%@ page import="projectmanager.Project; com.ProjectManager.auth.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-6 col-md-4">

            <div class="title"><h3>Project ${project.name}</h3></div>

            <div class="fieldcontain"><f:field property="key" label="Key: ">${project.key}</f:field></div>

            <div class="fieldcontain"><f:field property="type" label="Type: ">${project.type}</f:field></div>

            <div class="fieldcontain"><f:field property="ownerId"
                                               label="Owner: ">${User.get(project.ownerId).username}</f:field></div>

            <div class="fieldcontain"><f:field property="category"
                                               label="Category: ">${project.category}</f:field></div>
        </div>

        <div class="col-md-8">
            <div class="title"><h3>Issues List</h3></div>

            <div style="display: block;
            max-height: 350px;
            overflow-y: auto;
            -ms-overflow-style: -ms-autohiding-scrollbar;
            ">
                <table class="table table-responsive-md">
                    <thead class="mdb-color lighten-4">
                    <tr>
                        <th>#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Type</th>
                        <th scope="col">Priorty</th>
                        <th scope="col">Status</th>
                        <th scope="col">Assignee</th>
                        <th scope="col">Created</th>
                    </tr>
                    </thead>

                    <tbody>
                    <g:each in="${issues.sort { a, b -> b.created <=> a.created }}" status="i" var="task">
                        <tr>
                            <th>${i}</th>
                            <td>
                                <g:link method="GET" resource="${task}">
                                    <f:display bean="${task}" property="name"/>
                                </g:link>
                            </td>

                            <td><f:display bean="${task}" property="type"/></td>
                            <td><f:display bean="${task}" property="priority"/></td>
                            <td><f:display bean="${task}" property="status"/></td>
                            <td><f:display bean="${task}" property="assigneeId"/></td>
                            <td><f:display bean="${task}" property="created"/></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>

        </div>


    </div>
    <div class="row" style="margin-top: 50px">
        <div class="col-md-12" style="background-color: #888888">
            <sec:access expression="hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERUSER')">

                <g:form resource="${this.project}" method="DELETE">
                    <fieldset class="buttons">
                        <g:link action="edit" resource="${this.project}"><g:message
                                code="default.button.edit.label" default="Edit"/></g:link>
                        <input type="submit"
                               value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                               onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </fieldset>
                </g:form>
            </sec:access>
        </div>
    </div>

</div>
</body>
</html>
