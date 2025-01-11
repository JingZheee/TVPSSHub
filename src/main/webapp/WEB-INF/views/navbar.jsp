<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sidebar Menu</title>
<script src="https://kit.fontawesome.com/af766576f4.js"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="<c:url value='/resources/css/nav.css' />">
</head>
<body>
	<button class="sidebar-toggle">
		<i class="fa-solid fa-bars"></i>
	</button>
	<div class="sidebar">
		<div class="sidebar-header">
			<img src="<c:url value='/resources/images/logo.png' />"
				alt="Ministry of Education">
			<h1>
				Johor <span class="highlight">TVPSS</span>
			</h1>
		</div>
		<ul class="menu">
			<!-- Dashboard link with active class -->
			<li><a href="/TVPSSHub/user/dashboard"
				class="${request.requestURI.contains('/user/dashboard') ? 'active' : ''}">
					<i class="fa-solid fa-gauge"></i> Dashboard
			</a></li>

			
			<!-- Admin-specific links -->
			<c:if test="${sessionScope.loggedInClient.role == 1}">
				<li><a href="/TVPSSHub/activity/activityList"
					class="${request.requestURI.contains('/activity') ? 'active' : ''}">
						<i class="fa-solid fa-calendar-days"></i> Programs
				</a></li>
				<li><a href="/TVPSSHub/school/list"
					class="${request.requestURI.contains('/school') ? 'active' : ''}">
						<i class="fa-solid fa-school"></i> School List
				</a></li>
				<li><a href="/TVPSSHub/resource/list"
					class="${request.requestURI.contains('/TVPSSHub/resource/admin') ? 'active' : ''}">
						<i class="fa-solid fa-book"></i> Resources
				</a></li>
				<li><a href="/TVPSSHub/user/profile"
					class="${request.requestURI.contains('/TVPSSHub/user/profile') ? 'active' : ''}">
						<i class="fa-solid fa-user"></i> Profile
				</a></li>
			</c:if>

			<!-- Teacher-specific links -->
			<c:if test="${sessionScope.loggedInClient.role == 2}">
				<li><a href="/TVPSSHub/activity/activityList"
					class="${request.requestURI.contains('/TVPSSHub/activity/activityList') ? 'active' : ''}">
						<i class="fa-solid fa-calendar-days"></i> Programs
				</a></li>
				<li><a href="/TVPSSHub/school/teacher"
					class="${request.requestURI.contains('/TVPSSHub/school/teacher') ? 'active' : ''}">
						<i class="fa-solid fa-school"></i> School Profile
				</a></li>
				<li><a href="/TVPSSHub/resource/list"
					class="${request.requestURI.contains('/TVPSSHub/resource/teacher') ? 'active' : ''}">
						<i class="fa-solid fa-book"></i> Resources
				</a></li>
				<li><a href="/TVPSSHub/user/userList"
					class="${request.requestURI.contains('/TVPSSHub/user/userList') ? 'active' : ''}">
						<i class="fa-solid fa-users"></i> User List
				</a></li>
			</c:if>

			<!-- Student-specific links -->
			<c:if test="${sessionScope.loggedInClient.role == 3}">
				<li><a href="/TVPSSHub/activity/activityList"
					class="${request.requestURI.contains('/TVPSSHub/activity/activityList') ? 'active' : ''}">
						<i class="fa-solid fa-calendar-days"></i> Programs
				</a></li>
				<li><a
					href="/TVPSSHub/school/student/schoolDetail?name=Sekolah Tinggi Segamat"><i
						class="fa-solid fa-school"></i> School Profile</a></li>

				<li><a href="/TVPSSHub/resource/student"
					class="${request.requestURI.contains('/TVPSSHub/resource/student') ? 'active' : ''}">
						<i class="fa-solid fa-book"></i> Resources
				</a></li>
				<li><a href="/TVPSSHub/user/profile"
					class="${request.requestURI.contains('/TVPSSHub/user/profile') ? 'active' : ''}">
						<i class="fa-solid fa-user"></i> Profile
				</a></li>
			</c:if>
		</ul>
		<div class="logout">
			<a href="/TVPSSHub/user/logout"><i
				class="fa-solid fa-right-from-bracket"></i> Logout</a>
		</div>
	</div>

	<script>
        const sidebarToggle = document.querySelector('.sidebar-toggle');
        const sidebar = document.querySelector('.sidebar');

        sidebarToggle.addEventListener('click', () => {
            sidebar.classList.toggle('open');
            sidebarToggle.classList.toggle('toggled'); // Add or remove the rotated state
        });
    </script>
</body>
</html>
