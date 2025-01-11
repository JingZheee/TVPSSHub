<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Programs</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/general.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/css/activity.css' />">
</head>
<body>
	<div>
		<%@ include file="../navbar.jsp"%>
	</div>
	<div class="content">
		<div class="header">
			<h1>Programs</h1>
			<c:if test="${sessionScope.loggedInClient.role == 2}">
				<button>
					<a href="/TVPSSHub/activity/addActivity">Add Program</a>
				</button>
			</c:if>

		</div>
		<div class="container">

			<form action="/TVPSSHub/activity/filterActivities" method="get">
				<div class="filters">
					<div class="input-wrapper">
						<i class="fa-solid fa-magnifying-glass search-icon"></i> <input
							type="text" name="searchKeyword"
							placeholder="Organization Name / Activity Name">
					</div>
					<div>
						<select name="location">
							<option value="">All</option>
							<option value="Batu Pahat">Batu Pahat</option>
							<option value="Johor Bahru">Johor Bahru</option>
							<option value="Kluang">Kluang</option>
							<option value="Kota Tinggi">Kota Tinggi</option>
							<option value="Kulai">Kulai</option>
							<option value="Mersing">Mersing</option>
							<option value="Muar">Muar</option>
							<option value="Pontian">Pontian</option>
							<option value="Segamat">Segamat</option>
							<option value="Tangkak">Tangkak</option>
						</select>
					</div>
					<div>
						<button type="submit">Filter</button>
					</div>
				</div>
			</form>

			<div class="programs">
				<!-- Loop through the activities and dynamically populate the cards -->
				<c:forEach var="activity" items="${activities}">
					<div class="program-card">
						<img src="<c:url value='/resources/images/activity.png' />"
							alt="${activity.title}">
						<div class="details">
							<h3>${activity.title}</h3>
							<p>Status: ${activity.status}</p>
							<p>${activity.date}</p>
							<div class="center-btn">
								<a href="/TVPSSHub/activity/activityDetails/${activity.id}"
									class="view-btn">View</a>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
