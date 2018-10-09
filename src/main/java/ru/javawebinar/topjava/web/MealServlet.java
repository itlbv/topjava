package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMap;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private MealDao dao;

    private static String EDIT_PAGE = "/mealEdit.jsp";
    private static String LIST_ALL = "/meals.jsp";

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealDaoMap();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        req.setAttribute("dateTimeFormatter", dateTimeFormatter);
        String jspToShow = LIST_ALL;
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("meals", getMealList());
            RequestDispatcher view = req.getRequestDispatcher(jspToShow);
            view.forward(req, resp);
        }

        switch (action) {
            case "insert":
                jspToShow = EDIT_PAGE;
                req.setAttribute("id" , null);
                break;
            case "edit":
                jspToShow = EDIT_PAGE;
                req.setAttribute("id", Integer.parseInt(req.getParameter("mealId")));
                req.setAttribute("meal", dao.getById(Integer.parseInt(req.getParameter("mealId"))));
                break;
            case "delete":
                dao.delete(Integer.parseInt(req.getParameter("mealId")));
                resp.sendRedirect("meals");
                return;
        }

        RequestDispatcher view = req.getRequestDispatcher(jspToShow);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ISO_DATE_TIME);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String idString = req.getParameter("id");

        int id;
        boolean mealIsNew = false;
        if (idString.equals("")) {
            id = getNewId();
            mealIsNew = true;
        } else {
            id = Integer.parseInt(idString);
        }

        Meal meal = new Meal(id, dateTime, description, calories);
        if (mealIsNew) {
            dao.add(meal);
        } else {
            dao.update(meal);
        }

        RequestDispatcher view = req.getRequestDispatcher(LIST_ALL);
        req.setAttribute("dateTimeFormatter", dateTimeFormatter);
        req.setAttribute("meals", getMealList());
        view.forward(req, resp);
    }

    private synchronized int getNewId() {
        return Collections.max(dao.getAll(), Comparator.comparingInt(Meal::getId)).getId() + 1;
    }

    private List<MealWithExceed> getMealList() {
        List<Meal> mealList = dao.getAll();
        return MealsUtil.getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
