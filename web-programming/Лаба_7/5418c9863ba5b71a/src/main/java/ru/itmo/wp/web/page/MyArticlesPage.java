package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @noinspection unused
 */
public class MyArticlesPage {
    ArticleService articleService = new ArticleService();
    public void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "You must register before visiting this page");
            throw new RedirectException("/index");
        }
        User user = (User) request.getSession().getAttribute("user");
        List<Article> articles = articleService.findAllByUserId(user.getId());
        view.put("articles", articles);
    }

    public void changeHiddenState(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String stringId = request.getParameter("id");
        String stringHidden = request.getParameter("hidden");

        articleService.validateHiddenChange((User) request.getSession().getAttribute("user"),
                stringId, stringHidden);

        articleService.updateHidden(Long.parseLong(stringId), Boolean.parseBoolean(stringHidden));
    }
}