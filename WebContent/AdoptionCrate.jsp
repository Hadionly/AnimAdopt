<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
    	<h1>My Adoption Crate</h1>
    	
   	  	<input onclick="window.location.href = 'addToCrate';" type="submit" value="Add a New Animal to Crate" />
    	<br> <br>
    	
   	    <table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Species</th>
    			<th>Price</th>
    			<th>Traits</th>
    			<th colspan="2" >Options</th>
    		</tr>
    	</thead>
    	<tbody>
        <c:forEach var="animal" items="${listCrateAnimals}">    	
    	<tr>
    		<td><c:out value="${animal.getAnimalName()}" /></td>
    		<td><c:out value="${animal.getSpecies()}" /></td>
    		<td><c:out value="${animal.getPrice()}" /></td>
    		<td><c:out value="${animal.getTraitList()}" /></td>

    		<td >               		
           		<form method="get" action="${pageContext.request.contextPath}/adoptAnimal">
           		<button type="submit" name="animalId" value="${animal.getAnimalId()}">Adopt</button>		
           		</form>		
            </td>
    			
    		<td >               		
           		<form method="get" action="${pageContext.request.contextPath}/removeFromCrate">          		
           		<button type="submit" name="animalId" value="${animal.getAnimalId()}">Remove</button>		
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