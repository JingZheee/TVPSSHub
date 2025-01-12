package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.dao.SchoolDAO;
import com.example.model.School;

@Controller
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolDAO schoolDAO;

    @PreAuthorize("hasRole('ROLE_1')")
    @GetMapping("/addSchool")
    public String showAddForm(Model model) {
        model.addAttribute("school", new School());
        return "schools/addSchool";
    }

    @PreAuthorize("hasRole('ROLE_1')")
    @PostMapping("/addSchool")
    public String addSchool(@ModelAttribute("school") School school) {
        schoolDAO.saveSchool(school);
        return "redirect:/school/list";
    }

    @PreAuthorize("hasAnyRole('ROLE_1', 'ROLE_2')")
    @GetMapping("/editSchool/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        School school = schoolDAO.getSchoolById(id);
        if (school != null) {
            model.addAttribute("school", school);
            return "schools/editSchool";
        }
        return "redirect:/school/list";
    }

    @PreAuthorize("hasAnyRole('ROLE_1', 'ROLE_2')")
    @PostMapping("/editSchool")
    public String updateSchool(@ModelAttribute("school") School school) {
        schoolDAO.saveSchool(school);
        return "redirect:/school/list";
    }

    @PreAuthorize("hasRole('ROLE_1')")
    @GetMapping("/deleteSchool/{id}")
    public String deleteSchool(@PathVariable int id) {
        schoolDAO.deleteSchool(id);
        return "redirect:/school/list";
    }

    @GetMapping("/list")
    public String listSchools(Model model) {
        List<School> schools = schoolDAO.getAllSchools();
        model.addAttribute("schools", schools);
        return "schools/schoolList";
    }

    @GetMapping("/viewDetails/{id}")
    public String viewDetails(@PathVariable int id, Model model) {
        School school = schoolDAO.getSchoolById(id);
        if (school != null) {
            model.addAttribute("school", school);
            return "schools/schoolDetails";
        }
        return "redirect:/school/list";
    }
}
