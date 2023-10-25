package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.BookmarksService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "bookmarksServlet", urlPatterns = "/bookmarks")
public class BookmarksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        BookmarksService bookmarksService = (BookmarksService) getServletContext().getAttribute("bookmarksService");
        List<AdvertisementDto> advertisements = bookmarksService.getAllOfUser(user.getId());
        req.setAttribute("advertisements", advertisements);
        req.getRequestDispatcher("/bookmarks.ftl").forward(req, resp);
    }
}
