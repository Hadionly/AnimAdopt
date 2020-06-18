<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.PreparedStatement" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pet Adoption Site</title>
</head>

<body>
    <div align="center">
    	<h1>My Adoptions</h1>
    <table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Species</th>
    			<th>Owner</th>
    			<th>Price</th>
    			<th>Birthday</th>
    			<th>Traits</th>
    			<th>Reviews</th>
    		</tr>
    	</thead>
    	<tbody>
        <c:forEach var="animal" items="${listAllAnimals}">    	
    	<tr>
    		<td><c:out value="${animal.getAnimalName()}" /></td>
    		<td><c:out value="${animal.getSpecies()}" /></td>
    		<td><c:out value="${animal.getOwner()}" /></td>
    		<td><c:out value="${animal.getPrice()}" /></td>
    		<td><c:out value="${animal.getBirthdate()}" /></td> 
    		<td><c:out value="${animal.getTraitList()}" /></td>
    			
    		<td >               		
             		<form method="post" action="${pageContext.request.contextPath}/ViewReview">
             		<button type="submit" name="ViewAnimalID" value="${animal.getAnimalId()}">View Reviews for ${animal.getAnimalName()}</button>		
             		</form>		
            </td>
    	</tr>
		</c:forEach>    	    	
    	</tbody>
    	</table> 
    	<h3>
          	<input onclick="window.location.href = 'home';" type="submit" value="Back" /> 
         </h3>
    	</div>
</body>
</html>