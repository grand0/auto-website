package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.BookmarksDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.AdvertisementDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Condition;
import ru.kpfu.itis.gr201.ponomarev.cars.model.filter.AdvertisementFilter;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.BookmarksService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "advertisementsServlet", urlPatterns = "/advertisements")
public class AdvertisementsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdvertisementService advertisementService = (AdvertisementService) getServletContext().getAttribute("advertisementService");
        if (req.getParameter("id") == null) {
            AdvertisementFilter filter = new AdvertisementFilter();
            if (req.getParameter("priceFrom") != null) {
                try {
                    filter.setPriceFrom(Integer.parseInt(req.getParameter("priceFrom")));
                } catch (NumberFormatException ignored) {}
            }
            if (req.getParameter("priceTo") != null) {
                try {
                    filter.setPriceTo(Integer.parseInt(req.getParameter("priceTo")));
                } catch (NumberFormatException ignored) {}
            }
            if (req.getParameter("mileageFrom") != null) {
                try {
                    filter.setMileageFrom(Integer.parseInt(req.getParameter("mileageFrom")));
                } catch (NumberFormatException ignored) {}
            }
            if (req.getParameter("mileageTo") != null) {
                try {
                    filter.setMileageTo(Integer.parseInt(req.getParameter("mileageTo")));
                } catch (NumberFormatException ignored) {}
            }
            if (req.getParameter("ownersFrom") != null) {
                try {
                    filter.setOwnersFrom(Integer.parseInt(req.getParameter("ownersFrom")));
                } catch (NumberFormatException ignored) {}
            }
            if (req.getParameter("ownersTo") != null) {
                try {
                    filter.setOwnersTo(Integer.parseInt(req.getParameter("ownersTo")));
                } catch (NumberFormatException ignored) {}
            }
            if (req.getParameter("exchangeAllowed") != null) {
                String exchangeStr = req.getParameter("exchangeAllowed");
                if (exchangeStr.equalsIgnoreCase("true")) {
                    filter.setExchangeAllowed(true);
                } else if (exchangeStr.equalsIgnoreCase("false")) {
                    filter.setExchangeAllowed(false);
                }
            }
            if (req.getParameter("conditions") != null) {
                try {
                    String[] conditionIds = req.getParameter("conditions").split(",");
                    List<Condition> conditions = Arrays.stream(conditionIds)
                            .map(Integer::parseInt)
                            .map(Condition::getById)
                            .collect(Collectors.toList());
                    filter.setConditions(conditions);
                } catch (Exception ignored) {}
            }

            List<AdvertisementDto> advertisements = advertisementService.getAllWithFilter(filter);
            req.setAttribute("advertisements", advertisements);
            req.setAttribute("conditions", Condition.values());
            req.setAttribute("filter", filter);
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
