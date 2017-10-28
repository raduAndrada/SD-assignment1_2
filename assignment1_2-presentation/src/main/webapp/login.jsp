<html>
<head>
<title>Login</title>
<link rel="icon" type="image/ico" href="css/img/favicon.ico"/>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/font-awesome.css" />
</head>
<body class="login-body">
	<div class="container">
		<div class="alert alert-danger error text-center">${errorMsg}</div>
		<div class="row">
			<div class="col-md-4 col-offset-4">
				<div class="panel panel-info" >
					<div class="panel-heading">
						<h4 class="text-center">Login</h4>
					</div>
					<div class="panel-body">
						<form action="login" method="post">

							<div class="form-group">
								<label for="email">Email address</label> <input type="text"
									name="email" class="form-control" id="email"
									placeholder="jon.snoow@winterfel.com">

							</div>

							<div class="form-group">
								<label for="password">Password</label> <input type="password"
									name="password" class="form-control" id="password"
									placeholder="password">
							</div>

							<button type="submit" class="btn btn-success btn-block">Submit</button>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>