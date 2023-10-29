package ru.kpfu.itis.gr201.ponomarev.cars.filter;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.MessageService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "checkMessagesFilter", urlPatterns = "/*")
public class CheckMessagesFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserDto user = userService.getAuthedUserDto(req, resp);
        if (user != null) {
            MessageService messageService = (MessageService) getServletContext().getAttribute("messageService");
            List<Integer> unreadAdIds = messageService.getAdvertisementIdsWithUnreadMessages(user.getId());
            req.setAttribute("unread_messages_count", unreadAdIds.size());
        }

        chain.doFilter(req, resp);
    }
}
