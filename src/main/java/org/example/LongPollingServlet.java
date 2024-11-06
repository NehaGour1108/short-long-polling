package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;

@WebServlet("/status/long-polling")
public class LongPollingServlet extends HttpServlet {

    // Queue to hold client requests
    private final LinkedBlockingQueue<HttpServletResponse> clientsQueue = new LinkedBlockingQueue<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Add the client's response object to the queue (for long polling)
        clientsQueue.add(resp);

        // Simulate waiting for new data (this can be replaced with actual event data)
        new Thread(() -> {
            try {
                // Simulate a delay (e.g., waiting for new data)
                Thread.sleep(10000); // Wait for 10 seconds before sending data
                sendStatusToClient("New data is available");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Simulate sending new data to the client after the long poll is completed
    private void sendStatusToClient(String statusMessage) {
        // Get the client response from the queue and send the response
        HttpServletResponse clientResponse = clientsQueue.poll();
        if (clientResponse != null) {
            try (PrintWriter out = clientResponse.getWriter()) {
                clientResponse.setStatus(HttpServletResponse.SC_OK);
                clientResponse.setContentType("application/json");
                out.println("{");
                out.println("\"status\": \"" + statusMessage + "\",");
                out.println("\"timestamp\": \"" + LocalDateTime.now() + "\"");
                out.println("}");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
