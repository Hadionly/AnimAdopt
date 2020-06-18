<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
    	<h3>Welcome to. . .</h3>
    	<h1>Pet Adoption Site!</h1>
        <h3>Please login or register: </h3>
        <table border="1" cellpadding="5">           
            <tr>
                <td colspan="2" align="center">
                    <input onclick="window.location.href = 'login';" type="submit" value="Login" />
                    
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input onclick="window.location.href = 'register';" type="submit" value="Register" />     
                </td>
            </tr>
        </table>
    </div>   
</body>
</html>