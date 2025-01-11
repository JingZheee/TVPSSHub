<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Feedback List</title>
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
			<div class="back"><a onclick="history.back()"><i
					class="fa-solid fa-angle-left">Back</i></a>
				<h2>Feedback</h2>
			</div>
		</div>
		<div class="feedback-container">
			<c:if test="${not empty feedbackList}">
				<table class="feedback-table">
					<thead>
						<tr>
							<th>Feedback</th>
							<th>Rating</th>
							<th>Author</th>
							<th>Date</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${feedbackList}" var="feedback">
							<tr>
								<td>${feedback.feedbackText}</td>
								<td>${feedback.rating}</td>
								<td>${feedback.userId}</td>
								<td>${feedback.date}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

			<c:if test="${empty feedbackList}">
				<p>No feedback available.</p>
			</c:if>

			<c:if test="${not empty feedbackList}">
				<div class="pagination">
					<c:if test="${currentPage > 1}">
						<button onclick="window.location.href='?page=${currentPage - 1}'">Previous</button>
					</c:if>

					<c:forEach begin="1" end="${totalPages}" var="page">
						<button class="${page == currentPage ? 'active' : ''}"
							onclick="window.location.href='?page=${page}'">${page}</button>
					</c:forEach>

					<c:if test="${currentPage < totalPages}">
						<button onclick="window.location.href='?page=${currentPage + 1}'">Next</button>
					</c:if>
				</div>
			</c:if>

		</div>
	</div>

</body>
</html>
