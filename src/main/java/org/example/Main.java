package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Server server = new Server(9092);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");

        server.setHandler(handler);

        handler.addServlet(new ServletHolder(new ShortPollingServlet()), "/status/short-polling");
        handler.addServlet(new ServletHolder(new LongPollingServlet()), "/status/long-polling");
try{
        server.start();
        System.out.println("Server started on port 9092");
        server.join();
    } catch (Exception e) {
    throw new RuntimeException(e);
        }
    }
}