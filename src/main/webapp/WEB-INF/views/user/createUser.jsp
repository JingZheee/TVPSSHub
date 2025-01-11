<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add User</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/general.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/activity.css' />">
</head>
<body>
	<div>
		<%@ include file="../navbar.jsp"%>
	</div>
	<div class="content">
		<div class="header">
			<div>
				<a href="/TVPSSHub/user/userList"><i class="fa-solid fa-angle-left">Back</i></a>
				<h2>Add User</h2>
			</div>
		</div>
		<c:if test="${not empty error}">
			<div class="error-message">
				<p>${error}</p>
			</div>
		</c:if>
		<div class="form-container">
			<form action="/TVPSSHub/user/createUser" method="POST">
				<section class="info-section">
					<div class="form-group">
						<label for="fullName">Full Name:</label>
						<input type="text" id="fullName" name="fullName" required>
					</div>
					<div class="form-group">
						<label for="email">Email:</label>
						<input type="email" id="email" name="email" required>
					</div>
					<div class="form-group">
						<label for="password">Password:</label>
						<input type="password" id="password" name="password" required>
					</div>
					<div class="form-group">
						<label for="checkPassword">Confirm Password:</label>
						<input type="password" id="checkPassword" name="checkPassword" required>
					</div>
					<div class="form-group">
						<label for="dateOfBirth">Date of Birth:</label>
						<input type="date" id="dateOfBirth" name="dateOfBirth" required>
					</div>
					<div class="form-group">
						<label for="school">School:</label>
						<input type="text" id="school" name="school" required>
					</div>
					<div class="form-group">
						<label for="identityCardNumber">Identity Card Number:</label>
						<input type="text" id="identityCardNumber" name="identityCardNumber" required>
					</div>
				</section>

				<!-- Submit Button -->
				<div class="form-actions">
					<button type="submit" class="btn btn-primary" onclick="return confirmSubmission()">Add User</button>
				</div>
			</form>
		</div>
	</div>
	<script>
		function confirmSubmission() {
			return confirm("Are you sure you want to add this user?");
		}
	</script>
</body>
</html>