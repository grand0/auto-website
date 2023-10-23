package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserNotAuthenticatedException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.*;
import ru.kpfu.itis.gr201.ponomarev.cars.service.CarService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.ModelService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "newCarServlet", urlPatterns = "/garage/new")
public class NewCarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("bodies", Body.values());
        req.setAttribute("transmissions", Transmission.values());
        req.setAttribute("drives", Drive.values());
        req.setAttribute("engines", Engine.values());
        req.getRequestDispatcher("/newcar.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        boolean formValid = true;
        JSONObject jsonResponse = new JSONObject();

        String make = req.getParameter("make");
        if (make == null || make.isEmpty()) {
            jsonResponse.put("makeInvalid", true);
            formValid = false;
        }

        String model = req.getParameter("model");
        if (model == null || model.isEmpty()) {
            jsonResponse.put("modelInvalid", true);
            formValid = false;
        }

        int year = 0;
        try {
            String yearStr = req.getParameter("year");
            year = Integer.parseInt(yearStr);
            if (year < 1900) throw new NumberFormatException();
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("yearInvalid", true);
            formValid = false;
        }

        int bodyId = 0;
        try {
            String bodyIdStr = req.getParameter("bodyId");
            bodyId = Integer.parseInt(bodyIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("bodyIdInvalid", true);
            formValid = false;
        }

        int transmissionId = 0;
        try {
            String transmissionIdStr = req.getParameter("transmissionId");
            transmissionId = Integer.parseInt(transmissionIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("transmissionIdInvalid", true);
            formValid = false;
        }

        int driveId = 0;
        try {
            String driveIdStr = req.getParameter("driveId");
            driveId = Integer.parseInt(driveIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("driveIdInvalid", true);
            formValid = false;
        }

        int engineId = 0;
        try {
            String engineIdStr = req.getParameter("engineId");
            engineId = Integer.parseInt(engineIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("engineIdInvalid", true);
            formValid = false;
        }

        int horsepower = 0;
        try {
            String horsepowerStr = req.getParameter("horsepower");
            horsepower = Integer.parseInt(horsepowerStr);
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("horsepowerInvalid", true);
            formValid = false;
        }

        float engineVolume = 0;
        try {
            String engineVolumeStr = req.getParameter("engineVolume");
            engineVolume = Float.parseFloat(engineVolumeStr);
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("engineVolumeInvalid", true);
            formValid = false;
        }

        String leftWheelStr = req.getParameter("leftWheel");
        boolean leftWheel = leftWheelStr == null || !leftWheelStr.equals("false");

        if (!formValid) {
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
        } else {
            try {
                ModelService modelService = (ModelService) getServletContext().getAttribute("modelService");
                int modelId = modelService.saveIfNotExists(make, model);
                Car car = new Car(
                        modelId,
                        Body.getById(bodyId),
                        Transmission.getById(transmissionId),
                        Engine.getById(engineId),
                        Drive.getById(driveId),
                        engineVolume,
                        year,
                        horsepower,
                        leftWheel
                );
                CarService carService = (CarService) getServletContext().getAttribute("carService");
                int id = carService.saveIfNotExists(car);
                car.setId(id);
                UserService userService = (UserService) getServletContext().getAttribute("userService");
                userService.addCarToCurrentUser(car, req, resp);
                jsonResponse.put("success", true);
                resp.getWriter().write(jsonResponse.toString());
            } catch (SaveException e) {
                Logger.getLogger(getClass().getName()).severe(e.toString());
                jsonResponse.put("unknown_error", true);
                jsonResponse.put("success", false);
                resp.getWriter().write(jsonResponse.toString());
            } catch (UserNotAuthenticatedException e) {
                jsonResponse.put("unauthorized", true);
                jsonResponse.put("success", false);
                resp.getWriter().write(jsonResponse.toString());
            }
        }
    }
}
