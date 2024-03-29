<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="com.zettamine.mi.entities.States"%>
<html>
<head>
<link rel="stylesheet" href="/css/add.css">
<title>Add Actual</title>
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

	<form:form action="add-actuals" method="post" class="form"
		modelAttribute="actual">
		<h3>Add Actual</h3>

		<table>
			<tr>

				<td><form:input type="hidden" path="inspectionLot"
						required="required" value="${inspectionLot.inspectionLotId}" /></td>



				<td><h4>${material.description}</h4></td>
																
			</tr>
			
			<tr>
				<td><label>Characteristics *</label></td>
				<td><form:select path="materialCharacteristic"
						required="required">
						<option selected disabled>Select Character</option>
						<c:forEach var="insp" items="${characteristics}">
								<option value="${insp.chId }">${insp.chDesc } in ${insp.uom}</option>
						</c:forEach>
					</form:select></td>
			</tr>



			<tr>
				<td><label>Max Measurement:</label></td>
				<td><form:input type="text" path="maxMeasurement" /></td>
			</tr>
			<tr>
				<td><label>Min Measurement:</label></td>
				<td><form:input type="text" path="minMeasurement" /></td>
			</tr>



			<tr>
				<td><button type="submit">Add</button></td>
				<td><button type="reset">Reset</button></td>
				<td><button type="button"
						onclick="window.location.href='/material-inspection/inspection/process-isp'">Cancel</button></td>
			</tr>

		</table>


	</form:form>


</body>
</html>