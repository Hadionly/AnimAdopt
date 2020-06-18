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
    	<h1>Search By Trait</h1>
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
			String sql = "SELECT * FROM traits";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(); 
			%>
			<p>Select Trait :
			<select name="item">
			<%
			rs.next();
			while(rs.next())
			{
				String traitName = rs.getString("trait"); 
				%>
				<option value="<%=traitName %>"><%=traitName %></option>
				<%
			}
			%>
			</select>
			</p>
			<input type="submit" name ="submit"/>
		</form>
		
		<%  String traitName = request.getParameter("item");
		    sql = "SELECT * FROM traits WHERE trait = ?";
	        ps = (PreparedStatement) con.prepareStatement(sql);
	        ps.setString(1, traitName);
	         
	        ResultSet resultSet = ps.executeQuery();
	        
	        int tid = 0;
	        String trait = "";
	        
	        if(resultSet.next()){
	        	tid = resultSet.getInt("traitId");  
	        	trait = resultSet.getString("trait");
	        }
	        
	        sql = "SELECT * FROM animals WHERE traitId1 = ? OR traitId2 = ? OR traitID3 = ?";
	        ps = (PreparedStatement) con.prepareStatement(sql);
	        ps.setInt(1, tid);
	        ps.setInt(2, tid);
	        ps.setInt(3, tid);
	        
	        ResultSet r = ps.executeQuery();
	       %>
	        <table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
	        
	        <tr>
        	    <th>Name</th>
        		<th>Species</th>
        		<th>Owner</th>
        		<th>Price</th>
        		<th>Birthday</th> 
        		<th>Has Trait</th>   		
	        </tr>
	        
	       <% 
	    	while(r.next()){
	        %>
	        	<tr>
	        		<td><%=r.getString("animalName") %></td>
	        		<td><%=r.getString("species") %></td>
	        		<td><%=r.getString("owner") %></td>
	        		<td>$ <%=r.getString("price") %></td>
	        		<td><%=r.getString("birthdate") %></td>
	        		<td><%=trait %></td>
	        	</tr>
	        <%
	        }
	        %>

	       </table>
	       
		 <h3>
          	<input onclick="window.location.href = 'home';" type="submit" value="Back" /> 
         </h3>
    	</div>
</body>
</html>