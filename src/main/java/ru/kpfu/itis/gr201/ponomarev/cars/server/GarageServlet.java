package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.UsersCarsDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.AdvertisementDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.CarDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Advertisement;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UsersCarsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@WebServlet(name = "garageServlet", urlPatterns = "/garage")
public class GarageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersCarsService usersCarsService = (UsersCarsService) getServletContext().getAttribute("usersCarsService");
        AdvertisementDao advertisementDao = (AdvertisementDao) getServletContext().getAttribute("advertisementDao");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
        } else {
            List<CarDto> cars = usersCarsService.getCarsOfUser(user.getId());
            List<Advertisement> advertisements = advertisementDao.getAllOfUser(user.getId());
            Map<String, String> carAdvertisementMap = new LinkedHashMap<>();
            for (Advertisement advertisement : advertisements) {
            	Optional<CarDto> optionalCar = cars.stream().filter(carDto -> carDto.getId() == advertisement.getCarId()).findFirst();
                optionalCar.ifPresent(carDto -> carAdvertisementMap.put(String.valueOf(carDto.getId()), String.valueOf(advertisement.getId())));
            }
            req.setAttribute("cars", cars);
            req.setAttribute("carAdvertisementMap", carAdvertisementMap);
            req.getRequestDispatcher("/garage.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        UsersCarsDao usersCarsDao = (UsersCarsDao) getServletContext().getAttribute("usersCarsDao");
        UserService userService = (UserService) getServletContext().getAttribute("userService");

        JSONObject jsonResponse = new JSONObject();

        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            jsonResponse.put("unauthorized", true);
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
        } else {
            String action = req.getParameter("action");
            if (action != null && action.equals("delete")) {
                String carIdStr = req.getParameter("carId");
                try {
                    int carId = Integer.parseInt(carIdStr);
                    int userId = user.getId();
                    usersCarsDao.removeCarFromUser(userId, carId);
                    jsonResponse.put("success", true);
                    resp.getWriter().write(jsonResponse.toString());
                } catch (NumberFormatException | NullPointerException e) {
                    Logger.getLogger(getClass().getName()).severe(e.toString());
                    jsonResponse.put("unknownError", true);
                    jsonResponse.put("success", false);
                    resp.getWriter().write(jsonResponse.toString());
                }
            }
        }
    }
}
