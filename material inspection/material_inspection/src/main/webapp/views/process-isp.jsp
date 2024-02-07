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
<title>Process Inspection</title>
</head>
<body>
<h3>Lots Eligible for Inspection</h3>
	<br>
    <input type="button" value="Back" onclick="window.location.href='/material-inspection/inspection'">
	 <table class="search-results">
    <thead>
      <tr>
        <th>Lot Id</th>
        <th>Material Name</th>
        <th>Vendor Name</th>
        <th>Plant Name</th>
        <th>Start Date</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
		
		
			<c:forEach var="insp" items="${inspection}">
				
			<tr>
				
				<td>${insp.getInspectionLotId()}</td>
				<td>${insp.getMaterial().getDescription()}</td>
				<td>${insp.getVendor().getVendorName()}</td>
				<td>${insp.getPlant().getPlantName()}</td>
				<td>${insp.getInspStartDate()}</td>
				<td class="action-links">
				<a class="view-project" href="/material-inspection/inspection/add-actuals${insp.getInspectionLotId()}">Add Actuals</a>
				<c:if test="${insp.getInspectionActual().size() != 0}">
            	<a class="add-project" href="/material-inspection/inspection/view-actuals${insp.getInspectionLotId()}">View Actuals</a>
            	
           		</c:if>
			</tr>
			</c:forEach>	
		
    </tbody>
  </table>
  
  
</body>
</html>