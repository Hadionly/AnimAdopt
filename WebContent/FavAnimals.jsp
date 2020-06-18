<%@page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
    	<h1>My Favorite Animals</h1>

	    <%!String driverName = "com.mysql.jdbc.Driver";%>
		<%!String url = "jdbc:mysql://127.0.0.1:3306/adoptionsitedb?";%>
		<%!String user = "john";%>
		<%!String psw = "pass1234";%>
		<form action="${pageContext.request.contextPath}/editFavAnimal" method = "POST">
			<%
			Connection con = null;
			PreparedStatement ps = null;
			
			session=request.getSession();
	   	 	String PostingUser	= (String) session.getAttribute("currentUser");
	   	 	

			Class.forName(driverName);
			con = DriverManager.getConnection(url,user,psw);
			String sql = "SELECT * FROM animals WHERE owner NOT LIKE ?";
			//TO-DO: exclude current user's animals from this query 
			ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, PostingUser);
			
			ResultSet rs = ps.executeQuery(); 
			%>
			<p>Add a new favorite :
			<select name="item">
			<%
			while(rs.next())
			{
				String animal = rs.getString("animalName"); 
				Integer id = rs.getInt("animalId");
				%>
				<option value="<%=id %>"><%=animal %></option>
				<%
			}
			%>
			</select>
			</p>
			<input type="submit" name ="submit"/>
		</form>
	    <%
	    	/*TO-DO: Make add button work !!  */  	 		
	    %>
	    
	    <table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Species</th>
    			<th>Owner</th>
    			<th>Price</th>
    			<th>Birthday</th>
    			<th>Remove</th>
    		</tr>
    	</thead>
    	<tbody>
        <c:forEach var="fav" items="${listFavAnimals}">    	
    	<tr>
    		<td><c:out value="${fav.getAnimalName()}" /></td>
    		<td><c:out value="${fav.getSpecies()}" /></td>
    		<td><c:out value="${fav.getOwner()}" /></td>
    		<td><c:out value="$ ${fav.getPrice()}" /></td>
    		<td><c:out value="${fav.getBirthdate()}" /></td>
    		<td align="center">               		
            		<form method="GET" action="${pageContext.request.contextPath}/editFavAnimal">
             		<button type="submit" name="RemoveFavAnimal" value="${fav.getAnimalId()}">Remove</button>		          		
            		</form>
            </td>
    	</tr>
		</c:forEach>    	    	
    	</tbody>
    	</table> 
    	<h3>
    	   	<input onclick="window.location.href = 'favAnimals';" type="submit" value="Refresh List" />              
          	<input onclick="window.location.href = 'home';" type="submit" value="Back" /> 
        </h3>
	    </div>
</body>
</html>