<%@ page import="com.ProjectManager.auth.User" %>
<%@ page import="projectmanager.*" %>

<div class="fieldcontain" >
    <label for="title">
        Title
    </label>
    <g:textField name="title" value="${task.title}"/>
</div>

<div class="fieldcontain">
    <label for="description">
        Description
    </label>
    <g:textArea name="description" value="${task.description}"/>
</div>

<%--
<div class="fieldcontain">
    <label for="userId">
        Created by
    </label>
    <g:select name="userId" from="${User.getAll()}" optionKey="id" optionValue="username" value="${task.userId}"/>
</div>
--%>

<div class="fieldcontain">
    <label for="responsibleUserId">
       Responsible User
    </label>
    <g:select name="responsibleUserId" from="${User.getAll()}" optionKey="id" optionValue="username" value="${task.responsibleUserId}"/>
</div>

<div class="fieldcontain">
    <label for="startDate">
       Start Date
    </label>
    <g:datePicker name="startDate" value="${task.startDate}"/>
</div>
<%--
<div class="fieldcontain">
    <label for="dateLastChange">
        Last Changed
    </label>
    <g:datePicker name="dateLastChange" value="${task.startDate}"/>
</div>
--%>

<g:hiddenField name="userId" value="${task.userId}"></g:hiddenField>

<div class="fieldcontain">
    <label for="status">
        Status
    </label>
    <g:select name="status" from="${StatusT.values()}" value="${task.status}"/>
</div>

<div class="fieldcontain">
    <label for="project">
        Project
    </label>

    <g:select name="project" from="${Project.getAll()}" optionKey="id" optionValue="title" value="${task.project != null ? task.project.id : task.project  }"></g:select>
</div>