package com.example.miraclefield.service;

import com.example.miraclefield.entity.Question;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
        model.addAttribute("questions", questionService.findAll());
        return "admin/questions";
    }

    public String showAddQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        return "admin/add-question";
    }

    public String addQuestion(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/add-question"; // Return to the form if validation fails
        }

        // Check if the question already exists
        if (questionService.existsByQuestionText(question.getQuestionText())) {
            // Add an error message to inform the user
            bindingResult.rejectValue("questionText", "duplicate.questionText", "Question already exists");
            return "admin/add-question";
        }

        questionService.save(question);
        return "redirect:/admin/questions";
    }

    public String showEditQuestionForm(@PathVariable("id") Long id, Model model) {
        Question question = questionService.findById(id);
        if (question != null) {
            model.addAttribute("question", question);
            return "admin/edit-question";
        } else {
            return "redirect:/admin/questions";
        }
    }

    public String editQuestion(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit-question";
        }
        questionService.deleteRelatedGameHistories(question);
        questionService.save(question);
        return "redirect:/admin/questions";
    }

    public String deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteById(id);
        return "redirect:/admin/questions";
    }
}
