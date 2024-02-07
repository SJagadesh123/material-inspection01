<jsp:include page="header.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.zettamine.mi.entities.Plant" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/search-list.css"/>
<style type="text/css">
	h3{
		text-align: center;
	}
</style>
<title>Plant List</title>
</head>
<body>
<h3>Plant List</h3>
	<br>
    <input type="button" value="Back" onclick="window.location.href='/material-inspection/plant'">
	 <table class="search-results">
    <thead>
      <tr>
        <th>Plant Name</th>
        <th>City</th>
        <th>State</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
		
			<c:forEach var="plant" items="${allPlants}">

			<tr>
				
				<td>${plant.getPlantName()}</td>
				<td>${plant.getCity()}</td>
				<td>${plant.getState()}</td>
				<td class="action-links">
				<a class="view-project" href="/material-inspection/plant/edit/id=${plant.getPlantId()}">Edit</a>
            <a class="add-project" href="/material-inspection/plant/delete/id=${plant.getPlantId()}">Delete</a>
            
			</tr>
			</c:forEach>	
		
    </tbody>
  </table>
  
  
</body>
</html>