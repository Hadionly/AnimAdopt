<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Pet Adoption Site</title>
</head>
<body>
	<div align="center">
	<h1>The Most Expensive Animal is...</h1>
	
	<%
	ArrayList<String> MostExp = (ArrayList<String>)request.getAttribute("MostExp");
	%>
			
	<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Species</th>
    			<th>Owner</th>
    			<th>Price</th>
    			<th>Birthday</th>
    			<th>Traits</th>
    		</tr>	
    	</thead>
    	<tbody>   	
    	    <tr>
	       		<td>${MostExp[0]}</td>
	       		<td>${MostExp[1]}</td>
	       		<td>${MostExp[2]}</td>
	       		<td>$ ${MostExp[3]}</td>
	       		<td>${MostExp[4]}</td>
	       		<td>${MostExp[5]}</td>
	       	</tr>
    	</tbody>
    	</table> 
    	<h3>
          	<input onclick="window.location.href = 'home';" type="submit" value="Back" /> 
        </h3>				
	</div>
</body>
</html>