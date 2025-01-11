<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Update Profile</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/general.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/css/activity.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/css/profile.css' />">

<script>
        function confirmUpdate() {
            return confirm("Are you sure you want to edit your profile?");
        }

        document.addEventListener('DOMContentLoaded', function() {
            const schoolInput = document.getElementById("school");
            const schoolOptions = document.getElementById("school-options");

            const schools = [
                "SK Ngee Heng, Johor Bahru",
                "SK Kota Dalam, Batu Pahat",
                "SK Sri Tebrau, Johor Bahru",
                "SK Bukit Mutiara, Johor Bahru"
            ];

            // Display options initially
            function updateSchoolOptions(filter = "") {
                schoolOptions.innerHTML = ""; // Clear existing options
                const filteredSchools = schools.filter(school => school.toLowerCase().includes(filter.toLowerCase()));
                if (filteredSchools.length) {
                    filteredSchools.forEach(school => {
                        const li = document.createElement("li");
                        li.textContent = school;
                        li.addEventListener("click", () => {
                            schoolInput.value = school; // Set selected value
                            schoolOptions.style.display = "none"; // Hide dropdown
                        });
                        schoolOptions.appendChild(li);
                    });
                    schoolOptions.style.display = "block"; // Show dropdown
                } else {
                    schoolOptions.style.display = "none"; // Hide dropdown if no match
                }
            }

            // Update options on input
            schoolInput.addEventListener("input", (e) => {
                updateSchoolOptions(e.target.value);
            });

            // Hide dropdown on blur
            schoolInput.addEventListener("blur", () => {
                setTimeout(() => schoolOptions.style.display = "none", 200);
            });

            // Show all options on focus
            schoolInput.addEventListener("focus", () => {
                updateSchoolOptions(schoolInput.value);
            });
        });
    </script>
</head>
<body>

	<div>
		<%@ include file="../navbar.jsp"%>
	</div>

	<div class="content">
		<div class="header">
			<div>
				<a href="/TVPSSHub/user/profile"><i
					class="fa-solid fa-angle-left">Back</i></a>
				<h2>Update Profile</h2>
			</div>
		</div>
		<div class="container">
			<div class="profile-container">
				<div class="profile-header">
					<img src="<c:url value='/resources/images/profile-pic.png' />"
						alt="Profile Picture">
					<h2>Update Profile for ${client.fullName}</h2>
				</div>

				<!-- Profile Information Form -->
				<form action="/TVPSSHub/user/updateProfile" method="post"
					class="profile-form" onsubmit="return confirmUpdate()">
					<div class="profile-info-container">
						<div class="input-group">
							<label for="fullName">Full Name:</label> <input type="text"
								id="fullName" name="fullName" value="${client.fullName}"
								required>
						</div>

						<div class="input-group">
							<label for="email">Email:</label> <input type="email" id="email"
								name="email" value="${client.email}" required>
						</div>

						<div class="input-group">
							<label for="password">Password:</label> <input type="password"
								id="password" name="password" required>
						</div>

						<div class="input-group">
							<label for="dateOfBirth">Date of Birth:</label> <input
								type="text" id="dateOfBirth" name="dateOfBirth"
								value="${client.dateOfBirth}" required>
						</div>

						<div class="input-group">
							<label for="identityCardNumber">ID Number:</label> <input
								type="text" id="identityCardNumber" name="identityCardNumber"
								value="${client.identityCardNumber}" required>
						</div>

						<!-- School Input with Dropdown -->
						<div class="input-group">
							<c:if test="${sessionScope.loggedInClient.role != 1}">
							<label for="school">School:</label>
								<input type="text" id="school" name="school"
									value="${client.school}" required autocomplete="off">
								<ul id="school-options" class="dropdown-options"></ul>
							</c:if>
							<c:if test="${sessionScope.loggedInClient.role == 1}">
								<input type="hidden" id="school" name="school" value="None">
							</c:if>
						</div>

					</div>

					<div class="header">
						<button type="submit">Save Changes</button>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>
