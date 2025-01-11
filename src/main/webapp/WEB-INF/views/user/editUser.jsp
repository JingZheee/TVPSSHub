<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/general.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/activity.css' />">
</head>
<body>
    <div>
        <%@ include file="../navbar.jsp" %>
    </div>
    <div class="content">
        <div class="header">
            <div>
                <a href="/TVPSSHub/user/userList"><i class="fa-solid fa-angle-left"></i> Back</a>
                <h2>Edit User</h2>
            </div>
        </div>
        <div class="form-container">
            <form action="/TVPSSHub/user/editUser/${user.id}" method="post">
                <!-- Hidden field to pass the user ID -->
                <input type="hidden" name="id" value="${user.id}">

                <section class="info-section">
                    <div class="form-group">
                        <label for="fullName">Full Name:</label>
                        <input type="text" id="fullName" name="fullName" value="${user.fullName}" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="${user.email}" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" value="${user.password}" required>
                    </div>
                    <div class="form-group">
                        <label for="checkPassword">Confirm Password:</label>
                        <input type="password" id="checkPassword" name="checkPassword" value="${user.password}" required>
                    </div>
                    <div class="form-group">
                        <label for="dateOfBirth">Date of Birth:</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" value="${user.dateOfBirth}" required>
                    </div>
                    <div class="form-group">
                        <label for="school">School:</label>
                        <input type="text" id="school" name="school" value="${user.school}" required>
                    </div>
                    <div class="form-group">
                        <label for="identityCardNumber">Identity Card Number:</label>
                        <input type="text" id="identityCardNumber" name="identityCardNumber" value="${user.identityCardNumber}" required>
                    </div>
                </section>

                <!-- Submit Button -->
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" onclick="return confirmSubmission()">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
    <script>
        function confirmSubmission() {
            return confirm("Are you sure you want to save changes to this user?");
        }
    </script>
</body>
</html>
