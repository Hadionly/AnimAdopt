<%@page import="java.sql.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.lang.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Pet Adoption Site</title>
</head>
<body>
	<div align="center">
	    <h1>My Favorite Breeders</h1>
	    <%!String driverName = "com.mysql.jdbc.Driver";%>
		<%!String url = "jdbc:mysql://127.0.0.1:3306/adoptionsitedb?";%>
		<%!String user = "john";%>
		<%!String psw = "pass1234";%>
		<form action="${pageContext.request.contextPath}/editFavBreeder" method = "POST">
			<%
			Connection con = null;
			PreparedStatement ps = null;
			
			session=request.getSession();
	   	 	String PostingUser	= (String) session.getAttribute("currentUser");
	   	 	
	       // List<FavoriteBreeders> listFavBreeders = FavoriteBreedersDAO.listUsersFavBreeders(PostingUser);
				
	   	 	Class.forName(driverName);
			con = DriverManager.getConnection(url,user,psw);
			String sql = "SELECT * FROM users WHERE username NOT LIKE ?";	
			
			ps = (PreparedStatement) con.prepareStatement(sql);
	        ps.setString(1, PostingUser);
	        			
			ResultSet rs = ps.executeQuery(); 
			%>
			<p>Add a new favorite :
			<select name="item">
			<%
			rs.next();
			while(rs.next())
			{
				String u = rs.getString("username"); 
				%>
				<option value="<%=u %>"><%=u %></option>
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
    			<th>Username</th>
    			<th>Remove</th>
    		</tr>
    	</thead>
    	<tbody>
        <c:forEach var="fav" items="${listFavBreeders}">    	
    	<tr>
    		<td><c:out value="${fav.getFavBreeder()}" /></td>
    		<td align="center">               		
					<!-- TO-DO: Make remove button work (using removeFavBreeder method) -->
            		<form method="GET" action="${pageContext.request.contextPath}/editFavBreeder">
               		<button type="submit" name="RemoveFavBreeder" value="${fav.getFavBreeder()}">Remove </button>               		
               		</form>
            </td>
    	</tr>
		</c:forEach>    	    	
    	</tbody>
    	</table> 
    	<h3>
    		<input onclick="window.location.href = 'favBreeders';" type="submit" value="Refresh List" />              
          	<input onclick="window.location.href = 'home';" type="submit" value="Back" /> 
        </h3>
	    	
    </div>
</body>
</html>