<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- New line below to use the JSP Standard Tag Library -->
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<h2>Edit Art</h2>
<div class="container">
    <%--@elvariable id="editBook" type="javax"--%>
    <form:form action="/update/${editBook.id}" method="put" modelAttribute="editBook">
        <div class="form-row">
            <form:errors path="title" class="error"/>
            <form:label for="title" path="title">Title:</form:label>
            <form:input type="text" path="title" class="form-control"/>
        </div>

        <div class="form-row">
            <form:errors path="author" class="error"/>
            <form:label for="author" path="author">Author:</form:label>
            <form:input type="text" path="author" class="form-control"/>
        </div>

        <div class="form-row">
            <form:errors path="thoughts" class="error"/>
            <form:label for="thoughts" path="thoughts">Thoughts:</form:label>
            <form:textarea path="thoughts" class="form-control"/>
        </div>
        <button class="btn btn-primary">Submit</button>

    </form:form>
</div>