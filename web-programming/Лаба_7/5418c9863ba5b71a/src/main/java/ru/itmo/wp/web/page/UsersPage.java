package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    private void changeAdmin(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String stringId = request.getParameter("userId");
        String stringAdmin = request.getParameter("status");
        User user = (User) request.getSession().getAttribute("user");
        User currentUser = userService.find(user.getId());
        userService.validatePrivilegesChange(currentUser, stringId, stringAdmin);
        long id = Long.parseLong(stringId);
        boolean admin = Boolean.parseBoolean(stringAdmin);
        if (user.getAdmin() && user.getId() != id) {
            userService.changeAdminStatus(id, admin);
            view.put("actualAdminStatus", userService.find(id).getAdmin());
        }
        if (user.getId() == id) {
            view.put("message", "You have no permission to change your admin status");
        }
        view.put("actualAdminStatus", userService.find(id).getAdmin());
    }
}
