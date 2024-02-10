<jsp:include page="header.jsp"></jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page import="java.util.*"%>
<%@ page import="com.zettamine.mi.entities.Plant"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/search-list.css" />
<style type="text/css">
h3 {
	text-align: center;
}

.red-text {
	color: red;
}

.form {
	max-width: 300px;
	margin: 20px auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
<title>Approval</title>
</head>
<body>
	<h3>Inspection Actuals Comparison</h3>
	<br>
	<input type="button" value="Back"
		onclick="window.location.href='/material-inspection/inspection/pending'">
	<table class="search-results">
		<thead>
			<tr>
				<th>Characteristic Name</th>
				<th>UOM</th>
				<th>Tolerance Upper limit</th>
				<th>Tolerance Lower limit</th>
				<th>Max Measurement</th>
				<th>Min Measurement</th>

			</tr>
		</thead>
		<tbody>

			<c:forEach var="charItem" items="${characteristics}">
				<c:forEach var="actualItem" items="${inspectionActual}">
					<c:if
						test="${charItem.chId eq actualItem.materialCharacteristic.chId}">
						<tr>
							<td><c:out value="${charItem.chDesc}" /></td>
							<td><c:out value="${charItem.uom}" /></td>
							<td><c:out value="${charItem.upperLimit}" /></td>
							<td><c:out value="${charItem.lowerLimit}" /></td>
							<td
								<c:if test="${actualItem.maxMeasurement gt charItem.upperLimit}">
                                class="red-text"
                            </c:if>>
								<c:out value="${actualItem.maxMeasurement}" />
							</td>

							<td
								<c:if test="${actualItem.minMeasurement lt charItem.lowerLimit}">
                                class="red-text"
                            </c:if>>
								<c:out value="${actualItem.minMeasurement}" />
							</td>
						</tr>
					</c:if>


				</c:forEach>
			</c:forEach>


		</tbody>
	</table>


	<form:form action="add-approval" method="post" class="form"
		modelAttribute="inspectionLot">
		<h3>Approval Form</h3>

		<table>
			<tr>
				<td><form:input type="hidden" path="inspectionLotId"
						required="required" value="${inspectionLot.getInspectionLotId()}" /></td>
			</tr>
			<tr>
				<td><form:input type="hidden" path="material"
						required="required" value="${inspectionLot.material.materialId}" /></td>
			</tr>
			<tr>
				<td><form:input type="hidden" path="vendor"
						required="required" value="${inspectionLot.vendor.vendorId}" /></td>
			</tr>
			<tr>
				<td><form:input type="hidden" path="plant"
						required="required" value="${inspectionLot.plant.plantId}" /></td>
			</tr>
			<tr>
				<td><form:input type="hidden" path="inspCreatedDate"
						required="required" value="${inspectionLot.inspCreatedDate}" /></td>
			</tr>
			<tr>
				<td><form:input type="hidden" path="inspStartDate"
						required="required" value="${inspectionLot.inspStartDate}" /></td>
			</tr>
			


			<tr>
				<td><label>Remarks</label></td>
				<td><form:textarea path="inspectionRemarks" rows="4" cols="20" /></td>

			</tr>
			<tr>
				<td><label>Result</label></td>
				<td><form:select path="inspectionResult" style="width: 170px; padding: 5px;" >
						<option selected disabled>Select Character</option>
						<form:option value="Pass">Pass</form:option>
						<form:option value="Fail">Fail</form:option>
					</form:select></td>
			</tr>


			<tr>
				<td><button type="submit">Add</button></td>
				<td><button type="reset">Reset</button></td>
				<td><button type="button"
						onclick="window.location.href='/material-inspection/inspection/pending'">Cancel</button></td>
			</tr>

		</table>


	</form:form>


</body>
</html>