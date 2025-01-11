package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.dao.SchoolDAO;
import com.example.model.School;

@Controller
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolDAO schoolDAO;

    @GetMapping("/list")
    public String listSchools(Model model) {
        List<School> schools = schoolDAO.getAllSchools();
        model.addAttribute("schools", schools);
        return "schools/schoolList";
    }

    @GetMapping("/addSchool")
    public String showAddForm(Model model) {
        model.addAttribute("school", new School());
        return "schools/addSchool"; 
    }

    @PostMapping("/addSchool")
    public String addSchool(@ModelAttribute("school") School school) {
    	 System.out.println("Saving school: " + school);
    	 System.out.println("School Details:");
    	 System.out.println("ID: " + school.getId());
    	 System.out.println("Name: " + school.getName());
    	 System.out.println("District: " + school.getDistrict());
    	 System.out.println("Representative: " + school.getRepresentative());

        schoolDAO.saveSchool(school);
        return "redirect:/school/list";
    }


    @GetMapping("/editSchool/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        School school = schoolDAO.getSchoolById(id);
        if (school != null) {
            model.addAttribute("school", school);
            return "schools/editSchool"; // Use the same form view for editing
        }
        return "redirect:/school/list";
    }

    @PostMapping("/editSchool")
    public String updateSchool(@ModelAttribute("school") School school) {
        schoolDAO.saveSchool(school);
        return "redirect:/school/list";
    }

    @GetMapping("/deleteSchool/{id}")
    public String deleteSchool(@PathVariable int id) {
        schoolDAO.deleteSchool(id);
        return "redirect:/school/list";
    }
    
    @GetMapping("/viewDetails/{id}")
    public String viewDetails(@PathVariable int id, Model model) {
        School school = schoolDAO.getSchoolById(id);
        if (school != null) {
            model.addAttribute("school", school);
            return "schools/schoolDetails"; // A new view to show school details
        }
        return "redirect:/school/list";
    }

}
