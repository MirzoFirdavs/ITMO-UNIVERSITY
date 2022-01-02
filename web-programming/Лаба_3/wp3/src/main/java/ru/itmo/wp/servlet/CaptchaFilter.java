package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Random random = new Random();
        if (session.getAttribute("captcha") == null) {
            if (session.getAttribute("number") != null && request.getParameter("number") != null &&
                    request.getParameter("number").equals(session.getAttribute("number").toString())) {
                response.sendRedirect(request.getRequestURI());
                session.setAttribute("captcha", true);
            } else {
                int number = random.nextInt(899) + 100;
                session.setAttribute("number", number);
                String captcha_img = Base64.getEncoder().encodeToString(ImageUtils.toPng(Integer.toString(number)));
                String captcha = "<!DOCTYPE html>\n" +  
                        "<html lang=\"en\">\n" +
                        "<body>\n" +
                        "<div class=\"middle\">\n" +
                        "    <main>\n" +
                        "        <div class=\"captcha\">\n" +
                        "            <form method=\"get\">\n" +
                        "                <label for=\"number-from-user\">Enter number</label>\n" +
                        "                <input id=\"number-from-user\" name=\"number\"/>\n" +
                        "                <img src=\"data:image/png;base64,%s\" alt=\"captcha\">\n" +
                        "            </form>\n" +
                        "        </div>\n" +
                        "    </main>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>";
                response.getWriter().print(String.format(captcha, captcha_img));
                response.getWriter().flush();
            }
        } else {
            // /messages.html
            chain.doFilter(request, response);
        }
    }
}