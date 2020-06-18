<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
    	<h1>Add An Animal To Crate</h1>
    	
    	<table border="1" cellpadding="5">           
            <caption><h2>List of Adoptable Animals</h2></caption>
            <tr>
                <th>Name</th>
                <th>Species</th>
                <th>Price</th>
                <th>Traits</th>
                <th>Add to Crate</th>
            </tr>
            <c:forEach var="animal" items="${listAllAnimals}">
                <tr>
                    <td><c:out value="${animal.animalName}" /></td>
                    <td><c:out value="${animal.species}" /></td>
                    <td><c:out value="$ ${animal.price}" /></td>
                    <td><c:out value="${animal.getTraitList()}" /></td>
                    <td colspan="1" align="center">
                		<form method="get" action="${pageContext.request.contextPath}/addAnimalToCrate">
                		<button type="submit" name="animalId" value="${animal.getAnimalId()}">Add</button>
                		</form>
                	</td>
                </tr>
            </c:forEach>
            <tr>            
         </table>
        	
  	    <h3>
          	<input onclick="window.location.href = 'adoptionCrate';" type="submit" value="Back" /> 
        </h3>
    </div>
</body>
</html>