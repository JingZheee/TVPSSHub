package com.example.controller;

import com.example.dao.ActivityDAO;
import com.example.dao.FeedbackDAO;

import com.example.model.ActivityViewModel;
import com.example.model.UserViewModel;
import com.example.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityDAO activityDAO;
    
    @Autowired
    private FeedbackDAO feedbackDAO;

    // Show activity list
    @GetMapping("/activityList")
    public String showActivityList(HttpSession session, Model model) {
        UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");
        if (loggedInClient == null) {
            model.addAttribute("error", "You must be logged in to view activities.");
            return "redirect:/user/login";
        }

        List<ActivityViewModel> activities = activityDAO.getAllActivities();
        model.addAttribute("activities", activities);

        return "activity/activityList";
    }

    // Show form to add an activity
    @GetMapping("/addActivity")
    public String showAddActivityForm(Model model) {
        model.addAttribute("activity", new ActivityViewModel());
        return "activity/addActivity";
    }

    // Process the add activity form
    @PostMapping("/addActivity")
    public String processAddActivityForm(@ModelAttribute("activity") ActivityViewModel activity, HttpSession session, Model model) {
        UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");
        if (loggedInClient == null) {
            model.addAttribute("error", "You must be logged in to add an activity.");
            return "redirect:/user/login";
        }

        // Set the creatorId to the logged-in user's ID
        activity.setCreatorId(loggedInClient.getId());
        
        activityDAO.saveActivity(activity);
        model.addAttribute("message", "Activity added successfully.");
        return "redirect:/activity/activityList";
    }

    // Show activity details
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

    @GetMapping("/editActivity/{id}")
    public String showEditActivityForm(@PathVariable("id") int id, HttpSession session, Model model) {
        ActivityViewModel activity = activityDAO.findActivityById(id);
        UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");

        if (activity == null) {
            model.addAttribute("error", "Activity not found.");
            return "redirect:/activity/activityList";
        }

        // Check if the logged-in user is the creator
        if (activity.getCreatorId() != loggedInClient.getId()) {
            model.addAttribute("error", "You are not authorized to edit this activity.");
            return "redirect:/activity/activityList";
        }

        model.addAttribute("activity", activity);
        return "activity/editActivity";
    }


    // Process the edit activity form
    @PostMapping("/editActivity/{id}")
    public String processEditActivityForm(@PathVariable("id") int id, @ModelAttribute("activity") ActivityViewModel updatedActivity, Model model) {
        ActivityViewModel existingActivity = activityDAO.findActivityById(id);
        if (existingActivity != null) {
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
        } else {
            model.addAttribute("error", "Activity not found.");
            return "activity/editActivity";
        }

        return "redirect:/activity/activityDetails/" + id;
    }

    @GetMapping("/filterActivities")
    public String filterActivities(@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                    @RequestParam(value = "location", required = false) String location,
                                    Model model) {
        // Logic to handle filtering activities
        List<ActivityViewModel> activities = activityDAO.getFilteredActivities(searchKeyword, location);

        model.addAttribute("activities", activities);
        return "activity/activityList";
    }
    
    @GetMapping("/showFeedback/{id}")
    public String showFeedback(@PathVariable("id") int activityId, Model model) {
        // Fetch the feedback list for the given activityId
        List<Feedback> feedbackList = feedbackDAO.getFeedbackByActivityId(activityId);

        System.out.println("Fetched feedback list size: " + (feedbackList != null ? feedbackList.size() : 0));

        // Add feedbackList to the model
        model.addAttribute("feedbackList", feedbackList);

        // Add a message if feedbackList is empty
        if (feedbackList == null || feedbackList.isEmpty()) {
            model.addAttribute("noFeedbackMessage", "No feedback available for this activity.");
        }

        return "activity/showFeedback";
    }
    
    

    
    @GetMapping("/addFeedback")
    public String showAddFeedback(@RequestParam("activityId") int activityId, HttpSession session, Model model) {
        // Retrieve the logged-in user from the session
        UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");
        if (loggedInClient == null) {
            model.addAttribute("error", "You must be logged in to add feedback.");
            return "redirect:/user/login";
        }

        // Fetch the activity using the provided activityId
        ActivityViewModel activity = activityDAO.findActivityById(activityId);
        if (activity == null) {
            throw new IllegalArgumentException("Activity not found for ID: " + activityId);
        }

        // Add necessary data to the model
        model.addAttribute("activity", activity);
        model.addAttribute("user", loggedInClient); // Add the logged-in user to the model
        model.addAttribute("currentDate", LocalDate.now()); // Pass current date to the model

        return "activity/addFeedback";
    }

    @PostMapping("/addFeedback")
    public String addFeedback(@RequestParam("activityId") int activityId,
                              @RequestParam("feedbackText") String feedbackText,
                              @RequestParam(value = "rating", required = false) Integer rating,
                              @RequestParam("userId") int userId,
                              @RequestParam("date") String date) {
        
        LocalDate parsedDate = LocalDate.parse(date);
        
        Feedback feedback = new Feedback();
        feedback.setActivityId(activityId);
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setUserId(userId);
        feedback.setDate(parsedDate);

        feedbackDAO.save(feedback);

        return "redirect:/activity/activityDetails/" + activityId;
    }


}
