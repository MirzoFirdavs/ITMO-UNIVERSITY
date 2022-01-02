package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.event.EventType;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class EnterPage extends Page {

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");
        getUserService().validateEnter(loginOrEmail, password);
        User user = getUserService().findByLoginAndPassword(loginOrEmail, password);
        getEventService().addEvent(user, EventType.ENTER);
        setUser(user);
        setMessage("Hello, " + user.getLogin());
        throw new RedirectException("/index");
    }
}