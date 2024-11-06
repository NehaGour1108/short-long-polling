package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet("/status/short-polling")
public class ShortPollingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Generate a status message
        String status = "Service is up and running at " + LocalDateTime.now();

        // Set the response type to JSON
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        // Send the status as JSON
        out.println("{");
        out.println("\"status\": \"" + status + "\",");
        out.println("\"timestamp\": \"" + LocalDateTime.now() + "\"");
        out.println("}");
        out.flush();
    }
}
