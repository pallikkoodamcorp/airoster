<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script language = "javascript" type = "text/javascript">
 //document.getElementById("customRecomId").style.display = "none";
 var x = document.getElementById("customRecomId");
 x.style.display = "none";
 function displayDiv(){
	 document.getElementById("customRecomId").style.display = "block";
 }
</script>
</head>
<body>
	<h1>Import</h1>

	<form:form method="POST" action="/uploadFile"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>Select a file to upload</td>
				<td><input type="file" name="file" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>

	<table>
		<tr>
			<%-- 			<td><c:if test="${result!=null}">${result}</c:if></td> --%>
			<td>${result}</td>
		</tr>
	</table>

	<form:form method="POST" action="/submitUserRecommendation"
		modelAttribute="userForm">
		<table border="1">
			<c:if test="${recommendations!=null && !recommendations.isEmpty()}">
				<tr>
					<th>District PID</th>
					<th>System Recommended</th>
					<th>Acceptance</th>
				</tr>
			</c:if>

			<c:forEach items="${recommendations}" var="recomm">
				<tr>

					<td>${recomm.pid}</td>
					<td>${recomm.systemSuggested}</td>
					<td><form:radiobutton path="acceptance" value="S" />Accepted <form:radiobutton
							path="acceptance" value="I" />Ignored <form:radiobutton
							path="acceptance" value="U" onclick="displayDiv()" />Custom</td>
					<form:hidden path="recommendationId" value="${recomm.id}" />
				</tr>
			</c:forEach>
			<div id="customRecomId" style="display: none">
					<tr>
						<td>Customer Recommended Pattern</td>
						<td><form:input path="customerRecommended" /></td>
						<td></td>
					</tr>
				</div>
			<c:if test="${recommendations!=null && !recommendations.isEmpty()}">
				<tr>
					<td></td>
					<td><input type="submit" value="Submit" /></td>
					<td></td>
				</tr>
			</c:if>
		</table>
			
	</form:form>

	<!-- 	<table> -->
	<!-- 		<tr> -->
	<!-- 			<td>Please select the Import File</td> -->
	<!-- 			<td><input type="submit" value="Submit" /></td> -->
	<!-- 		</tr> -->
	<!-- 	</table> -->

	<%-- 	<c:forEach items="${recommendations}" var="recomm"> --%>
	<%-- 		${recomm.pid} --%>
	<%-- 	</c:forEach> --%>
</body>
</html>