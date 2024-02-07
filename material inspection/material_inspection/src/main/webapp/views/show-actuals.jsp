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
<title>Inspection Actuals</title>
</head>
<body>
<h3>Inspection Actuals</h3>
	<br>
    <input type="button" value="Back" onclick="window.location.href='/material-inspection/inspection/process-isp'">
	 <table class="search-results">
    <thead>
      <tr>
        <th>Characteristic Name</th>
        <th>UOM</th>
        <th>Max Measurement</th>
        <th>Min Measurement</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
		
			<c:forEach var="inspActuals" items="${inspActuals}">

			<tr>	
				<td>${chDesc}</td>		
				<td>${uom}</td>
				<td>${inspActuals.maxMeasurement}</td>
				<td>${inspActuals.minMeasurement}</td>
				<td class="action-links">
				<%-- /material-inspection/material/edit/id=${material.getMaterialId()} --%>
				<a  href="">Edit Characteristics</a>
<%--             <a  href="/material-inspection/material/add-char/id=${material.getMaterialId()}">Add Characteristics</a>
 --%>            
			</tr>
			</c:forEach>	
		
    </tbody>
  </table>
  
  
</body>
</html>