<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
     <form action="AdoptionServlet" method="POST">
     	<h1>Adoption Form</h1>
        <table border="1" cellpadding="5">    
            <tr>
                <td>
                    <label for="animalName"><b>Name: </b></label>
                </td>
                <td align="right">
                    <input type="text" placeholder="Enter name" 
                    name="animalName" required
                    />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="species"><b>Species: </b></label>
                </td>
                <td align="right">   
                    <input type="text" placeholder="Enter species" 
                    name="species" required 
                    />
                </td>
            </tr>
            
            <tr>
                <td>
                    <label for="Birthday"><b>Birthday: </b></label>
                </td>
                <td align="right">
                    <input type="date" name="birthday" placeholder="dd/mm/yyyy"
					   required />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="Price"><b>Price: </b></label>
                </td>   
                <td align="right"> 
                    <input type="text" placeholder="Enter price"
                     name="price" required 
                    />
                </td>
            </tr>
            <tr>
                <td>
                    <label for="Traits"><b>Traits: </b></label>
                </td>
                <td align="center">
                    <input type="text" placeholder="separeted by (,)" 
                    name="traits" required>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Register" />
                    
                </td>
            </tr>
        </table>
     </form>  
     <h3><a href="HomePage.jsp">Back</a></h3>      
     </div>     
</body>
</html>