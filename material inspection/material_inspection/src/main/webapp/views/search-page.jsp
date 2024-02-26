<%@ include file="header.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="com.zettamine.mi.entities.States"%>
<html>
<head>
<link rel="stylesheet" href="/css/add.css">
<title>Search</title>
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


	<form:form action="search-lot" method="post" class="form"
		modelAttribute="searchCriteria">
		<h3>Search</h3>
		<h5 style="color: red">${error }</h5>
		<table>
			<tr>
				<td><label>Inspection Date *</label></td>
				<td><form:input type="date" path="startDate"
						required="required" /></td>
				<td>to</td>
				<td><form:input type="date" path="endDate" required="required" /></td>

			</tr>
			<tr>
				<td><label>Vendor Id</label></td>
				<td><form:input type="number" path="vendorId" /></td>

			</tr>
			<tr>
				<td><label>Plant Id</label></td>
				<td><form:input type="text" path="plantId" /></td>

			</tr>
			<tr>
				<td><label>Material Id</label></td>
				<td><form:input type="text" path="materialId" /></td>

			</tr>
			<tr>
				<td><label>Status</label></td>
				<td><form:input type="text" path="status" /></td>

			</tr>

			<tr>
				<td><button type="submit">Search</button></td>
				<td><button type="reset">Reset</button></td>
				<td><button type="button"
						onclick="window.location.href='/material-inspection/inspection'">Cancel</button></td>
			</tr>

		</table>


	</form:form>

</body>
</html>