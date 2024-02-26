
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="com.zettamine.mi.entities.States"%>
<html>
<head>
<link rel="stylesheet" href="/css/add.css">
<title>Add Vendor</title>
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

	<c:choose>
		<c:when test="${vendorById eq null}">
			<form:form action="add-vendor" method="post" class="form"
				modelAttribute="vendor">
				<h3>Add Vendor</h3>
				<h5 style="color: red">${error }</h5>
				<h5 style="color: green">${msg}</h5>
				<table>
					<tr>
						<td><label>Vendor Name *</label></td>
						<td><form:input type="text" path="vendorName"
								required="required" /></td>

						<td><form:errors style="display:inline; color:red;" path="vendorName"></form:errors></td>
					</tr>
					<tr>
						<td><label>Vendor email *</label></td>
						<td><form:input type="text" path="email" required="required" /></td>

						<td><form:errors style="display:inline; color:red;" path="email"></form:errors></td>
					</tr>


					<tr>
						<td><label> State *</label></td>
						<td><form:select path="state" required="required">
								<option selected disabled>Select State</option>
								<c:forEach var="state" items="${States.values()}">
									<option value="${state.getStateName()}">${state.getStateName()}</option>
								</c:forEach>

							</form:select></td>

						<td><form:errors style="display:inline; color:red;" path="state"></form:errors></td>
					</tr>
					<tr>
						<td><label>City *</label></td>
						<td><form:input type="text" required="required" path="city" /></td>

						<td><form:errors style="display:inline; color:red;" path="city"></form:errors></td>
					</tr>

					<tr>
						<td><button type="submit">Add</button></td>
						<td><button type="reset">Reset</button></td>
						<td><button type="button"
								onclick="window.location.href='/material-inspection/vendor'">Cancel</button></td>
					</tr>

				</table>


			</form:form>
		</c:when>
		<c:otherwise>
			<form:form action="add-vendor" method="post" class="form"
				modelAttribute="vendorById">
				<h3>Update Vendor</h3>
					
				<table>
					<tr>
						<td><form:input type="hidden" path="vendorId"
								required="required" value="${vendorById.getVendorId()}" /></td>
					</tr>
					<tr>
						<td><label>Vendor Name *</label></td>
						<td><form:input type="text" path="vendorName"
								required="required" value="${vendorById.getVendorName()}" /></td>
						<td><form:errors style="display:inline; color:red;" path="vendorName"></form:errors></td>

					</tr>
					<tr>
						<td><label>Vendor email *</label></td>
						<td><form:input type="text" path="email" required="required"
								value="${vendorById.getEmail()}" /></td>
						<td><form:errors style="display:inline; color:red;" path="email"></form:errors></td>


					</tr>


					<tr>
						<td><label> State *</label></td>
						<td><form:select path="state" required="required">
								<option selected>${vendorById.getState()}</option>
								<c:forEach var="state" items="${States.values()}">
									<option value="${state.getStateName()}">${state.getStateName()}</option>
								</c:forEach>

							</form:select></td>
						<td><form:errors style="display:inline; color:red;" path="state"></form:errors></td>

					</tr>
					<tr>
						<td><label>City *</label></td>
						<td><form:input type="text" required="required" path="city"
								value="${vendorById.getCity()}" /></td>
						<td><form:errors style="display:inline; color:red;" path="city"></form:errors></td>

					</tr>

					<tr>
						<td><button type="submit">Add</button></td>
						<td><button type="reset">Reset</button></td>
						<td><button type="button"
								onclick="window.location.href='/material-inspection/vendor/search'">Cancel</button></td>
					</tr>

				</table>


			</form:form>
		</c:otherwise>
	</c:choose>

</body>
</html>