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
</head>
<body>
	<div>
		<%@ include file="../navbar.jsp"%>
	</div>
	<div class="content">
		<div class="header">
			<div>
				<a href="/TVPSSHub/activity/activityList"><i
					class="fa-solid fa-angle-left">Back</i></a>
				<h2>Edit Programs</h2>
			</div>
		</div>
		<div class="form-container">

			<form action="/TVPSSHub/activity/editActivity/${activity.id}"
				method="post">
				<!-- Activity Information Section -->
				<section class="info-section">
					<h2>Activity Information</h2>
					<div class="form-group">
						<label for="title">Title:</label> <input type="text" id="title"
							name="title" value="${activity.title}" required>
					</div>
					<div class="form-group">
						<label for="organizer">Organizer:</label> <input type="text"
							id="organizer" name="organizer" value="${activity.organizer}"
							required>
					</div>
					<div class="form-group">
						<label for="status">Status:</label> <select name="status">
							<option value="Preparation"
								${activity.status == 'Preparation' ? 'selected' : ''}>Preparation</option>
							<option value="Ongoing"
								${activity.status == 'Ongoing' ? 'selected' : ''}>Ongoing</option>
							<option value="Complete"
								${activity.status == 'Complete' ? 'selected' : ''}>Complete</option>
							<option value="Cancel"
								${activity.status == 'Cancel' ? 'selected' : ''}>Cancel</option>
						</select>
					</div>
					<div class="form-group">
						<label for="date">Date:</label> <input type="date" id="date"
							name="date" value="${activity.date}" required>
					</div>
					<div class="form-group">
						<label for="venue">Venue:</label> <input type="text" id="venue"
							name="venue" value="${activity.venue}" required>
					</div>
					<!-- District Dropdown -->
					<div class="form-group">
						<label for="district">District:</label> <select name="district">
							<option value="all"
								${activity.district == 'all' ? 'selected' : ''}>All</option>
							<option value="Batu Pahat"
								${activity.district == 'Batu Pahat' ? 'selected' : ''}>Batu
								Pahat</option>
							<option value="Johor Bahru"
								${activity.district == 'Johor Bahru' ? 'selected' : ''}>Johor
								Bahru</option>
							<option value="Kluang"
								${activity.district == 'Kluang' ? 'selected' : ''}>Kluang</option>
							<option value="Kota Tinggi"
								${activity.district == 'Kota Tinggi' ? 'selected' : ''}>Kota
								Tinggi</option>
							<option value="Kulai"
								${activity.district == 'Kulai' ? 'selected' : ''}>Kulai</option>
							<option value="Mersing"
								${activity.district == 'Mersing' ? 'selected' : ''}>Mersing</option>
							<option value="Muar"
								${activity.district == 'Muar' ? 'selected' : ''}>Muar</option>
							<option value="Pontian"
								${activity.district == 'Pontian' ? 'selected' : ''}>Pontian</option>
							<option value="Segamat"
								${activity.district == 'Segamat' ? 'selected' : ''}>Segamat</option>
							<option value="Tangkak"
								${activity.district == 'Tangkak' ? 'selected' : ''}>Tangkak</option>
						</select>
					</div>

					<div class="form-group">
						<label for="targetLanguage">Target Language:</label> <input
							type="text" id="targetLanguage" name="targetLanguage"
							value="${activity.targetLanguage}" required>
					</div>
					<!-- Competition Level Dropdown -->
					<div class="form-group">
						<label for="competitionLevel">Competition Level:</label> <select
							name="competitionLevel">
							<option value="International"
								${activity.competitionLevel == 'International' ? 'selected' : ''}>International</option>
							<option value="National"
								${activity.competitionLevel == 'National' ? 'selected' : ''}>National</option>
							<option value="State"
								${activity.competitionLevel == 'State' ? 'selected' : ''}>State</option>
							<option value="District"
								${activity.competitionLevel == 'District' ? 'selected' : ''}>District</option>
							<option value="School"
								${activity.competitionLevel == 'School' ? 'selected' : ''}>School</option>
						</select>
					</div>
					<div class="form-group">
						<label for="programDuration">Program Duration (days):</label> <input
							type="number" id="programDuration" name="programDuration"
							value="${activity.programDuration}" min="1" required>
					</div>
				</section>

				<!-- Participants Section -->
				<section class="participants-section">
					<h2>Participants</h2>
					<div class="form-group">
						<label for="primaryParticipants">Primary School:</label> 
						<input type="number" id="participantsPrimary" name="participantsPrimary" value="${activity.participantsPrimary}" min="0" required>
					</div>
					<div class="form-group">
						<label for="secondaryParticipants">Secondary School:</label> <input
							type="number" id="participantsSecondary"
							name="participantsSecondary" value="${activity.participantsSecondary}"
							min="0" required>
					</div>
					<div class="form-group">
						<label for="openParticipants">Open:</label> 
						<input type="number" id="openParticipants" name="participantsOpen" value="${activity.participantsOpen}" min="0" required>
					</div>

				</section>

				<!-- Description Section -->
				<section class="description-section">
					<h2>Description</h2>
					<div class="form-group">
						<label for="description">Activity Description:</label>
						<textarea id="description" name="description" rows="4" required>${activity.description}</textarea>
					</div>
				</section>

				<!-- Submit Button -->
				<div class="form-actions">
					<button type="submit" class="btn btn-primary"
						onclick="return confirmEdit()">Update</button>
				</div>
			</form>

		</div>
	</div>
	<script>
		// JavaScript function to show confirmation dialog
		function confirmEdit() {
			return confirm("Are you sure you want to edit this program?");
		}
	</script>
</body>
</html>
