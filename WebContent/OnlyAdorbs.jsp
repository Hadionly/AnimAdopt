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
    	<h1>Animals that have only 'Adorbs' or only 'Totes Adorbs' Reviews</h1>
    	
 	    <%!String driverName = "com.mysql.jdbc.Driver";%>
		<%!String url = "jdbc:mysql://127.0.0.1:3306/adoptionsitedb?";%>
		<%!String user = "john";%>
		<%!String psw = "pass1234";%>
		<form action="" method = "get">
			<%
			Connection con = null;
			PreparedStatement ps = null;

			Class.forName(driverName);
			con = DriverManager.getConnection(url,user,psw);
			
			String sql = "SELECT DISTINCT owner FROM animals " 
			+ "WHERE animalId IN "
			+ "( SELECT animalId FROM reviews WHERE category='Adorbs' OR category='Totes Adorbs' ) "
			+ "AND owner <> ? ";
			
			session=request.getSession();
	   	 	String currentUser	= (String) session.getAttribute("currentUser");
			
			ps = con.prepareStatement(sql);
			ps.setString(1, currentUser);
			ResultSet rs = ps.executeQuery(); 
			%>
			<p>Select user :
			<select name="item">
			<%
			while(rs.next())
			{
				String user = rs.getString("owner"); 
				%>
				<option value="<%=user %>"><%=user %></option>
				<%
			}
			%>
			</select>
			</p>
			<input type="submit" name ="submit"/>
		</form>   	
    	<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Owner</th>
    			<th>Name</th>
    			<th>Species</th>
    			<th>Review Category</th>
    			<th>Review Contents</th>
    		</tr>
    	</thead>
    	<tbody>
  	    	<%  
  	    	String user = request.getParameter("item");

  	    	//select all the fitting reviews for all animals belonging to "user"
  	    	sql = "SELECT * FROM reviews" 
  	    	+ " WHERE animalId IN (SELECT animalId FROM animals WHERE owner = ?) "
  	    	+ " AND animalId NOT IN "
  	  			+ "( SELECT animalId FROM reviews WHERE category='Cray' OR category='Cray-Cray' ) ";
  	   
			ps = con.prepareStatement(sql);
			ps.setString(1, user);
			rs = ps.executeQuery();  
  	    	%>
  	    	
	   		<% 
	    	while(rs.next()){
	        %>
	        	<tr>
	        		<td><%=user %></td>
	        		<td>
	        		<%int anId = rs.getInt("animalId"); 
	        			sql = "SELECT * FROM animals WHERE animalId = ?";
	        			ps = con.prepareStatement(sql);
	        			ps.setInt(1, anId);
	        		
	        			ResultSet rs2 = ps.executeQuery();
	        			String name = "name";
	        			String species = "species";
	        			
	        			while(rs2.next()){
	        				name = rs2.getString("animalName");
	        				species = rs2.getString("species");
	        			}	        		
	        		%>
	        		<%=name %>
	        		</td>
	        		<td><%=species %></td>
	        		<td><%=rs.getString("category") %></td>
	        		<td><%=rs.getString("contents") %></td>	        		
	        	</tr>
	        <%
	        }
	        %> 	    	
    	</tbody>
    	</table> 
    	   	
        <h3>
          	<input onclick="window.location.href = 'home';" type="submit" value="Back" /> 
        </h3>	
    </div>
</body>
</html>