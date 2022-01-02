package ru.itmo.wp.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] uris = uri.split("\\+");
        ServletOutputStream outputStream = response.getOutputStream();
        File[] files = new File[uris.length];

        for (int i = 0; i < files.length; i++) {
            String current = uris[i];
            File file = new File(getServletContext().getRealPath("/static/") + "../../../src/main/webapp/static/" + current);
            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath("/static/" + current));
            }
            if (file.isFile()) {
                files[i] = file;
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        for (int j = 0; j < files.length; ++j) {
            if (j == 0) {
                response.setContentType(getContentTypeFromName(files[j].getPath()));
            }
            Files.copy(files[j].toPath(), outputStream);
        }
        outputStream.flush();
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
