<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registration Page</title>
<link rel="stylesheet" href="<c:url value='/resources/css/login.css' />">
</head>
<body>
	<div class="login-page">
		<div class="login-container">
			<div class="logo-section">
				<img src="<c:url value='/resources/images/logo.png' />"
					alt="Logo of Ministry of Education">
				<h2>MINISTRY OF EDUCATION</h2>
				<h3>Johor TVPSS</h3>
			</div>
			<div class="divider"></div>
			<!-- Form Section -->
			<div class="login-form">
				<h2>Register</h2>
				<form action="/TVPSSHub/user/register" method="post">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					<!-- Full Name -->
					<div class="form-row">
						<input type="text" name="fullName" placeholder="Full Name"
							required>
					</div>

					<!-- Email -->
					<div class="form-row">
						<input type="email" name="email" placeholder="Email Address"
							required>
					</div>

					<!-- Date of Birth -->
					<div class="form-row">
						<input type="date" name="dateOfBirth" id="dob"
							placeholder="Date of Birth" required>
					</div>

					<!-- Identity Card Number -->
					<div class="form-row">
						<input type="text" name="identityCardNumber"
							placeholder="Identity Card Number" required>

					</div>

					<!-- School -->
					<div class="form-row">
						<input type="text" name="school" id="school-input"
							placeholder="School" autocomplete="off" required>
						<ul id="school-options" class="dropdown-options"></ul>
					</div>


					<!-- Password -->
					<div class="form-row">
						<input type="password" name="password" placeholder="Password"
							required>
					</div>

					<!-- Confirm Password -->
					<div class="form-row">
						<input type="password" id="checkPassword" name="checkPassword"
							placeholder="Confirm Password" required>

					</div>

					<p>
						Already got an account? <a href="/TVPSSHub/user/login">Login
							here</a>
					</p>

					<button type="submit">Submit Registration</button>
				</form>
				<!-- Display Error Messages -->
				<c:if test="${not empty error}">
					<div class="error-message">
						<p>${error}</p>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<script>
		document.addEventListener('DOMContentLoaded', function() {
			// Restrict future dates for Date of Birth
			const dateInput = document
					.querySelector("input[name='dateOfBirth']");
			dateInput.setAttribute("max",
					new Date().toISOString().split("T")[0]);
			dateInput.addEventListener('focus', () => dateInput.showPicker && dateInput.showPicker());
			
			const schoolInput = document.getElementById("school-input");
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
</body>
</html>
