<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8" />
<title>USERS</title>
<link rel="icon" type="image/ico" href="css/img/favicon.ico"/>
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
		String admin = (String) session.getAttribute("admin");
		String admName = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("admin"))
					admName = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>
	<div class="container" id="menuId"></div>
	<div class="container center">
		<div class="alert alert-success">${successMsg}</div>
		<div class="alert alert-danger error text-center">${errorMsg}</div>
		<div class="col-md-8 col-md-offset-3">
			<a href="/assignment1_2/users?mode=${mode}" title="create user"
				class="btn btn-md btn-default"><i class="fa fa-plus"> <!-- add some users -->
			</i></a>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><span>Email</span></th>
						<th><span>First name</span></th>
						<th><span>Last name</span></th>
						<th><span>Address</span></th>
						<th><span>Phone number</span></th>
						<th><span>Series Id</span></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="usr">
						<tr>
							<td><a href='/assignment1_2/users?usrEmail=${usr.email}'><span>${usr.email}</span></a></td>
							<td><span>${usr.fNm}</span></td>
							<td><span>${usr.lNm}</span></td>
							<td><span>${usr.addr}</span></td>
							<td><span>${usr.tel}</span></td>
							<td><span>${usr.serId}</span></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>