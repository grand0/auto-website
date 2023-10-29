package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.CarDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UsersCarsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "profileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        AdvertisementService advertisementService = (AdvertisementService) getServletContext().getAttribute("advertisementService");
        UsersCarsService usersCarsService = (UsersCarsService) getServletContext().getAttribute("usersCarsService");
        String useridStr = req.getParameter("id");
        if (useridStr != null) {
            int userId;
            try {
                userId = Integer.parseInt(useridStr);
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/profile");
                return;
            }
            UserDto user = userService.get(userId);
            List<AdvertisementDto> advertisements = advertisementService.getAllOfUser(userId);
            List<CarDto> cars = usersCarsService.getCarsOfUser(userId);
            req.setAttribute("profile", user);
            req.setAttribute("advertisements", advertisements);
            req.setAttribute("cars", cars);
            req.getRequestDispatcher("/profile.ftl").forward(req, resp);
        } else {
            UserDto user = userService.getAuthedUserDto(req, resp);
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/auth");
            } else {
                List<AdvertisementDto> advertisements = advertisementService.getAllOfUser(user.getId());
                List<CarDto> cars = usersCarsService.getCarsOfUser(user.getId());
                req.setAttribute("profile", user);
                req.setAttribute("advertisements", advertisements);
                req.setAttribute("cars", cars);
                req.getRequestDispatcher("/profile.ftl").forward(req, resp);
            }
        }
    }
}
