package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.MakeDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.ModelDao;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Make;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "hintsServlet", urlPatterns = "/hints")
public class HintsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String action = req.getParameter("action");
        if (action == null) action = "";

        if (action.equalsIgnoreCase("getMakes")) {
            MakeDao makeDao = (MakeDao) getServletContext().getAttribute("makeDao");
            String query = req.getParameter("query");
            if (query == null) query = "";
            List<String> makes = makeDao.search(query)
                    .stream()
                    .map(Make::getMake)
                    .collect(Collectors.toList());
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("makes", makes);
            resp.getWriter().write(jsonResponse.toString());
        } else if (action.equalsIgnoreCase("getModels")) {
            ModelDao modelDao = (ModelDao) getServletContext().getAttribute("modelDao");
            String query = req.getParameter("query");
            if (query == null) query = "";
            String make = req.getParameter("make");
            if (make != null && make.isEmpty()) make = null;
            List<String> models = modelDao.search(make, query)
                    .stream()
                    .map(Model::getModel)
                    .collect(Collectors.toList());
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("models", models);
            resp.getWriter().write(jsonResponse.toString());
        } else {
            resp.sendError(400);
        }
    }
}
