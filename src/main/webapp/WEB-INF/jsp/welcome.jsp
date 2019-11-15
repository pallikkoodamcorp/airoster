<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <style>
    /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
    .row.content {height: 1500px}

    /* Set gray background color and 100% height */
    .sidenav {
      background-color: #e6f5ff;
      height: 100%;
    }

    /* Set black background color, white text and some padding */
    footer {
      background-color: #b3d9ff;
      color: white;
      padding: 15px;
    }

    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height: auto;}
    }
  </style>

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


<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>AI - Imports</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="#section1">Imports</a></li>
      </ul><br>
    </div>

    <div class="col-sm-9">

    <h2>Class Roster <span class="label label-default">AI</span></h2>
             <br><br>
			<form:form method="POST" action="/uploadFile"
		        enctype="multipart/form-data">
		        <div class="form-group">
			        <label for="select">Select a file to import</label> <span class="glyphicon glyphicon-import"></span>
				    <input class="form-control" type="file" name="file" />
				</div>
				 <div class="form-group">
			        <button type="submit" class="btn btn-default">Submit</button>
                 </div>
	        </form:form>

            <c:choose>
	            <c:when test= "${result=='success'}">
	                <div class="alert alert-success alert-dismissible fade in">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <strong>Success!</strong> ${result}
                    </div>
	            </c:when>
	            <c:when test= "${result=='success'}">
                    <div class="alert alert-danger alert-dismissible fade in">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <strong>Failed!</strong> ${result}
                      </div>
                </c:when>
	        </c:choose>

	<!-- <table>
		<tr>
			<%--<td><c:if test="${result!=null}">${result}</c:if></td> --%>
			<td>${result}</td>
		</tr>
	</table> -->

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
    </div>
  </div>
</div>


</body>
</html>