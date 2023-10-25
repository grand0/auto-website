package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.BookmarksDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.AdvertisementDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.BookmarksService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "advertisementsServlet", urlPatterns = "/advertisements")
public class AdvertisementsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdvertisementService advertisementService = (AdvertisementService) getServletContext().getAttribute("advertisementService");
        if (req.getParameter("id") == null) {
            List<AdvertisementDto> advertisements = advertisementService.getAll();
            req.setAttribute("advertisements", advertisements);
            req.getRequestDispatcher("/ads.ftl").forward(req, resp);
        } else {
            int adId = Integer.parseInt(req.getParameter("id"));
            AdvertisementDto advertisement = advertisementService.get(adId);
            UserService userService = (UserService) getServletContext().getAttribute("userService");
            UserDto user = userService.getAuthedUserDto(req, resp);
            boolean isBookmarked = false;
            boolean isMy = false;
            if (user != null) {
                BookmarksService bookmarksService = (BookmarksService) getServletContext().getAttribute("bookmarksService");
                isBookmarked = bookmarksService.isBookmarked(user.getId(), adId);
                isMy = advertisement.getSeller().getId() == user.getId();
            }
            AdvertisementDao advertisementDao = (AdvertisementDao) getServletContext().getAttribute("advertisementDao");
            req.setAttribute("advertisement", advertisement);
            req.setAttribute("isBookmarked", isBookmarked);
            req.setAttribute("isMy", isMy);
            req.getRequestDispatcher("/ad.ftl").forward(req, resp);
            advertisementDao.incrementViewCount(advertisement.getId());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JSONObject jsonResponse = new JSONObject();

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            jsonResponse.put("unauthorized", true);
            resp.getWriter().write(jsonResponse.toString());
            return;
        }

        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            resp.getWriter().write("{}");
        } else {
            if (action.equals("bookmark")) {
                BookmarksDao bookmarksDao = (BookmarksDao) getServletContext().getAttribute("bookmarksDao");
                BookmarksService bookmarksService = (BookmarksService) getServletContext().getAttribute("bookmarksService");
                String adIdStr = req.getParameter("adId");
                try {
                    int adId = Integer.parseInt(adIdStr);
                    boolean isBookmarked = bookmarksService.isBookmarked(user.getId(), adId);
                    if (isBookmarked) {
                        bookmarksDao.remove(user.getId(), adId);
                    } else {
                        bookmarksDao.add(user.getId(), adId);
                    }
                    jsonResponse.put("isBookmarked", !isBookmarked);
                    resp.getWriter().write(jsonResponse.toString());
                } catch (NumberFormatException e) {
                    resp.getWriter().write("{}");
                }
            }
        }
    }
}
