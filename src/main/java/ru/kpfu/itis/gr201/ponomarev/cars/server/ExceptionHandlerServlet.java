package ru.kpfu.itis.gr201.ponomarev.cars.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "exceptionHandlerServlet", urlPatterns = "/error")
public class ExceptionHandlerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        req.setAttribute("exception", throwable);
        req.setAttribute("statusCode", statusCode);
        req.setAttribute("requestUri", requestUri);
        req.getRequestDispatcher("/error.ftl").forward(req, resp);
    }
}
