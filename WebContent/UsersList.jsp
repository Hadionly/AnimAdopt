<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <center>
   		<h1>List of all Users</h1>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">
        	  <tr>
                <th>ID</th>
                <th>User Name</th>
                <th>Password</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
            </tr>
            <c:forEach var="user" items="${listUsers}">
                <tr>
                    <td><c:out value="${user.getUserId()}" /></td>
                    <td><c:out value="${user.getUsername()}" /></td>
                    <td><c:out value="${user.getPass()}" /></td>
                    <td><c:out value="${user.getFirst()}" /></td>
                    <td><c:out value="${user.getLast()}" /></td>
                    <td><c:out value="${user.getEmail()}" /></td>
                </tr>
            </c:forEach>        
        </table>
    </div>   
</body>
</html>