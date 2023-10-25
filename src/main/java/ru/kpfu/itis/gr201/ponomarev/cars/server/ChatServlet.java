package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.MessageDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.MessageDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Message;
import ru.kpfu.itis.gr201.ponomarev.cars.service.MessageService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "chatServlet", urlPatterns = "/chat")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageService messageService = (MessageService) getServletContext().getAttribute("messageService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");

        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        String adIdStr = req.getParameter("ad_id");
        String senderIdStr = req.getParameter("sender_id");
        if (req.getParameter("format") != null && req.getParameter("format").equals("json")) {
            resp.setContentType("application/json");

            JSONObject jsonResponse = new JSONObject();

            int adId = 0;
            int senderId = 0;
            try {
                adId = Integer.parseInt(adIdStr);
                senderId = Integer.parseInt(senderIdStr);
            } catch (NumberFormatException | NullPointerException e) {
                jsonResponse.put("wrong_id", true);
                resp.getWriter().write(jsonResponse.toString());
                return;
            }
            List<MessageDto> messages = messageService.getAllOfAdvertisementAndUser(adId, senderId);
            jsonResponse.put("messages", messages);
            jsonResponse.put("senderId", senderId);
            resp.getWriter().write(jsonResponse.toString());
        } else {
            int adId = 0;
            try {
                adId = Integer.parseInt(adIdStr);
            } catch (NumberFormatException | NullPointerException e) {
                resp.sendRedirect(req.getContextPath() + "/advertisements/my");
                return;
            }

            int senderId = 0;
            try {
                senderId = Integer.parseInt(senderIdStr);
            } catch (NumberFormatException | NullPointerException e) {
                List<UserDto> chats = messageService.getAllRecipientsOfAdvertisement(adId, user.getId());
                req.setAttribute("adId", adId);
                req.setAttribute("chats", chats);
                req.getRequestDispatcher("/msgsenders.ftl").forward(req, resp);
                return;
            }

            List<MessageDto> messages = messageService.getAllOfAdvertisementAndUser(adId, senderId);
            req.setAttribute("messages", messages);
            req.setAttribute("senderId", senderId);
            req.setAttribute("adId", adId);
            req.getRequestDispatcher("/chat.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        UserService userService = (UserService) getServletContext().getAttribute("userService");

        JSONObject jsonResponse = new JSONObject();

        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user == null) {
            jsonResponse.put("unauthorized", true);
            resp.getWriter().write(jsonResponse.toString());
        } else {
            int recipientId = Integer.parseInt(req.getParameter("toUserId"));
            int adId = Integer.parseInt(req.getParameter("adId"));
            MessageDao messageDao = (MessageDao) getServletContext().getAttribute("messageDao");
            MessageService messageService = (MessageService) getServletContext().getAttribute("messageService");
            Message msg = new Message(
                    user.getId(),
                    recipientId,
                    adId,
                    req.getParameter("message"),
                    Timestamp.valueOf(LocalDateTime.now())
            );
            messageDao.send(msg);
            MessageDto messageDto = messageService.toMessageDto(msg);
            jsonResponse.put("success", true);
            jsonResponse.put("message", new JSONObject(messageDto));
            resp.getWriter().write(jsonResponse.toString());
        }
    }
}
