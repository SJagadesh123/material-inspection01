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
	<h3>Details</h3>
	<br>
	<input type="button" value="Back"
		onclick="window.location.href='/material-inspection/inspection/results'">

<div class="container">
	<table class="inspection-table">
		<tr>
			<td>Lot Id :</td>
			<td>${inspection.inspectionLotId}</td>

		</tr>
		<tr>
			<td>Material :</td>
			<td>${inspection.material.description}</td>
		</tr>
		<tr>
			<td>Vendor :</td>
			<td>${inspection.vendor.vendorName}</td>
		</tr>
		<tr>
			<td>Plant :</td>
			<td>${inspection.plant.plantName}</td>
		</tr>
		<tr>
			<td>Created on :</td>
			<td>${inspection.inspCreatedDate}</td>
		</tr>
		<tr>
			<td>Start Date :</td>
			<td>${inspection.inspStartDate}</td>
		</tr>
		<tr>
			<td>End Date :</td>
			<td>${inspection.inspEndDate}</td>
		</tr>
		<tr>
			<td>Result :</td>
			<td>${inspection.inspectionResult}</td>
		</tr>
		<tr>
			<td>Remarks :</td>
			<td>${inspection.inspectionRemarks}</td>
		</tr>
		<tr>
			<td>User :</td>
			<td>${inspection.user.userName}</td>
		</tr>
	</table>
</div>

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





</body>
</html>