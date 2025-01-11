<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Program</title>
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
				<h2>Add Program</h2>
			</div>
		</div>
		<c:if test="${not empty error}">
			<div class="error-message">
				<p>${error}</p>
			</div>
		</c:if>
		<div class="form-container">
			<form action="/TVPSSHub/activity/addActivity" method="post">
				<!-- File Upload Section -->
				<section class="form-section">
					<div class="file-upload" id="file-upload">
						<label for="fileUpload">Upload a File:</label> <input type="file"
							id="fileUpload" name="fileUpload"
							accept=".pdf,.doc,.docx,.jpg,.png" onchange="previewImage(event)">
					</div>
					<!-- Preview Image Section -->
					<div class="file-upload" id="imagePreviewContainer"
						style="display: none;">
						<img id="imagePreview" src="" alt="Image Preview"
							style="max-width: 100%; height: auto;">
					</div>
				</section>
				<!-- Activity Information Section -->
				<section class="info-section">
					<h2>Activity Information</h2>
					<div class="form-group">
						<label for="title">Title:</label> <input type="text" id="title"
							name="title" required>
					</div>
					<div class="form-group">
						<label for="organizer">Organizer:</label> <input type="text"
							id="organizer" name="organizer" required>
					</div>
					<div class="form-group">
						<label for="status">Status:</label> <select name="status">
							<option value="Preparation">Preparation</option>
							<option value="Ongoing">Ongoing</option>
							<option value="Complete">Complete</option>
							<option value="Cancel">Cancel</option>
							</select>
					</div>

					<div class="form-group">
						<label for="date">Date:</label> <input type="date" id="date"
							name="date" required>
					</div>
					<div class="form-group">
						<label for="venue">Venue:</label> <input type="text" id="venue"
							name="venue" required>
					</div>
					<div class="form-group">
						<label for="district">District:</label> <select name="district">
							<option value="all">All</option>
							<option value="Batu Pahat">Batu Pahat</option>
							<option value="Johor Bahru">Johor Bahru</option>
							<option value="Kluang">Kluang</option>
							<option value="Kota Tinggi">Kota Tinggi</option>
							<option value="Kulai">Kulai</option>
							<option value="Mersing">Mersing</option>
							<option value="Muar">Muar</option>
							<option value="Pontian">Pontian</option>
							<option value="Segamat">Segamat</option>
							<option value="Tangkak">Tangkak</option>
						</select>
					</div>
					<div class="form-group">
						<label for="targetLanguage">Target Language:</label> <input
							type="text" id="targetLanguage" name="targetLanguage" required>
					</div>
					<div class="form-group">
						<label for="competitionLevel">Competition Level:</label> <select name="competitionLevel">
							<option value="International">International</option>
							<option value="National">National</option>
							<option value="State">State</option>
							<option value="District">District</option>
							<option value="School">School</option>
						</select>
					</div>
					<div class="form-group">
						<label for="programDuration">Program Duration (days):</label> <input
							type="number" id="programDuration" name="programDuration" min="1"
							required>
					</div>


				</section>

				<!-- Participants Section -->
				<section class="participants-section">
					<h2>Participants</h2>

					<div class="form-group">
						<label for="primaryParticipants">Primary School:</label> 
						<input type="number" id="participantsPrimary" name="participantsPrimary" min="0" required>
					</div>
					<div class="form-group">
						<label for="secondaryParticipants">Secondary School:</label> <input
							type="number" id="participantsSecondary"
							name="participantsSecondary" min="0" required>
					</div>
					<div class="form-group">
						<label for="openParticipants">Open:</label> <input type="number"
							id="participantsOpen" name="participantsOpen" min="0" required>
					</div>

				</section>

				<section class="description-section">
					<h2>Description</h2>
					<div class="form-group">
						<label for="description">Activity Description:</label>
						<textarea id="description" name="description" rows="4" required></textarea>
					</div>
				</section>
				<!-- Submit Button -->
				<div class="form-actions">
					<button type="submit" class="btn btn-primary"
						onclick="return confirmSubmission()">Add</button>
				</div>
			</form>
		</div>
	</div>
	<script>
		// JavaScript to handle image preview
		function previewImage(event) {
			var file = event.target.files[0];
			var reader = new FileReader();

			// Display the image preview after the file is selected
			reader.onload = function() {
				var previewContainer = document
						.getElementById('imagePreviewContainer');
				var imagePreview = document.getElementById('imagePreview');
				var fileUpload = document.getElementById('file-upload'); // Get the file upload section

				imagePreview.src = reader.result;
				previewContainer.style.display = 'block';

				// Hide the file upload input after selecting a file
				fileUpload.style.display = 'none';
			};

			if (file) {
				reader.readAsDataURL(file);
			}
		}

		function confirmSubmission() {
			return confirm("Are you sure you want to add this activity?");
		}

		// Enable direct date input
		document.addEventListener("DOMContentLoaded", function() {
			const dateInput = document.getElementById("date");
			dateInput.setAttribute("onfocus", "this.showPicker()");
		});
	</script>

</body>

</html>
