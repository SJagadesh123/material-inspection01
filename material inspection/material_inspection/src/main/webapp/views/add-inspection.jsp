<%@ include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="com.zettamine.mi.entities.States"%>
<html>
<head>
<link rel="stylesheet" href="/css/add.css">
<title>Add Inspection Lot</title>
<style>
h3 {
	text-align: center;
}

.form {
	max-width: 600px;
	margin: 20px auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
</head>


<body>

	<form:form action="add-inspection" method="post" class="form"
		modelAttribute="inspection">
		<h3>Add Inspection Lot</h3>
			<h5 style="color:red">${error }</h5>
		<table>
			<tr>
				<td><label> Material *</label></td>
				<td><form:select  path="material" required="required">
						<option value="" selected disabled>Select Material</option>
						<c:forEach var="mat" items="${material}">
							<option value="${mat.materialId}">${mat.description}</option>
						</c:forEach>

					</form:select></td>
			</tr>
			
			<tr>
				<td><label> Vendor *</label></td>
				<td><form:select  path="vendor" required="required">
						<option value="" selected disabled>Select Vendor</option>
						<c:forEach var="vendor" items="${vendor}">
							<option value="${vendor.vendorId}">${vendor.vendorName}</option>
						</c:forEach>

					</form:select></td>
			</tr>
			<tr>
				<td><label> Plant *</label></td>
				<td><form:select  path="plant" required="required">
						<option value="" selected disabled>Select Plant</option>
						<c:forEach var="plant" items="${plant}">
							<option value="${plant.plantId}">${plant.plantId}</option>
						</c:forEach>

					</form:select></td>
			</tr>
			<tr>
				<td><label>Created On *</label></td>
				<td><form:input type="date" path="inspCreatedDate"
						required="required" /></td>

			</tr>
			
			<tr>
				<td><label>Started On *</label></td>
				<td><form:input type="date" path="inspStartDate"
						required="required" /></td>

			</tr>
			

			<tr>
				<td><button type="submit">Add</button></td>
				<td><button type="reset">Reset</button></td>
				<td><button type="button"
						onclick="window.location.href='/material-inspection/inspection'">Cancel</button></td>
			</tr>

		</table>


	</form:form>


</body>
</html>