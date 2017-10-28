<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8" />
<title>CITIES FOR FLIGHT</title>
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
		document.getElementById('userList-id').style.display = "none";
	});
</script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
	$(document)
			.ready(
					function() {

						//Sending A Default Location
						var Latitude = 6.0535185;
						var Longitude = 80.22097729999996;
						var cities = new Array();
						<c:forEach items="${list}" var="city" varStatus="status">
						cityDetails = new Object();
						cityDetails.lat = "${city.lat}";
						cityDetails.lngt = "${city.lngt}";
						cityDetails.nm = "${city.nm}";
						getTimeUsingLatLng(cityDetails.lat, cityDetails.lngt,
								cityDetails.nm);
						</c:forEach>

						function getTimeUsingLatLng(lat, lng, nm) {
							var times_Stamp = (Math
									.round((new Date().getTime()) / 1000))
									.toString();
							$
									.ajax(
											{
												url : "https://maps.googleapis.com/maps/api/timezone/json?location="
														+ lat
														+ ","
														+ lng
														+ "&timestamp="
														+ times_Stamp,
												cache : false,
												type : "POST",
												async : false,
											})
									.done(
											function(response) {

												if (response.timeZoneId != null) {
													var Cur_Date = new Date();
													var UTC = Cur_Date
															.getTime()
															+ (Cur_Date
																	.getTimezoneOffset() * 60000);
													var Loc_Date = new Date(
															UTC
																	+ (1000 * response.rawOffset)
																	+ (1000 * response.dstOffset));
													$("#" + nm)
															.html(
																	Loc_Date
																			.toLocaleString());
												}
											});
						}
					});
</script>
</head>
<body class="rc-body">
	<div class="container" id="menuId"></div>
	<div class="container center">
		<div class="rc-filter-container"></div>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th><span>Name</span></th>
					<th><span>Latitude</span></th>
					<th><span>Longitude</span></th>
					<th><span>Local time</span></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="ct">
					<tr>
						<td><span>${ct.nm}</span></td>
						<td><span>${ct.lat}</span></td>
						<td><span>${ct.lngt}</span></td>
						<td><span id="${ct.nm}"></span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</body>
</html>