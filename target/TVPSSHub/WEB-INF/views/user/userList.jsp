<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User List</title>
<link rel="stylesheet" href="<c:url value='/resources/css/general.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/activity.css' />">

<script>
    // Function for confirmation before deletion
    function confirmDelete(event) {
        var confirmAction = confirm("Are you sure you want to delete?");
        if (!confirmAction) {
            event.preventDefault(); // Prevents the form submission if "Cancel" is clicked
        }
    }
</script>

</head>
<body>
	<div>
		<%@ include file="../navbar.jsp"%>
	</div>
	<div class="content">
		<div class="header">
			 <h1>User List for ${schoolName}</h1>
			<c:if test="${sessionScope.loggedInClient.role == 2}">
				<button>
					<a href="/TVPSSHub/user/createUser">Add User</a>
				</button>
			</c:if>
		</div>
		<div class="feedback-container">
		<c:if test="${not empty userList}">
			<table class="feedback-table">
				<thead>
					<tr>
						<th>Name</th>
						<th>School</th>
						<th>Date of Birth</th>
						<th>Email</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
				    <c:forEach var="user" items="${userList}">
				        <tr>
				            <td>${user.fullName}</td>
				            <td>${user.school}</td>
				            <td>${user.dateOfBirth}</td>
				            <td>${user.email}</td>
				            <td>
				                <button>
				                	<a href="<c:url value='/user/editUser/${user.id}' />">Edit</a>
				                </button> 
				                <button onclick="confirmDelete(event)">
				                	<a href="<c:url value='/user/deleteUser/${user.id}' />">Delete</a>
				                </button>
				            </td>
				        </tr>
				    </c:forEach>
				</tbody>

			</table>
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
			<c:if test="${empty userList}">
            <p>No users found for ${schoolName}.</p>
        </c:if>
		</div>
	</div>
</body>
</html>
