<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard</title>
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
			<img src="<c:url value='/resources/images/profile-pic.png' />"
				alt="${client.fullName}">
			<div>
				<h1>Welcome Back, ${client.fullName}!</h1>
			</div>
		</div>

		<!-- Ongoing Programs -->
		<div class="container">
			<h2>Ongoing Programs</h2>
			<div class="programs">
				<c:forEach var="program" items="${programs}">
					<div class="program-card">
						<h3>${program.title}</h3>
						<p>Status: ${program.status}</p>
						<p>Date: ${program.date}</p>
						<a href="/program/details/${program.id}">View Details</a>
					</div>
				</c:forEach>
			</div>
		</div>

		<!-- Conditional School Information -->
		<div class="container">
			<h2>School List</h2>
			<div class="programs">
				<c:forEach var="school" items="${schools}">
					<div class="school-card">
						<h3>${school.name}</h3>
						<p>Address: ${school.district}</p>
						<p>Contact: ${school.district}</p>
					</div>
				</c:forEach>
			</div>

		</div>

		<!-- Resources Section -->
		<div class="container">
			<h2>Resource Request</h2>

			<div class="programs">
				<c:forEach var="resource" items="${resources}">
					<div class="resource-card">
						<h3>${resource.request}</h3>
						<p>Type: ${resource.type}</p>
						<p>Availability: ${resource.description}</p>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
