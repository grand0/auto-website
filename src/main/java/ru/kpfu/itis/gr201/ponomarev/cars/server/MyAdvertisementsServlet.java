package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.AdvertisementDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "myAdvertisementsServlet", urlPatterns = "/advertisements/my")
public class MyAdvertisementsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
        } else {
            AdvertisementService advertisementService = (AdvertisementService) getServletContext().getAttribute("advertisementService");
            List<AdvertisementDto> advertisements = advertisementService.getAllOfUser(user.getId());
            req.setAttribute("advertisements", advertisements);
            req.getRequestDispatcher("/myads.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        AdvertisementDao advertisementDao = (AdvertisementDao) getServletContext().getAttribute("advertisementDao");
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
                String adIdStr = req.getParameter("adId");
                try {
                    int adId = Integer.parseInt(adIdStr);
                    advertisementDao.delete(adId);
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
