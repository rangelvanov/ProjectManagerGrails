<%@ page import="com.ProjectManager.auth.User" %>

<div class="fieldcontain" >
    <label for="title">
        Title
    </label>
    <g:textField name="title" value="${project.title}"/>
</div>

<div class="fieldcontain">
    <label for="description">
        Description
    </label>
    <g:textArea name="description" value="${project.description}" />
</div>

<%--
<div class="fieldcontain">
    <label for="userId">
        Created by
    </label>
    <g:select name="userId" from="${User.getAll()}" optionKey="id" optionValue="username" value="${project.userId}"/>
</div>

--%>
<g:hiddenField name="userId" value="${project.userId}"></g:hiddenField>

<div class="fieldcontain">
    <label for="startDate">
        Start Date
    </label>
    <g:datePicker name="startDate" value="${project.startDate}"/>
</div>

<%--
<div class="fieldcontain">
    <label for="dateLastChange">
        Last Changed
    </label>
    <g:datePicker name="dateLastChange" value="${project.dateLastChange}"/>
</div>
--%>

<div class="fieldcontain">
    <label for="status">
        Status
    </label>
    <g:select name="status" from="${projectmanager.StatusP.values()}" value="${project.status}"/>
</div>
