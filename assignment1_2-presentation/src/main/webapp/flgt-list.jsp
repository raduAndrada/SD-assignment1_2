<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<meta charset="UTF-8" />
<title>FLIGHTS</title>
<link rel="icon" type="image/ico" href="css/img/favicon.ico" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/font-awesome.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type='text/javascript' src='js/knockout.js'></script>
<script>
	$(document).ready(function() {
		$('#menuId').load('menu.html');
	});
</script>
</head>
<body class="rc-body">
	<%
		//allow access only if session exists
		String usrName = null;
		String admName = null;
		String editable = null;

		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user"))
					usrName = cookie.getValue();
				if (cookie.getName().equals("admin"))
					admName = cookie.getValue();
				editable = "edit";
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>
	<div class="container" id="menuId"></div>
	<div class="container center">
		<div>
			<div class="alert alert-success">${successMsg}</div>
		</div>
		<div class="alert alert-danger error text-center">${errorMsg}</div>
		<a href="/assignment1_2/flights-users?mode=${mode}"
			title="add user" class="btn btn-md btn-link"><i
			class="fa fa-plus"> <!-- add some users -->
		</i></a>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th><span>Flight number</span></th>
					<th><span>Airplane type</span></th>
					<th><span>Departure city</span></th>
					<th><span>Arrival time</span></th>
					<th><span>Number of seats</span></th>
					<c:if test="<%=admName != null%>">
						<th></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="flgt">
					<tr>
						<td><a href="/assignment1_2/flights?flgtId=${flgt.id}"
							title="FlgtNb"> <span>${flgt.flgtNb}</span>
						</a></td>
						<td><span>${flgt.aplnTp}</span></td>
						<td><span>${flgt.dprtCt}</span></td>
						<td><span>${flgt.arrTm}</span></td>
						<td><span>${flgt.nbSt}</span></td>
						<c:if test="<%=admName != null%>">
							<td><a class="btn btn-md btn-warning" href="/assignment1_2/flights-users?flgtId=${flgt.id}"><i class="fa fa-pencil" aria-hidden="true"></i><span>Edit</span></a></td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


</body>
</html>