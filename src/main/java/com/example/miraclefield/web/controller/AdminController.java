package com.example.miraclefield.web.controller;

import com.example.miraclefield.entity.Question;
import com.example.miraclefield.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/questions")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String listQuestions(Model model) {
        return adminService.listQuestions(model);
    }

    @GetMapping("/add")
    public String showAddQuestionForm(Model model) {
        return adminService.showAddQuestionForm(model);
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult) {
        return adminService.addQuestion(question, bindingResult);
    }

    @GetMapping("/edit/{id}")
    public String showEditQuestionForm(@PathVariable("id") Long id, Model model) {
        return adminService.showEditQuestionForm(id, model);
    }

    @PostMapping("/edit")
    public String editQuestion(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult) {
        return adminService.editQuestion(question, bindingResult);
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Long id) {
        return adminService.deleteQuestion(id);
    }
}
