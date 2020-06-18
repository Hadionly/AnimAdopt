<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="ISO-8859-1">
<title>Review page</title>
</head>
<body>	
					<div align="center">
    	<h1>Reviews</h1>
    	<table border="1" cellpadding="5">
	    	<c:choose>  
	        	<c:when test="${review !=null}" >  
		            <tr>
		                <th>Review poster</th>
		                <th>Category</th>
		                <th>Contents</th>
		            </tr>
		                <tr>
		                <c:if test="${reviewList != null}">
		                	<c:choose>
		                		<c:when test="${review.size() > 0}">
		                			<c:forEach items="${reviewList}" var="review" varStatus="status">
		                				<tr>
			                				<td>${reviewPosterList[status.index]}</td>
			                				<td>${reviewCategoryList[status.index]}</td>
			                				<td>${reviewContentsList[status.index]}</td>		                				
		                				</tr>
		                			</c:forEach>
		                		</c:when>
		                		<c:otherwise>
		                			<div>No Reviews posted for this animal</div>
		                		</c:otherwise>
		                	</c:choose>
		                </c:if>
		             </tr>
	         	</c:when>
	         	<c:otherwise>
	         		<div>No Reviews found for this animal</div>
	         	</c:otherwise>	
	     	</c:choose>
         </table>            
         <h3>
          	<input onclick="window.location.href = 'listalladoptions';" type="submit" value="Back to all adoptions" /> 
        	<input onclick="window.location.href = 'listmyadoptions';" type="submit" value="Back to my adoptions" /> 
         </h3>
    </div>   
</body>
</html>