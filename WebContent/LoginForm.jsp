<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
        <h1>Login Page</h1>
        <form action="loginCheck" method="POST">
        	<table border="1" cellpadding="5">
           <tr>
                <td colspan="2" align="center">
                    <label for="username"><b>Username</b></label>
                    <input type="text" placeholder="Enter username" name="username" required>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <label for="password"><b>Password</b></label>
                    <input type="text" placeholder="Enter password" name="password" required>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Login" />
                    
                </td>
            </tr>
        </table>
        </form>       
        <h3><a href="LoginOrRegisterPage.jsp">Back</a></h3>                    
    </div>   
</body>
</html>