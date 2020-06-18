<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
    	<h1>Congratulations! Adoption successful!</h1>
   		<% 
	  		String anIdString = request.getParameter("anId"); 	
   		%>     	
  	    <h3>
          	<input onclick="window.location.href = 'home';" type="submit" value="Return Home" /> 
        </h3>
    </div>
</body>
</html>