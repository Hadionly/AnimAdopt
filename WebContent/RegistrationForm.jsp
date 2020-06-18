<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pet Adoption Site</title>
</head>
<body>
     <div align="center">
     <form action="ControlServlet" method="POST">
     	<h1>New User Registration</h1>
		
        <table border="1" cellpadding="5">    
            <tr>
                <td>
                    <label for="username"><b>Username: </b></label>
                </td>
                <td align="right">
                    <input type="text" placeholder="Enter username" 
                    name="username" required
                    />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="password"><b>Password: </b></label>
                </td>
                <td align="right">   
                    <input type="text" placeholder="Enter password" 
                    name="password" required 
                    />
                </td>
            </tr>
            
            <tr>
                <td>
                    <label for="firstName"><b>First Name: </b></label>
                </td>
                <td align="right">
                    <input type="text" placeholder="Enter first name"
                     name="firstname" required 
                    />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="lastName"><b>Last Name: </b></label>
                </td>   
                <td align="right"> 
                    <input type="text" placeholder="Enter last name"
                     name="lastname" required 
                    />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="email"><b>Email: </b></label>
                </td>
                <td align="right">
                    <input id="email" type="text" align="left" placeholder="Enter email"
                     name="email" required
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Register" />
                    
                </td>
            </tr>
        </table>
     </form>  
     <h3><a href="LoginOrRegisterPage.jsp">Back</a></h3>
     
           
     </div>  
</body>
</html>