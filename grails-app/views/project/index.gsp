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

        <fieldset class="buttons">
            <g:link action="create"><g:message code="default.new.label" args="[entityName]"/></g:link>
        </fieldset>
    </div>
</sec:access>
<div class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <div class="row">
        <g:each in="${myProjects}" status="i" var="project">
            <div class='col-sm-3'>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class='panel-title'>
                            <f:display bean="${project}" property="id"/>
                            <f:display bean="${project}" property="name"/>
                        </h4>
                    </div>

                    <div class="panel-body">

                        <div class="fieldcontain">
                            <label>
                                Type:
                            </label>
                            <g:fieldValue field="type" bean="${project}"></g:fieldValue>
                        </div>

                        <div class="fieldcontain">
                            <label>
                                Category:
                            </label>
                            <g:fieldValue field="category" bean="${project}"></g:fieldValue>
                        </div>
                    </div>

                    <div class='panel-footer'>
                        <g:link class="btn btn-default" method="GET" resource="${project}">
                            View
                        </g:link>
                    </div>
                </div>
            </div>
        </g:each>

    </div>

</div>
</body>
</html>