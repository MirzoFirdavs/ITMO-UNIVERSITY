package ru.itmo.wp.lesson8.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.form.NoticeForm;
import ru.itmo.wp.lesson8.form.validator.NoticeCredentialsValidator;
import ru.itmo.wp.lesson8.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
    private final NoticeCredentialsValidator noticeCredentialsValidator;

    public NoticePage(NoticeService noticeService, NoticeCredentialsValidator noticeCredentialsValidator) {
        this.noticeService = noticeService;
        this.noticeCredentialsValidator = noticeCredentialsValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (noticeCredentialsValidator.supports(binder.getTarget().getClass())) {
            binder.addValidators(noticeCredentialsValidator);
        }
    }

    @GetMapping("/notice")
    public String noticeGet(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String noticePost(@Valid @ModelAttribute("noticeForm") NoticeForm noticeForm,
                             BindingResult bindingResult,
                             HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        noticeService.add(noticeForm);
        putMessage(httpSession, "Congrats, your notice is added!");

        return "redirect:";
    }
}