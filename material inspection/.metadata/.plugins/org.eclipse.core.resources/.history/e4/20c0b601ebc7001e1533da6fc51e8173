<jsp:include page="header.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.zettamine.mi.entities.Vendor" %>

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
<title>Results List</title>
</head>
<body>
<h3>Results List</h3>
	<br>
    <input type="button" value="Back" onclick="window.location.href='/material-inspection/inspection'">
	 <table class="search-results">
    <thead>
      <tr>
        <th>Lot Id</th>
        <th>Material</th>
        <th>Vendor</th>
        <th>Plant</th>
        <th>Created on</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Result</th>
        <th>Remarks</th>
        <th>User</th>
        
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
		
			<c:forEach var="inspection" items="${inspection}">

			<tr>
				
				<td>${inspection.inspectionLotId}</td>
				<td>${inspection.material.description}</td>
				<td>${inspection.vendor.vendorName}</td>
				<td>${inspection.plant.plantName}</td>
				<td>${inspection.inspCreatedDate}</td>
				<td>${inspection.inspStartDate}</td>
				<td>${inspection.inspEndDate}</td>
				<td>${inspection.inspectionResult}</td>
				<td>${inspection.inspectionRemarks}</td>
				<td>${inspection.user.userName}</td>
				
				<td class="action-links">
				<a class="view-project" href="/material-inspection/vendor/edit/id=${inspection.inspectionLotId}">view</a>
<%--             <a class="add-project" href="/material-inspection/vendor/show-char/id=${vendor.getVendorId()}">Delete</a>
 --%>            
			</tr>
			</c:forEach>	
		
    </tbody>
  </table>
  
  
</body>
</html>