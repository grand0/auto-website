package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.AdvertisementDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.AdvertisementImagesDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.CarDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Advertisement;
import ru.kpfu.itis.gr201.ponomarev.cars.model.AdvertisementImage;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Condition;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UsersCarsService;
import ru.kpfu.itis.gr201.ponomarev.cars.util.CloudinaryUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "newAdvertisementServlet", urlPatterns = "/advertisements/new")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 60 * 1024 * 1024)
public class NewAdvertisementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersCarsService usersCarsService = (UsersCarsService) getServletContext().getAttribute("usersCarsService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
        } else {
            List<CarDto> cars = usersCarsService.getNotAdvertisedCarsOfUser(user.getId());
            req.setAttribute("cars", cars);
            Condition[] conditions = Condition.values();
            req.setAttribute("conditions", conditions);
            req.setAttribute("id", req.getParameter("id"));
            req.getRequestDispatcher("/newad.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        JSONObject jsonResponse = new JSONObject();

        List<Part> photosParts = new ArrayList<>();
        try {
            for (Part part : req.getParts()) {
                if (part.getSubmittedFileName() != null) {
                    if (part.getContentType() != null && (part.getContentType().endsWith("jpeg") || part.getContentType().endsWith("png"))) {
                        photosParts.add(part);
                    } else {
                        jsonResponse.put("photoUnsupportedFormat", true);
                        break;
                    }
                }
            }
        } catch (IllegalStateException e) {
            jsonResponse.put("photoTooBig", true);
        }
        if (photosParts.isEmpty()) {
            jsonResponse.put("noPhotos", true);
        }

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            jsonResponse.put("unauthorized", true);
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
            return;
        }

        int carId = 0;
        try {
            String carIdStr = req.getParameter("carId");
            carId = Integer.parseInt(carIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("carIdInvalid", true);
        }

        String description = req.getParameter("description");
        if (description == null || description.isEmpty()) {
            jsonResponse.put("descriptionInvalid", true);
        }

        int mileage = 0;
        try {
            String mileageStr = req.getParameter("mileage");
            mileage = Integer.parseInt(mileageStr);
            if (mileage < 0) throw new NumberFormatException();
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("mileageInvalid", true);
        }

        String color = req.getParameter("color");
        if (color == null || color.isEmpty()) {
            jsonResponse.put("colorInvalid", true);
        }

        Condition condition = null;
        try {
            String conditionIdStr = req.getParameter("conditionId");
            int conditionId = Integer.parseInt(conditionIdStr);
            condition = Condition.getById(conditionId);
            if (condition == null) throw new NumberFormatException();
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("conditionIdInvalid", true);
        }

        int owners = 0;
        try {
            String ownersStr = req.getParameter("owners");
            owners = Integer.parseInt(ownersStr);
            if (owners <= 0) throw new NumberFormatException();
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("ownersInvalid", true);
        }

        String exchangeAllowedStr = req.getParameter("exchangeAllowed");
        boolean exchangeAllowed = exchangeAllowedStr != null && exchangeAllowedStr.equals("true");

        int price = 0;
        try {
            String priceStr = req.getParameter("price");
            price = Integer.parseInt(priceStr);
            if (price <= 0) throw new NumberFormatException();
        } catch (NumberFormatException | NullPointerException e) {
            jsonResponse.put("priceInvalid", true);
        }

        if (!jsonResponse.isEmpty()) {
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
        } else {
            try {
                Advertisement advertisement = new Advertisement(
                        carId,
                        description,
                        price,
                        user.getId(),
                        Timestamp.valueOf(LocalDateTime.now()),
                        mileage,
                        color,
                        condition,
                        owners,
                        exchangeAllowed,
                        0
                );
                AdvertisementDao advertisementDao = (AdvertisementDao) getServletContext().getAttribute("advertisementDao");
                advertisementDao.save(advertisement);
                AdvertisementImagesDao advertisementImagesDao = (AdvertisementImagesDao) getServletContext().getAttribute("advertisementImagesDao");
                for (Part part : photosParts) {
                    String photoUrl = CloudinaryUtil.uploadPart(part);
                    advertisementImagesDao.save(
                            new AdvertisementImage(
                                    advertisement.getId(),
                                    photoUrl
                            )
                    );
                }
                jsonResponse.put("success", true);
                jsonResponse.put("id", advertisement.getId());
                resp.getWriter().write(jsonResponse.toString());
            } catch (SaveException e) {
                Logger.getLogger(getClass().getName()).severe(e.toString());
                jsonResponse.put("unknown_error", true);
                jsonResponse.put("success", false);
                resp.getWriter().write(jsonResponse.toString());
            }
        }
    }
}
