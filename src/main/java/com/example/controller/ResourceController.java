package com.example.controller;

import com.example.dao.ResourceDAO;
import com.example.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceDAO resourceDAO;

    @GetMapping("/list")
    public String listResources(Model model) {
        List<Resource> resources = resourceDAO.getAllResources();
        model.addAttribute("resources", resources);
        return "resources/resourceList";
    }

    @GetMapping("/addResource")
    public String showAddForm(Model model) {
        model.addAttribute("resource", new Resource());
        return "resources/createResource";
    }

    @PostMapping("/addResource")
    public String addResource(@ModelAttribute("resource") Resource resource) {
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
            return "resources/resourceDetails";
        }
        return "redirect:/resource/list";
    }


    @PostMapping("/editResource")
    public String updateResource(@ModelAttribute("resource") Resource resource) {
        resourceDAO.saveResource(resource);
        return "redirect:/resource/list";
    }

}
