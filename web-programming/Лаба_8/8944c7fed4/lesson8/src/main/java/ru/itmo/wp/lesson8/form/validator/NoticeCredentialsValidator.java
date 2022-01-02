package ru.itmo.wp.lesson8.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.lesson8.form.NoticeForm;
import ru.itmo.wp.lesson8.service.NoticeService;

@Component
public class NoticeCredentialsValidator implements Validator {
    private final NoticeService noticeService;

    public NoticeCredentialsValidator(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NoticeForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            NoticeForm noticeForm = (NoticeForm) target;
            if (noticeForm.getContent() != null && noticeForm.getContent().length() < 2) {
                errors.rejectValue("content", "content.invalid-content", "content length must be at least 2");
            } else if (noticeForm.getContent() != null && noticeForm.getContent().length() > 255) {
                errors.rejectValue("content", "content.invalid-content", "content length can't be more than 255");
            } else if (noticeForm.getContent() != null && noticeForm.getContent().isBlank()) {
                errors.rejectValue("content", "content.invalid-content", "content must have non-empty characters");
            } else if (noticeForm.getContent() != null && noticeForm.getContent().isEmpty()) {
                errors.rejectValue("content", "content.invalid-content", "content must not be empty");
            }
        }
    }
}