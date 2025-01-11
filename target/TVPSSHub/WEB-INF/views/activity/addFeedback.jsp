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

<script>
	function confirmFeedbackSubmit() {
		return confirm("Are you sure you want to submit this feedback?");
	}
</script>

</head>
<body>
	<div>
		<%@ include file="../navbar.jsp"%>
	</div>
	<div class="content">
		<div class="header">
			<div class="back">
				<a onclick="history.back()"><i class="fa-solid fa-angle-left">Back</i></a>
				<h2>Add Feedback</h2>
			</div>
		</div>
		<div class="form-container">

			<form action="/TVPSSHub/activity/addFeedback" method="post"
				onsubmit="return confirmFeedbackSubmit()">

				<div>
					<label for="feedbackText">Your Feedback:</label>
					<textarea id="feedbackText" name="feedbackText" rows="4" required></textarea>
				</div>

				<div>
					<label for="rating">Rating:</label> <select id="rating"
						name="rating" required>
						<option value="">Select Rating</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
				</div>
				<input type="hidden" name="activityId" value="${activity.id}" /> <input
					type="hidden" name="userId" value="${user.id}" /> <input
					type="hidden" name="date" value="${currentDate}" /> Activity ID:
				<c:out value="${activity.id}" />
				<br> User ID:
				<c:out value="${user.id}" />
				<br> Date:
				<c:out value="${currentDate}" />
				<br>


				<div>
					<button type="submit">Submit Feedback</button>
				</div>
			</form>



		</div>
	</div>

</body>
</html>
