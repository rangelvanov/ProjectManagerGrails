<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<a href="#list-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>
<sec:access expression="hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERUSER')">
    <div class="nav" role="navigation">
        <ul>
            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
        </ul>
    </div>
</sec:access>
<div class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table class="table table-striped table-hover" id="project-list">
        <thead>
        <g:sortableColumn title="Name" property="name"/>
        <g:sortableColumn title="Key" property="key"/>
        <g:sortableColumn title="Type" property="type"/>
        <g:sortableColumn title="Category" property="category"/>
        </thead>
        <tbody>
        <g:each in="${myProjects}" status="i" var="bean">
            <tr class="prop">
                <td><g:link method="GET" resource="${bean}">
                    <f:display bean="${bean}" property="name" displayStyle="${displayStyle?:'table'}" />
                </g:link></td>
                <td><f:display bean="${bean}" property="key" displayStyle="${displayStyle ?: 'table'}"/></td>
                <td><f:display bean="${bean}" property="type" displayStyle="${displayStyle ?: 'table'}"/></td>
                <td><f:display bean="${bean}" property="category" displayStyle="${displayStyle ?: 'table'}"/></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>