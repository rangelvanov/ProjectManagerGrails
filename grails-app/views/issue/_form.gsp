<%@ page import="com.ProjectManager.auth.User" %>
<%@ page import="projectmanager.*" %>

<div class="fieldcontain">
    <label for="name">
        Name
    </label>
    <g:textField name="name" value="${issue.name}"/>
</div>

<div class="fieldcontain">
    <label for="type">
        Type
    </label>
    <g:select name="type" from="${IssueType.values()}"  value="${issue.type}"/>
</div>

<div class="fieldcontain">
    <label for="priority">
        Priority
    </label>
    <g:select name="priority" from="${IssuePriority.values()}" value="${issue.priority}"/>
</div>

<div class="fieldcontain">
    <label for="description">
        Description
    </label>
    <g:textArea name="description" value="${issue.description}"/>
</div>

<div class="fieldcontain">
    <label for="status">
        Status
    </label>
    <g:select name="status" from="${StatusT.values()}" value="${issue.status}"/>
</div>

<div class="fieldcontain">
    <label for="assigneeId">
        Assignee
    </label>
    <g:select name="assigneeId" from="${User.getAll()}" optionKey="id" optionValue="username"
              value="${issue.assigneeId}"/>
</div>


<g:hiddenField name="ownerId" value="${issue.ownerId}"></g:hiddenField>
<g:hiddenField name="created" value="${issue.created}"></g:hiddenField>
<g:hiddenField name="updated" value="${issue.updated}"></g:hiddenField>
<div class="fieldcontain">
    <label for="project">
        Project
    </label>

    <g:select name="project" from="${Project.getAll()}" optionKey="id" optionValue="name"
              value="${issue.project != null ? issue.project.id : issue.project}"></g:select>
</div>