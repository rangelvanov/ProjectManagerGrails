<%@ page import="projectmanager.Project; com.ProjectManager.auth.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
              <%--  <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li> --%>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>

            </ul>
        </div>
        <div id="show-project" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <f:field property="title" label="Title: ">${ project.title}</f:field>
            <f:field property="description" label="Description: ">${project.description}</f:field>
            <f:field property="startDate" label="Start Date: "><g:formatDate date="${project.startDate}" type="datetime" style="MEDIUM"></g:formatDate></f:field>
            <f:field property="dateLastChange" label="Last updated: "><g:formatDate date="${project.dateLastChange}" type="datetime" style="MEDIUM"></g:formatDate></f:field>
            <f:field property="status" label="Status: ">${project.status}</f:field>
            <f:field property="userId" label="Created by: ">${User.get(project.userId).username}</f:field>

            <sec:access expression="hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERUSER')">
                <g:form resource="${this.project}" method="DELETE">
                    <fieldset class="buttons">
                        <g:link class="edit" action="edit" resource="${this.project}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </fieldset>
                </g:form>
            </sec:access>

    </body>
</html>
