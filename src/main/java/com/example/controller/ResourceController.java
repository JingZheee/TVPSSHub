package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.ResourceDAO;
import com.example.dao.UserDAO;
import com.example.model.Resource;
import com.example.model.UserViewModel;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceDAO resourceDAO;

	@Autowired
	private UserDAO userDAO; 

    @GetMapping("/list")
    public String listResources(Model model) {
        List<Resource> resources = resourceDAO.getAllResources();
        model.addAttribute("resources", resources);
        return "resources/resourceList";}

    @PreAuthorize("hasAnyRole('ROLE_2', 'ROLE_3')")
    @GetMapping("/addResource")
    public String showAddForm(Model model) {
        model.addAttribute("resource", new Resource());
        return "resources/createResource";
    }

    @PreAuthorize("hasAnyRole('ROLE_2', 'ROLE_3')")
    @PostMapping("/addResource")
    public String addResource(@ModelAttribute("resource") Resource resource) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = auth.getName();
    
    // Get user's school
    UserViewModel user = userDAO.getUserByEmail(userEmail);
    
    // Set school in resource
    resource.setSchool(user.getSchool());
    resource.setUpdatedDate(LocalDate.now());
    resource.setState("Pending");
        resourceDAO.saveResource(resource);
        return "redirect:/resource/list";
    }

    @GetMapping("/editResource/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Resource resource = resourceDAO.getResourceById(id);
        if (resource != null) {
            model.addAttribute("resource", resource);
            return "resources/editResource";
        }
        return "redirect:/resource/list";
    }

    @PostMapping("/editResource")
    public String updateResource(@ModelAttribute("resource") Resource resource) {
        resourceDAO.saveResource(resource);
        return "redirect:/resource/list";
    }

    @PreAuthorize("hasAnyRole('ROLE_2', 'ROLE_3')")
    @GetMapping("/deleteResource/{id}")
    public String deleteResource(@PathVariable Long id) {
        resourceDAO.deleteResource(id);
        return "redirect:/resource/list";
    }

    @GetMapping("/viewDetails/{id}")
    public String viewDetails(@PathVariable Long id, Model model) {
        Resource resource = resourceDAO.getResourceById(id);
        if (resource != null) {
            model.addAttribute("resource", resource);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_1")) && auth != null) {
                return "resources/adminDetail";
            }
            return "resources/resourceDetails";
        }
        return "redirect:/resource/list";
    }

    @PostMapping("/replyResource")
        public String replyResource(@RequestParam("id") int id, 
                          @RequestParam("reply") String reply,
                          @RequestParam("action") String action,
                          Model model) {
        // Get existing resource
        Resource existingResource = resourceDAO.getResourceById(id);
        
        // Update fields
        existingResource.setReply(reply);
        existingResource.setState(action.equals("approve") ? "Approved" : "Rejected");
        existingResource.setUpdatedDate(LocalDate.now());
        
        // Save updated resource
        resourceDAO.saveResource(existingResource);
        
        // Refresh list and return view
        List<Resource> resources = resourceDAO.getAllResources();
        model.addAttribute("resources", resources);
        return "redirect:/resource/list";
    }

   @GetMapping("/filter")
    public String filterResources(
        @RequestParam(required = false) String searchText,
        @RequestParam(required = false) String state,
        Model model) {
        
        List<Resource> resources = resourceDAO.getFilteredResources(searchText, state);
        model.addAttribute("resources", resources);
        return "resources/resourceList";
    }
}
