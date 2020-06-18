<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pet Adoption Site</title>
</head>
<body>
    <div align="center">
    	<h1>Home Page</h1>
        <h2>Login successful!</h2>
        <table border="1" cellpadding="5">
        	<tr>
                <td colspan="2" align="center">
                    <input onclick="window.location.href = 'adoptionCrate';" type="submit" value="My Adoption Crate" />              
            </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input onclick="window.location.href = 'adoptionform';" type="submit" value="Place an Animal Up For Adoption" />              
                </td>
            </tr>
            <tr>
                <td align="center">
                    <input onclick="window.location.href = 'listalladoptions';" type="submit" value="List All Adoptions" />              
                </td>

                <td align="center">
                    <input onclick="window.location.href = 'listmyadoptions';" type="submit" value="List My Adoptions" />              
                </td>
            </tr>
            <tr>
                <td align="center">
                    <input onclick="window.location.href = 'favBreeders';" type="submit" value="Favorite Breeders" />              
                </td>

                <td align="center">
                    <input onclick="window.location.href = 'favAnimals';" type="submit" value="Favorite Animals" />              
                </td>
            </tr>
            <tr>
                <td align="center">
                    <input onclick="window.location.href = 'seeUsersWho';" type="submit" value="See Users Who..." />              
                </td>
                <td align="center">
                    <input onclick="window.location.href = 'onlyAdorbs';" type="submit" value="Only Adorbs Animals" />              
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input onclick="window.location.href = 'mostExpensive';" type="submit" value="Most Expensive Animal" />              
            	</td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input onclick="window.location.href = 'searchform';" type="submit" value="Search by Trait" />              
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input onclick="window.location.href = 'logout';" type="submit" value="Logout" />              
                </td>
            </tr>
        </table>
    </div>   
</body>
</html>