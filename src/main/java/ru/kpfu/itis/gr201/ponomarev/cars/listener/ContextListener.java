package ru.kpfu.itis.gr201.ponomarev.cars.listener;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.*;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.*;
import ru.kpfu.itis.gr201.ponomarev.cars.service.*;
import ru.kpfu.itis.gr201.ponomarev.cars.service.impl.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = new UserDaoImpl();
        MakeDao makeDao = new MakeDaoImpl();
        ModelDao modelDao = new ModelDaoImpl();
        CarDao carDao = new CarDaoImpl();
        AdvertisementDao advertisementDao = new AdvertisementDaoImpl();
        AdvertisementImagesDao advertisementImagesDao = new AdvertisementImagesDaoImpl();
        UsersCarsDao usersCarsDao = new UsersCarsDaoImpl();
        MessageDao messageDao = new MessageDaoImpl();
        BookmarksDao bookmarksDao = new BookmarksDaoImpl();

        UserService userService = new UserServiceImpl(userDao, usersCarsDao);
        ModelService modelService = new ModelServiceImpl(makeDao, modelDao);
        CarService carService = new CarServiceImpl(carDao, makeDao, modelDao);
        AdvertisementService advertisementService = new AdvertisementServiceImpl(advertisementDao, advertisementImagesDao, carService, userService);
        UsersCarsService usersCarsService = new UsersCarsServiceImpl(usersCarsDao, carService, userService);
        MessageService messageService = new MessageServiceImpl(messageDao, userService, advertisementDao, advertisementService);
        BookmarksService bookmarksService = new BookmarksServiceImpl(bookmarksDao, advertisementService);

        sce.getServletContext().setAttribute("userDao", userDao);
        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("makeDao", makeDao);
        sce.getServletContext().setAttribute("modelDao", modelDao);
        sce.getServletContext().setAttribute("modelService", modelService);
        sce.getServletContext().setAttribute("carDao", carDao);
        sce.getServletContext().setAttribute("carService", carService);
        sce.getServletContext().setAttribute("advertisementDao", advertisementDao);
        sce.getServletContext().setAttribute("advertisementService", advertisementService);
        sce.getServletContext().setAttribute("advertisementImagesDao", advertisementImagesDao);
        sce.getServletContext().setAttribute("usersCarsDao", usersCarsDao);
        sce.getServletContext().setAttribute("usersCarsService", usersCarsService);
        sce.getServletContext().setAttribute("messageDao", messageDao);
        sce.getServletContext().setAttribute("messageService", messageService);
        sce.getServletContext().setAttribute("bookmarksDao", bookmarksDao);
        sce.getServletContext().setAttribute("bookmarksService", bookmarksService);
    }
}
