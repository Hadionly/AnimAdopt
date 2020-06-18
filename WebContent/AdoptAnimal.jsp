<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
    	<h1>Confirm Adoption</h1>   	
   		<% 
	  		String anIdString = request.getParameter("animalId"); 	
	  		
	  		Object name = request.getAttribute("name");
	  		Object species = request.getAttribute("species");
	  		Object price = request.getAttribute("price");
   		%>
    	
   	    <table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Species</th>
    			<th>Price</th>
    		</tr>
    	</thead>
    	<tbody>	
    	<tr>
    		<td><%=name %></td>
    		<td><%=species %></td>
    		<td>$ <%=price %></td>
    	</tr>       	
    	</tbody>
    	</table>
    	
  	    <h3> </h3>		
  	    
			<form action="${pageContext.request.contextPath}/congrats" method="get">
          		<input type="hidden" name="anId" value="<%=anIdString%>"/>
          		<input type="submit" value="Confirm Adoption"/>
        	</form>
        	          	
      		<input onclick="window.location.href = 'adoptionCrate';" type="submit" value="Cancel" /> 
        	
    </div>
</body>
</html>