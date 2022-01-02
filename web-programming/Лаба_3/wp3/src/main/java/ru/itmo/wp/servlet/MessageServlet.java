package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MessageServlet extends HttpServlet {
    private final List<Message> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String json = "";
        HttpSession requestGetSession = request.getSession();
        if (uri.endsWith("/message/auth")) {
            String user = (String) requestGetSession.getAttribute("user");
            if (user == null) {
                user = request.getParameter("user");
                if (user == null) {
                    user = "";
                } else {
                    requestGetSession.setAttribute("user", user);
                }
            }
            json = new Gson().toJson(user);
        } else if (uri.endsWith("/message/findAll")) {
            if (requestGetSession.getAttribute("user") != null) {
                json = new Gson().toJson(messages);
            }
        } else if (uri.endsWith("/message/add")) {
            String text = request.getParameter("text");
            if (!text.isBlank()) {
                String message = (String) requestGetSession.getAttribute("user");
                messages.add(new Message(message, text));
            }
        } else {
            throw new IllegalArgumentException();
        }
        response.setContentType("application/json");
        OutputStreamWriter wr = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        wr.write(json);
        wr.flush();
    }

    public static class Message {
        private String user;
        private String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}