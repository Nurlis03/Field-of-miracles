package com.example.miraclefield.service;

import com.example.miraclefield.entity.Question;
import com.example.miraclefield.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class AdminService {

    private QuestionService questionService;

    public String listQuestions(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("questions", questionService.findAll());
        model.addAttribute("user", user);
        return "admin/questions";
    }

    public String showAddQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        return "admin/add-question";
    }

    public String addQuestion(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/add-question";
        }

        if (questionService.existsByQuestionText(question.getQuestionText())) {
            bindingResult.rejectValue("questionText", "duplicate.questionText", "Question already exists");
            return "admin/add-question";
        }
        question.setQuestionText(question.getQuestionText().trim());
        question.setAnswer(question.getAnswer().trim());
        questionService.save(question);
        return "redirect:/admin/questions";
    }

    public String showEditQuestionForm(@PathVariable("id") Long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "admin/edit-question";
    }

    public String editQuestion(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit-question";
        }
        questionService.deleteRelatedGameHistories(question);
        question.setQuestionText(question.getQuestionText().trim());
        question.setAnswer(question.getAnswer().trim());
        questionService.save(question);
        return "redirect:/admin/questions";
    }

    public String deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteById(id);
        return "redirect:/admin/questions";
    }
}
