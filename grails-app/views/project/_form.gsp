<%@ page import="com.ProjectManager.auth.User" %>

<div class="fieldcontain">
    <label for="name">
        Name
    </label>
    <g:textField name="name" value="${project.name}"/>
</div>

<div class="fieldcontain">
    <label for="key">
        Key
    </label>
    <g:textField name="key" value="${project.key}"/>
</div>

<div class="fieldcontain">
    <label for="ownerId">
        Owner
    </label>
    <g:select name="ownerId" from="${User.getAll()}" optionKey="id" optionValue="username" value="${project.ownerId}"/>
</div>

<div class="fieldcontain">
    <label for="type">
        Type
    </label>
    <g:select name="type" from="${projectmanager.ProjectType.values()}" value="${project.type}"/>
</div>

<div class="fieldcontain">
    <label for="category">
        Category
    </label>
    <g:select name="category" from="${projectmanager.Category.values()}" value="${project.category}"/>
</div>
