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
    	<h1>See Users Who...</h1>
    	
   	<!-- POSTED MULTIPLE SPECIES -->
   		<hr>
    	<h2>Have posted animals of the following species:</h2>
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
			String sql = "SELECT DISTINCT species FROM animals";
			//TO-DO: exclude current user's animals from this query 
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(); 
			%>
			<p>Select species 1 :
			<select  name="species1">
			<%
			while(rs.next())
			{
				String species1 = rs.getString("species"); 
				%>
				<option value="<%=species1 %>"><%=species1 %></option>
				<%
			}
			%>
			</select>
			</p>
			
			<p>Select species 2 :
			<select name="species2">
			<%
			rs = ps.executeQuery();
			while(rs.next())
			{
				String species2 = rs.getString("species");
				%>
				<option value="<%=species2 %>"><%=species2 %></option>
				<%
			}
			%>
			</select>
			</p>
			<input type="submit" name ="submit"/>
		</form>
		<%
			String s1 = request.getParameter("species1");
			String s2 = request.getParameter("species2");
			
			if(s1 == null){
				s1 = "?";
				s2 = "?";
			}
			
		%>
		
    	<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Users that posted <%=s1 %> and <%=s2 %> animals</th>
    		</tr>
    	</thead>
    	<% 
    	sql = "SELECT DISTINCT owner FROM animals WHERE species=? "
    			+ "AND owner IN (SELECT owner FROM animals WHERE species=? ) ";
    	
    	ps = con.prepareStatement(sql);
    	ps.setString(1, s1);
    	ps.setString(2, s2);    	
    	%>
    	<tbody>
  	    	<tr>
  	    	
  	    	<% if(s1.equals(s2)){ %>
  	    	<tr>
  	    		<td>Please select two different species</td>
  	    	</tr>
  	    	<%
  	    	} else {
  	    	rs = ps.executeQuery();
  	    	while(rs.next()){
  	    	%>
  	    	<tr>
  	    		<td><%= rs.getString(1) %></td> 	    		
  	    	</tr>
    	</tbody>
    	<%}}%>
    	</table> 
    	<br>
    	
   	<!-- POSTED MOST REVIEWS -->
    	<hr>
    	<h2>Posted the most reviews:</h2>
   	   	<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Username</th>
    			<th>Number of Reviews</th>
    		</tr>
    	</thead>
    	<% 
    	/*****************************FIXED***********************************/
    	sql = "SELECT username,numReviews FROM users ORDER BY numReviews DESC";
    	ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
    	while(rs.next()){
    	%>
    	<tbody>  	    	
  	    	<tr>
  	    		<td><%= rs.getString(1) %></td>
  	    		<td><%= rs.getString(2) %></td>
  	    	</tr>
  	    	
    	</tbody>
    	<%}%>
    	</table> 
    	<br>
    	
    	
   	<!-- SHARE FAVORITE ANIMALS -->    	
    	<hr>
	     <h2>Share favorite animals:</h2>
    	 <form action="" method = "get">
    		<%  		
    		session=request.getSession();
	   	 	String PostingUser	= (String) session.getAttribute("currentUser");
    		
    		sql = "SELECT * FROM users WHERE username <> ?";
			ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, PostingUser);
			rs = ps.executeQuery(); 
			%>
			<p>Select user 1 :
			<select name="user1">
			<%
			rs.next();
			while(rs.next())
			{
				String u1 = rs.getString("username"); 
				%>
				<option value="<%=u1 %>"><%=u1 %></option>
				<%
			}
			%>
			</select>
			</p>
			<p>Select user 2 :
			<select name="user2">
			<%
			rs = ps.executeQuery();
			rs.next();
			while(rs.next())
			{
				String u2 = rs.getString("username"); 
				%>
				<option value="<%=u2 %>"><%=u2 %></option>
				<%
			}
			%>
			</select>
			</p>
			<input type="submit" name ="submit"/>
		</form>

		<%
			String user1 = request.getParameter("user1");
			String user2 = request.getParameter("user2");
			
			if(user1 == null){
				user1 = "?";
				user2 = "?";
			} 
			
			%>
			
		<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th colspan = "2" >User <%=user1 %> and <%=user2 %> share favorite animals</th>
    		</tr>
    		<tr>
    			<th>Name</th>
    			<th>Species</th>
 			</tr>
    		
    	</thead>
    	<tbody>
			<%			
			//sql to get shared favorite animals of two users
				sql = "SELECT animalName, species FROM animals WHERE animalId IN "
					+ "(SELECT f.favAnimalId FROM favoriteanimals f1 "
					+ "JOIN favoriteanimals f2 "
					+ "ON f1.favAnimalId = f2.favAnimalId "
					+ "JOIN favoriteanimals f "
					+ "ON f2.favAnimalId = f.favAnimalId "
					+ "WHERE f1.user = ? AND f2.user = ? )" ; 
			
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setString(1, user1);
				ps.setString(2, user2);
			
				rs = ps.executeQuery(); 					
		%>	
    		<tr>
	  	    	<%
				if(user1.equals(user2)){
					%>
					<tr>
						<td colspan="2">Please select two different users!</td>
					</tr>
					<%
					
				} else {
				rs = ps.executeQuery();
				while(rs.next())
				{					
					String f2 = rs.getString("animalName"); 
					String f3 = rs.getString("species"); 

					%>
					<tr>
						<td><%=f2 %></td>
						<td><%=f3 %></td>
					</tr>
					<%
				}
				}
				%>
    	</tbody>
    	</table> 
    	<br>
   	
 	<!-- HAVE NEVER POSTED A RIDIC ADORBS ANIMAL -->
    	<hr>
    	<h2>Have never posted a ‘Ridic Adorbs’ animal:</h2>
   	   	<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Username</th>
    		</tr>
    	</thead>
    	<%
    	/*********************************FIXED******************************FIXED**************************FIXED******************************/
    	sql = "SELECT DISTINCT owner FROM animals WHERE owner not in (SELECT owner FROM animals where animalId in (SELECT animalId from (SELECT animalId,category,COUNT(*) C FROM reviews GROUP BY animalId,category ) as K where (category=\"Totes Adorbs\" and C >= 2)))";
    	ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
    	while(rs.next()){
    	%>
    	<tbody>
  	    	<tr>
  	    		<td><%= rs.getString(1) %></td>
  	    	</tr>
    	</tbody>
    	<%}%>
    	</table> 
    	<br>
    	
    	
    <!-- HAVE NEVER POSTED A CRAY CRAY REVIEW -->
    	<hr>
    	<%
    	//only looks at users who have posted at least one review
    	/*sql = "SELECT DISTINCT p.postingUser FROM reviews p "
    			+ "WHERE p.postingUser NOT IN "
    			+ "(SELECT r.postingUser FROM reviews r "
    			+ "JOIN reviews e ON r.category = e.category "
    			+ "WHERE e.category = ? )";*/
    	
    	//considers users who have never posted any reviews as well.
    	sql = "SELECT username FROM users WHERE "
    		+ "userId NOT IN (SELECT DISTINCT userId FROM reviews WHERE category='Cray-Cray')";
    	
		ps = (PreparedStatement) con.prepareStatement(sql);
		//ps.setString(1, "Cray-Cray"); //for first query option
		rs = ps.executeQuery(); 
    	
    	%>
    	<h2>Have never posted a ‘Cray Cray’ review:</h2>
   	   	<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Username</th>
    		</tr>
    	</thead>
    	<tbody>
  	    	<%
				rs = ps.executeQuery();
				rs.next();
				while(rs.next())
				{
					//String f2 = rs.getString("postingUser"); //for first query option
					String f2 = rs.getString("username"); 
					%>
					<tr>
					<td value="<%=f2 %>"><%=f2 %></td>
					</tr>
					<%
				}
				%>
    	</tbody>
    	</table> 
    	<br>
    	
    <!-- ONLY POSTED ONE OR MORE REVIEWS THAT ARE ALL CRAY CRAY-->
    	<hr>
    	<h2>Have <u>only</u> posted one or more ‘Cray Cray’ review(s):</h2>
    	<%
    	//posted one or more reviews that are all cray-cray
 		sql = "SELECT DISTINCT postingUser FROM reviews WHERE "
 				+ "category='Cray-Cray' AND postingUser NOT IN ( "
 				+ "SELECT postingUser FROM reviews WHERE category<>'Cray-Cray' ) ";
		
		ps = (PreparedStatement) con.prepareStatement(sql);
		rs = ps.executeQuery();   	
    	%>
   	   	<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Username</th>
    		</tr>
    	</thead>
    	<tbody>
  	    	<%
				while(rs.next())
				{
					String f2 = rs.getString("postingUser"); 
					%>
					<tr>
					<td><%=f2 %></td>
					</tr>
					<%
				}
				%>
    	</tbody>
    	</table> 
    	<br>
    	
    <!-- HAVE POSTED AN ANIMAL AND NO ANIMALS HAVE A CRAY CRAY REVIEW-->
    	<hr>
    	<%    	
    	//all users who have posted an animal and those animals have no cray cray review
    	//select distinct owners from list of owners not on this list
    	sql = "SELECT DISTINCT owner FROM animals WHERE "
    		+ "owner NOT IN (SELECT owner FROM animals WHERE "
    		+ "animalId IN (SELECT animalId FROM reviews WHERE category='Cray-Cray') )";
    	
    	ps = (PreparedStatement) con.prepareStatement(sql);
		rs = ps.executeQuery();     	
    	%>
    	<h2>Have posted an animal(s), but no animal has received a 'Cray Cray' review:</h2>
   	   	<table class="table table-bordered table-striped table-hover" border="1" cellpadding="5">
    	<thead>
    		<tr>
    			<th>Username</th>
    		</tr>
    	</thead>
    	<tbody>
  	    	<%
				rs = ps.executeQuery();
				while(rs.next())
				{
					String f2 = rs.getString("owner"); 
					%>
					<tr>
					<td value="<%=f2 %>"><%=f2 %></td>
					</tr>
					<%
				}
				%>
    	</tbody>
    	</table> 
    	<br>
    	
    	<hr>
    	<h3>
          	<input onclick="window.location.href = 'home';" type="submit" value="Back" /> 
        </h3>
    </div>
</body>
</html>