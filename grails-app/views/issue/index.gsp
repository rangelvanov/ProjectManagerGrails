<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}" />
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
         <%--   <f:table collection="${CurrentUserIssue}" properties="['id','name','ownerId', 'assigneeId','created','updated', 'category', 'project']"/> --%>

            <table class="table table-striped table-hover" id="project-tasks">
                <thead>
                <g:sortableColumn title="Name" property="name"/>
                <g:sortableColumn title="Type" property="type"/>
                <g:sortableColumn title="Priority" property="priority"/>
                <g:sortableColumn title="Status" property="status"/>
                <g:sortableColumn title="Updated" property="updated"/>


                </thead>

                <tbody>
                <g:each in="${CurrentUserIssue.sort{ a, b -> b.created <=> a.created }}" status="i" var="issue">
                    <tr class="prop">

                        <td><g:link method="GET" resource="${issue}">
                            <f:display bean="${issue}" property="name" displayStyle="${displayStyle?:'table'}" />
                        </g:link></td>
                        <td><f:display bean="${issue}" property="type"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><f:display bean="${issue}" property="priority"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><f:display bean="${issue}" property="status"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><g:formatDate date="${issue.updated}" type="datetime" style="MEDIUM"></g:formatDate></td>

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