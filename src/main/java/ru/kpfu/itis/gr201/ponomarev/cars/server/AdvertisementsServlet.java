package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.AdvertisementDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;

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
            AdvertisementDto advertisement = advertisementService.get(Integer.parseInt(req.getParameter("id")));
            AdvertisementDao advertisementDao = (AdvertisementDao) getServletContext().getAttribute("advertisementDao");
            req.setAttribute("advertisement", advertisement);
            req.getRequestDispatcher("/ad.ftl").forward(req, resp);
            advertisementDao.incrementViewCount(advertisement.getId());
        }
    }
}
