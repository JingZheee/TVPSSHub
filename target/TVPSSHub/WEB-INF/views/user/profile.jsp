<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link
      rel="stylesheet"
      href="<c:url value='/resources/css/general.css' />"
    />
    <link rel="stylesheet" href="<c:url value='/resources/css/activity.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/profile.css' />">
</head>
<body>
    <div>
        <%@ include file="../navbar.jsp"%>
    </div>

    <div class="content">
		<div class="header">
			<h1>Profile</h1>
				<button>
                <a href="/TVPSSHub/user/updateProfile">Update Profile</a>
				</button>
		</div>

        <div class="profile-container">
            <div class="profile-header">
                <img src="<c:url value='/resources/images/profile-pic.png' />" alt="Profile Picture">
                <h2>${client.fullName}</h2>
            </div>

            <div class="profile-info">
                <div class="profile-info-container">
                    <div class="info-item">
                        <label>Email:</label>
                        <div>${client.email}</div>
                    </div>
                    <div class="info-item">
                        <label>Date of Birth:</label>
                        <div>${client.dateOfBirth}</div>
                    </div>
                    <div class="info-item">
                        <label>Identity Card Number:</label>
                        <div>${client.identityCardNumber}</div>
                    </div>
                    
			<c:if test="${sessionScope.loggedInClient.role != 1}">
                    <div class="info-item">
                        <label>School:</label>
                        <div>${client.school}</div>
                    </div>
                   </c:if>
                    <div class="info-item">
                        <label>Role:</label>
                        <div>
                            <c:choose>
                                <c:when test="${client.role == 1}">Admin</c:when>
                                <c:when test="${client.role == 2}">Teacher</c:when>
                                <c:otherwise>Student</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
