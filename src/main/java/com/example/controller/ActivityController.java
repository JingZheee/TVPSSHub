package com.example.controller;

import com.example.dao.ActivityDAO;
import com.example.dao.FeedbackDAO;
import com.example.dao.UserDAO;

import com.example.model.ActivityViewModel;
import com.example.model.UserViewModel;
import com.example.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityDAO activityDAO;

    @Autowired
    private FeedbackDAO feedbackDAO;

    @Autowired
    private UserDAO userDAO;

    // Show activity list - Everyone can access
    @GetMapping("/activityList")
    public String showActivityList(Model model) {
        List<ActivityViewModel> activities = activityDAO.getAllActivities();
        model.addAttribute("activities", activities);
        return "activity/activityList";
    }

    // Add Activity - Only Teachers can add
    @PreAuthorize("hasRole('ROLE_2')")
    @GetMapping("/addActivity")
    public String showAddActivityForm(Model model) {
        model.addAttribute("activity", new ActivityViewModel());
        return "activity/addActivity";
    }

    @PreAuthorize("hasRole('ROLE_2')")
    @PostMapping("/addActivity")
    public String processAddActivityForm(@ModelAttribute("activity") ActivityViewModel activity,
            Authentication authentication) {
        // Get current user's ID from authentication
        UserViewModel currentUser = userDAO.findUserByEmail(authentication.getName());
        activity.setCreatorId(currentUser.getId());

        activityDAO.saveActivity(activity);
        return "redirect:/activity/activityList";
    }

    // View Activity Details - Everyone can access
    @GetMapping("/activityDetails/{id}")
    public String showActivityDetails(@PathVariable("id") int id, Model model) {
        ActivityViewModel activity = activityDAO.findActivityById(id);
        if (activity == null) {
            model.addAttribute("error", "Activity not found.");
            return "redirect:/activity/activityList";
        }
        model.addAttribute("activity", activity);
        return "activity/activityDetails";
    }

    // Edit Activity - Only Teachers who created the activity
    @PreAuthorize("hasRole('ROLE_2')")
    @GetMapping("/editActivity/{id}")
    public String showEditActivityForm(@PathVariable("id") int id, Authentication authentication, Model model) {
        System.out.println("Principal: " + authentication.getPrincipal());

        ActivityViewModel activity = activityDAO.findActivityById(id);
        UserViewModel currentUser = userDAO.findUserByEmail(authentication.getName());

        if (activity == null) {
            model.addAttribute("error", "Activity not found.");
            return "redirect:/activity/activityList";
        }

        // Check if the logged-in user is the creator
        if (activity.getCreatorId() != currentUser.getId()) {
            model.addAttribute("error", "You are not authorized to edit this activity.");
            return "redirect:/activity/activityList";
        }

        model.addAttribute("activity", activity);
        return "activity/editActivity";
    }

    @PreAuthorize("hasRole('ROLE_2')")
    @PostMapping("/editActivity/{id}")
    public String processEditActivityForm(@PathVariable("id") int id,
            @ModelAttribute("activity") ActivityViewModel updatedActivity,
            Authentication authentication,
            Model model) {
        ActivityViewModel existingActivity = activityDAO.findActivityById(id);
        UserViewModel currentUser = userDAO.findUserByEmail(authentication.getName());

        if (existingActivity != null && existingActivity.getCreatorId() == currentUser.getId()) {
            // Update activity fields
            existingActivity.setTitle(updatedActivity.getTitle());
            existingActivity.setOrganizer(updatedActivity.getOrganizer());
            existingActivity.setStatus(updatedActivity.getStatus());
            existingActivity.setDate(updatedActivity.getDate());
            existingActivity.setVenue(updatedActivity.getVenue());
            existingActivity.setDistrict(updatedActivity.getDistrict());
            existingActivity.setTargetLanguage(updatedActivity.getTargetLanguage());
            existingActivity.setCompetitionLevel(updatedActivity.getCompetitionLevel());
            existingActivity.setProgramDuration(updatedActivity.getProgramDuration());
            existingActivity.setDescription(updatedActivity.getDescription());
            existingActivity.setParticipantsPrimary(updatedActivity.getParticipantsPrimary());
            existingActivity.setParticipantsSecondary(updatedActivity.getParticipantsSecondary());
            existingActivity.setParticipantsOpen(updatedActivity.getParticipantsOpen());

            activityDAO.updateActivity(existingActivity);
            model.addAttribute("message", "Activity updated successfully.");
        }
        return "redirect:/activity/activityDetails/" + id;
    }

    // Filter Activities - Everyone can access
    @GetMapping("/filterActivities")
    public String filterActivities(@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "location", required = false) String location,
            Model model) {
        List<ActivityViewModel> activities = activityDAO.getFilteredActivities(searchKeyword, location);
        model.addAttribute("activities", activities);
        return "activity/activityList";
    }

    // Show Feedback - Only Teachers and Admin can view
    @PreAuthorize("hasAnyRole('ROLE_1', 'ROLE_2')")
    @GetMapping("/showFeedback/{id}")
    public String showFeedback(@PathVariable("id") int activityId, Model model) {
        List<Feedback> feedbackList = feedbackDAO.getFeedbackByActivityId(activityId);
        model.addAttribute("feedbackList", feedbackList);
        if (feedbackList == null || feedbackList.isEmpty()) {
            model.addAttribute("noFeedbackMessage", "No feedback available for this activity.");
        }
        return "activity/showFeedback";
    }

    // Add Feedback - Only Admin can add feedback
    @PreAuthorize("hasRole('ROLE_1')")
    @GetMapping("/addFeedback")
    public String showAddFeedback(@RequestParam("activityId") int activityId, Model model) {
        ActivityViewModel activity = activityDAO.findActivityById(activityId);
        if (activity == null) {
            throw new IllegalArgumentException("Activity not found for ID: " + activityId);
        }

        model.addAttribute("activity", activity);
        model.addAttribute("currentDate", LocalDate.now());
        return "activity/addFeedback";
    }

    @PreAuthorize("hasRole('ROLE_1')")
    @PostMapping("/addFeedback")
    public String addFeedback(@RequestParam("activityId") int activityId,
            @RequestParam("feedbackText") String feedbackText,
            @RequestParam(value = "rating", required = false) Integer rating,
            Authentication authentication,
            @RequestParam("date") String date) {

        UserViewModel currentUser = userDAO.findUserByEmail(authentication.getName());
        LocalDate parsedDate = LocalDate.parse(date);

        Feedback feedback = new Feedback();
        feedback.setActivityId(activityId);
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setUserId(currentUser.getId().intValue());
        feedback.setDate(parsedDate);

        feedbackDAO.save(feedback);
        return "redirect:/activity/activityDetails/" + activityId;
    }

}
