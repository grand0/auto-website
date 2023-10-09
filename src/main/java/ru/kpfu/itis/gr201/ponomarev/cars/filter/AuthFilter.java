package ru.kpfu.itis.gr201.ponomarev.cars.filter;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(filterName = "authFilter", urlPatterns = "/*")
public class AuthFilter extends HttpFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        UserDao userDao = new UserDao();
        UserService userService = new UserServiceImpl(userDao);

        if (!userService.isAuthed(req, resp)) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                String login = null;
                String password = null;
                for (Cookie c : cookies) {
                    if (c.getName().equalsIgnoreCase("userLogin")) {
                        login = c.getValue();
                    } else if (c.getName().equalsIgnoreCase("userPassword")) {
                        password = c.getValue();
                    }
                }
                if (login != null && password != null) {
                    User user = userDao.getByLoginAndPasswordHash(login, password);
                    if (user != null) {
                        userService.auth(user, false, req, resp);
                    }
                }
            }
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
