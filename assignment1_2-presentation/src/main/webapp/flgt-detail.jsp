<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8" />
<title>FLIGHT DETAILS</title>
<link rel="icon" type="image/ico" href="css/img/favicon.ico" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/font-awesome.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type='text/javascript' src='js/knockout.js'></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">

<script>
	$(document).ready(function() {
		$('#menuId').load('menu.html');
	});
</script>
</head>
<body class="rc-body">
	<div class="container" id="menuId"></div>
	<div class="container center">
		<div>
			<div class="alert alert-success">${successMsg}</div>
		</div>
		<div class="col-md-8 col-md-offset-3">
			<c:choose>

				<c:when test="${mode == 'create'}">
					<form role="form" data-bind="submit: updateCtList">
						<div class="form-group float-label-control">
							<label><span>Airplane type</span></label> <input type="text"
								class="form-control" placeholder="nice" title="airplane type"
								data-bind="value: aplnTp" name="aplnTp" />
						</div>
						<div class="alert alert-danger">${invalidArplTp}</div>
						<div class="form-group float-label-control">
							<label><span>Departure city</span></label> <input type="text"
								class="form-control" placeholder="Kansas" title="departure city"
								data-bind="value: dprtCt" name="dprtCt" />
						</div>
						<div class="alert alert-danger">${invalidDprtCt}</div>
						<label><span>Arrival time</span></label>
						<div class='input-group date' id='arrTmId'>
							<input type='text' class="form-control"
								data-bind="datepicker: arrTm" /> <span
								class="input-group-addon datetimepicker-span"> <i
								class="fa fa-calendar" aria-hidden="true"></i>
							</span>
						</div>
						<div class="alert alert-danger">${invalidArrTm}</div>
						<div class="form-group float-label-control">
							<label><span>Number of seats</span></label> <input type="number"
								class="form-control" placeholder="20" title="number of Seats"
								min="0" data-bind="value: nbSt" name="nbSt" />
						</div>
						<div class="alert alert-danger">${invalidNbSt}</div>

						<button id="save-btn" type="submit" class="btn btn-success btn-md">
							<i class="fa fa-save"></i> <span>Save</span>
						</button>
						<a id="cancel-btn" href="/assignment1_2/flights"
							class="btn btn-info btn-md pull-right"> <i class="fa fa-undo"></i>
							<span>Cancel</span>
						</a>
					</form>

					<h4><b>Cities</b></h4>
					<select multiple="multiple" class="form-control"
						data-bind="options:cities, selectedOptions: selectedCities, visible: cities().length > 0">
					</select>

					<form data-bind="submit: addCity">
						City: <input type="text"
							data-bind='value: newCtNm, valueUpdate: "afterkeydown"'
							placeholder="Berlin" />
						<button class="btn-md btn btn-default" type="submit">
							<i class="fa fa-plus-square" aria-hidden="true"></i>
						</button>
					</form>
					<div>
						<button class="btn btn-md btn-danger"
							data-bind="click: removeSelected, enable: selectedCities().length >0, visible: selectedCities().length > 0">
							<span>Remove selected cities</span><i class="fa fa-minus-square-o" aria-hidden="true"></i>
							</button>
					</div>



				</c:when>


				<c:when test="${mode == 'update'}">
					<form class="margin-bottom" method="post"
						action="/assignment1_2/flights-users?deleteFlgtId=${flgt.id}">
						<button id="delete-btn" type="submit"
							class="btn btn-danger btn-md pull-right">
							<i class="fa fa-remove"></i> <span>Delete</span>
						</button>
						<br />
					</form>
					<form role="form"
						action="/assignment1_2/flights-users?flgtId=${flgt.id}"
						method="post">
						<div class="form-group float-label-control">
							<label><span>Flight number</span></label> <input type="text"
								class="form-control" placeholder="nice" title="airplane type"
								name="flgtNb" value="${flgt.flgtNb}" readonly="readonly"
								disabled="disabled" />
						</div>
						<div class="form-group float-label-control">
							<label><span>Airplane type</span></label> <input type="text"
								class="form-control" placeholder="nice" title="airplane type"
								name="aplnTp" value="${flgt.aplnTp}" />
						</div>
						<div class="alert alert-danger">${invalidAplnTp}</div>
						<div class="form-group float-label-control">
							<label><span>Departure city</span></label> <input type="text"
								class="form-control" placeholder="Kansas" title="departure city"
								name="dprtCt" value="${flgt.dprtCt}" />
						</div>
						<div class="alert alert-danger">${invalidDprtCt}</div>
						<label><span>Arrival time</span></label>
						<div class='input-group date' id='arrTmId'>

							<input type='text' class="form-control" name="arrTm" /> <span
								class="input-group-addon datetimepicker-span"> <i
								class="fa fa-calendar" aria-hidden="true"></i>
							</span>
						</div>
						<div class="alert alert-danger">${invalidArrTm}</div>
						<div class="form-group float-label-control">
							<label><span>Number of seats</span></label> <input type="number"
								class="form-control" placeholder="20" title="number of Seats"
								name="nbSt" value="${flgt.nbSt}" />
						</div>
						<div class="alert alert-danger">${invalidNbSt}</div>
						<button id="save-btn" type="submit" class="btn btn-success btn-md">
							<i class="fa fa-save"></i> <span>Save</span>
						</button>
						<a id="cancel-btn" href="/assignment1_2/flights-users"
							class="btn btn-info btn-md pull-right"> <i class="fa fa-undo"></i>
							<span>Cancel</span>
						</a>
					</form>
				</c:when>
			</c:choose>
		</div>
	</div>
	<footer>
		<script type='text/javascript' src='js/CtListVM.js'></script>
		<script type="text/javascript">
			$(function() {
				$('#arrTmId').datetimepicker();
			});
		</script>
	</footer>

</body>
</html>