<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <sec:access expression="hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERUSER')">
           <div class="nav" role="navigation">
               <ul>
                   <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
               </ul>
           </div>
       </sec:access>
        <div id="list-project" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table class="table table-striped table-hover" id="project-list">
                <thead>
                <g:sortableColumn title="id" property="id"/>
                <g:sortableColumn title="Title" property="title"/>
                <g:sortableColumn title="Created by" property="userId"/>
                <g:sortableColumn title="Start Date" property="startDate"/>
                <g:sortableColumn title="Last Updated" property="DateLastChange"/>
                <g:sortableColumn title="Status" property="status"/>

                </thead>

                <tbody>
                <g:each in="${myProjects.sort{ a, b -> b.startDate <=> a.startDate } }" status="i" var="bean">
                    <tr class="prop">
                        <td><g:link method="GET" resource="${bean}"><f:display bean="${bean}" property="id" displayStyle="${displayStyle?:'table'}" /></g:link></td>
                        <td><f:display bean="${bean}" property="title"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><f:display bean="${bean}" property="userId"  displayStyle="${displayStyle?:'table'}" /></td>
                        <td><g:formatDate date="${bean.startDate}" type="datetime" style="MEDIUM"></g:formatDate></td>
                        <td><g:formatDate date="${bean.dateLastChange}" type="datetime" style="MEDIUM"></g:formatDate></td>
                        <td><f:display bean="${bean}" property="status"  displayStyle="${displayStyle?:'table'}" /></td>
                    </tr>
                </g:each>
                </tbody>
            </table>



  <div class="pagination">
      <g:paginate total="${projectCount ?: 0}" />
  </div>
</div>
</body>
</html>