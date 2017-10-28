<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8" />
<title>USER</title>
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
	<div class="container center col-md-6">
		<div>
			<div class="alert alert-success">${successMsg}</div>
		</div>
		<div class="alert alert-danger error text-center">${errorMsg}</div>
		<c:choose>
			<c:when test="${mode == 'create'}">
				<form role="form" action="users" method="post">
					<div class="form-group float-label-control">
						<label><span>Email</span></label> <input type="email"
							class="form-control" placeholder="jonSnow@winterfell.com"
							title="email" name="email" />
						<!--TODO <input type="text" class="form-control"
								readonly="readonly" disabled="disabled" />-->
					</div>
					<div class="alert alert-danger">${invalidEmail}</div>
					<div class="form-group float-label-control">
						<label><span>First name</span></label> <input type="text"
							class="form-control" placeholder="Snow" title="first name"
							name="fNm" />
					</div>
					<div class="alert alert-danger">${invalidFNm}</div>
					<div class="form-group float-label-control">
						<label><span>Last name</span></label> <input type="text"
							class="form-control" placeholder="Jon" title="last name"
							name="lNm" />
					</div>
					<div class="alert alert-danger">${invalidLNm}</div>
					<div class="form-group float-label-control">
						<label><span>Address</span></label> <input type="text"
							class="form-control" placeholder="Cluj-Napoca, Baritiu, 29"
							title="address" name="addr" />
					</div>
					<div class="alert alert-danger">${invalidAddr}</div>
					<div class="form-group float-label-control">
						<label><span>Phone number</span></label> <input type="text"
							class="form-control" placeholder="07274677212"
							title="phone number" name="tel" />
					</div>
					<div class="alert alert-danger">${invalidTel}</div>
					<div class="form-group float-label-control">
						<label><span>Series id</span></label> <input type="text"
							class="form-control" placeholder="HD36271" title="series id"
							name="serId" />
					</div>
					<div class="alert alert-danger">${invalidSerId}</div>
					<div class="form-group float-label-control">
						<label class="form-check-label">
						
						
						<input type="checkbox"
							class="form-check-input form-control check-box-big " title="administrator" name="adm" />
							Administrator
						</label> 	
							
					</div>
					<button id="save-btn" type="submit" class="btn btn-success btn-md">
						<i class="fa fa-save"></i> <span>Save</span>
					</button>
					<a id="cancel-btn" href="/assignment1_2/users"
						class="btn btn-info btn-md pull-right"> <i class="fa fa-undo"></i>
						<span>Cancel</span>
					</a>
				</form>
			</c:when>


			<c:when test="${mode == 'update'}">
				<form class="margin-bottom" method="post"
					action="/assignment1_2/users?deleteUsrEmail=${usr.email}">
					<button id="delete-btn" type="submit"
						class="btn btn-danger btn-md pull-right">
						<i class="fa fa-remove"></i> <span>Delete</span>
					</button>
					<br />
				</form>
				<form role="form"
					action="/assignment1_2/users?usrEmail=${usr.email}" method="post">
					<div class="form-group float-label-control">
						<label><span>Email</span></label> <input type="text"
							class="form-control" value="${usr.email}" readonly="readonly"
							disabled="disabled" />
					</div>
					<div class="form-group float-label-control">
						<label><span>First name</span></label> <input type="text"
							class="form-control" placeholder="Snow" title="first name"
							name="fNm" value="${usr.fNm}" />
					</div>
					<div class="alert alert-danger">${invalidFNm}</div>
					<div class="form-group float-label-control">
						<label><span>Last name</span></label> <input type="text"
							class="form-control" placeholder="Jon" title="last name"
							name="lNm" value="${usr.lNm}" />
					</div>
					<div class="alert alert-danger">${invalidLNm}</div>
					<div class="form-group float-label-control">
						<label><span>Address</span></label> <input type="text"
							class="form-control" placeholder="Cluj-Napoca, Baritiu, 29"
							title="address" name="addr" value="${usr.addr}" />
					</div>
					<div class="alert alert-danger">${invalidAddr}</div>
					<div class="form-group float-label-control">
						<label><span>Phone number</span></label> <input type="text"
							class="form-control" placeholder="07274677212"
							title="phone number" name="tel" value="${usr.tel}" />
					</div>
					<div class="alert alert-danger">${invalidTel}</div>
					<div class="form-group float-label-control">
						<label><span>Series id</span></label> <input type="text"
							class="form-control" placeholder="HD627121" title="series id"
							name="serId" value="${usr.serId}" />
					</div>
					<div class="alert alert-danger">${invalidSerId}</div>
					<button id="save-btn" type="submit" class="btn btn-success btn-md">
						<i class="fa fa-save"></i> <span>Save</span>
					</button>
					<a id="cancel-btn" href="/assignment1_2/users"
						class="btn btn-info btn-md pull-right"> <i class="fa fa-undo"></i>
						<span>Cancel</span>
					</a>
				</form>
			</c:when>
		</c:choose>
	</div>

</body>
</html>