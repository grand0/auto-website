package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.MessageService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "chatsServlet", urlPatterns = "/chats")
public class ChatsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        MessageService messageService = (MessageService) getServletContext().getAttribute("messageService");
        AdvertisementService advertisementService = (AdvertisementService) getServletContext().getAttribute("advertisementService");
        List<AdvertisementDto> myAdvertisements = advertisementService.getAllOfUser(user.getId());
        List<List<UserDto>> users = myAdvertisements.stream()
                .map((ad) -> messageService.getAllRecipientsOfAdvertisement(ad.getId(), user.getId()))
                .collect(Collectors.toList());
        List<AdvertisementDto> otherAdvertisements = messageService.getAllAdvertisementsToWhichUserSentMessage(user.getId());

        Map<String, List<Integer>> unreadAdIdsToUserIds = new HashMap<>();
        messageService.getAdvertisementIdsAndSenderIdsWithUnreadMessages(user.getId())
                        .forEach((k, v) -> unreadAdIdsToUserIds.put(String.valueOf(k), v));

        req.setAttribute("myAdvertisements", myAdvertisements);
        req.setAttribute("users", users);
        req.setAttribute("otherAdvertisements", otherAdvertisements);
        req.setAttribute("unreadMap", unreadAdIdsToUserIds);
        req.getRequestDispatcher("/chats.ftl").forward(req, resp);
    }
}
