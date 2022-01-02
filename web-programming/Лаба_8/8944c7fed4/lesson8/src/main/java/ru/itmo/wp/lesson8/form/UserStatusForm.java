package ru.itmo.wp.lesson8.form;

import org.springframework.stereotype.Component;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
public class UserStatusForm {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "Disable|Enable", message = "Exception action \"Disable\" or \"Enable\"")
    private String disabled;

    private Long userId;

    @AssertTrue
    private boolean isDisabledValid() {
        return "Enable".equals(disabled) || "Disable".equals(disabled);
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String enabled) {
        this.disabled = enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}