<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>

<body>
<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
        <ul class="nav navbar-nav navbar-right">
            <sec:ifLoggedIn>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-expanded="false"><sec:username/><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Profile</a></li>
                        <li>(<g:link controller="logout">Logout</g:link>)</li>
                    </ul>
                </li>
            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
                <li>
                    <g:link controller='login'>Login</g:link>
                </li>
            </sec:ifNotLoggedIn>
        </ul>
        <ul class="nav navbar-nav navbar-left">
            <li><g:link controller="project" action="index">Projects</g:link></li>
            <li><g:link controller="issue" action="index">Issues</g:link></li>
            <sec:access expression="hasRole('ROLE_ADMIN')">
                <li><g:link controller="comment" action="index">Comments</g:link></li>
                <li><g:link controller="WorkLog" action="index">Work Log</g:link></li>
            </sec:access>
        </ul>
    </div>
</div>
</div>
<g:layoutBody/>


<asset:javascript src="application.js"/>

</body>
</html>
