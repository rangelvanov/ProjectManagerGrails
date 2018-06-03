<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'task.label', default: 'Task')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-task" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <sec:access expression="hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERUSER')">
            <div class="nav" role="navigation">
                <ul>
                    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </ul>
            </div>
        </sec:access>
        <div id="list-task" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
         <%--   <f:table collection="${CurrentUserTasks}" properties="['id','title','userId', 'responsibleUserId','startDate','dateLastChange', 'status', 'project']"/> --%>

            <table class="table table-striped table-hover" id="project-tasks">
                <thead>
                <g:sortableColumn title="id" property="id"/>
                <g:sortableColumn title="Title" property="title"/>
                <g:sortableColumn title="Created by" property="userId"/>
                <g:sortableColumn title="Responsible User" property="responsibleUserId"/>
                <g:sortableColumn title="Start date" property="startDate"/>
                <g:sortableColumn title="Last Updated" property="DateLastChange"/>
                <g:sortableColumn title="Status" property="status"/>
                <g:sortableColumn title="Project" property="project"/>

                </thead>

                <tbody>
                <g:each in="${CurrentUserTasks.sort{ a, b -> b.startDate <=> a.startDate }}" status="i" var="task">
                    <tr class="prop">
                        <td><g:link method="GET" resource="${task}"><f:display bean="${task}" property="id" displayStyle="${displayStyle?:'table'}" /></g:link></td>
                        <td><f:display bean="${task}" property="title"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><f:display bean="${task}" property="userId"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><f:display bean="${task}" property="responsibleUserId"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><g:formatDate date="${task.startDate}" type="datetime" style="MEDIUM"></g:formatDate></td>
                        <td><g:formatDate date="${task.dateLastChange}" type="datetime" style="MEDIUM"></g:formatDate></td>
                        <td><f:display bean="${task}" property="status"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><f:display bean="${task}" property="project.title"  displayStyle="${displayStyle?:'table'}" /></td>

                    </tr>
                </g:each>
                </tbody>
            </table>


            <div class="pagination">
                <g:paginate total="${taskCount ?: 0}" />
            </div>
        </div>
    </body>
</html>