package ru.kpfu.itis.gr201.ponomarev.cars.listener;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.service.impl.UserServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = new UserDao();
        sce.getServletContext().setAttribute("userDao", userDao);
        sce.getServletContext().setAttribute("userService", new UserServiceImpl(userDao));
    }
}
