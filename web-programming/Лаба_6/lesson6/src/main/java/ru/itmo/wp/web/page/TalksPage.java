package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talks;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class TalksPage extends Page {

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            setMessage("You have to be authorised to use talks");
            throw new RedirectException("/index");
        }
        view.put("users", getUserService().findAll());
        view.put("talks", getTalkService().findTalks(getUser()));
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Talks talks = new Talks();
        long targetSourceId = Long.parseLong(request.getParameter("targetUserId"));
        String text = request.getParameter("text");
        getTalkService().validateTalks(talks, targetSourceId, text, getUser());
        getTalkService().sendMessage(getUser(), targetSourceId, text);
        throw new RedirectException("/talks");
    }
}