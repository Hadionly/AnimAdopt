<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
    	<h1>List All Adoptions</h1>
    	<table border="1" cellpadding="5">
            
            <caption><h2>List of Available Adoptions</h2></caption>
            <tr>
                <th>Name</th>
                <th>Species</th>
                <th>Owner</th>
                <th>Price</th>
                <th>Traits</th>
                <th>Animal Reviews</th>
                <th>Submit Review</th>
            </tr>
            <c:forEach var="animal" items="${listAllAnimals}">
                <tr>
                    <td><c:out value="${animal.animalName}" /></td>
                    <td><c:out value="${animal.species}" /></td>
                    <td><c:out value="${animal.owner}" /></td>
                    <td><c:out value="$ ${animal.price}" /></td>
                    <td><c:out value="${animal.getTraitList()}" /></td>
                    <td colspan="1" align="center">
                		<form method="post" action="${pageContext.request.contextPath}/ViewReview">
                		<button type="submit" name="ViewAnimalID" value="${animal.getAnimalId()}">View Reviews for ${animal.animalName}</button>
                		</form>
                	</td>
                	<td colspan="1" align="center">
                	<form action="${pageContext.request.contextPath}/CreateReview" method="POST">
	                    	<select name="category">
	                    		<option value="Totes Adorbs">Totes Adorbs</option>
	                    		<option value="Adorbs">Adorbs</option>
	                    		<option value="Cray">Cray</option>
	                    		<option value="Cray-Cray">Cray-Cray</option>
	                    	</select>
	                    	<input type="text" name="content">
                		
                	 	<button type="submit" name="AnimalID" value="${animal.getAnimalId()}">Review ${animal.animalName}</button>	
						</form>  
					</td>
                </tr>
            </c:forEach>
            <tr>
            
         </table>
         <h3>
          	<input onclick="window.location.href = 'home';" type="submit" value="Back" /> 
         </h3>
    </div>   
</body>
</html>