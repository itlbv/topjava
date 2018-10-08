package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMap;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static MealDao dao;

    private static LocalTime START_TIME = LocalTime.MIN;
    private static LocalTime END_TIME = LocalTime.MAX;
    private static final int CALORIES_PER_DAY = 2000;

    private static String EDIT_PAGE = "/mealEdit.jsp";
    private static String LIST_ALL = "/meals.jsp";

    public MealServlet() {
        super();
        dao = new MealDaoMap();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        String jspToShow;
        String action = req.getParameter("action");

        if (action == null) {
            jspToShow = LIST_ALL;
            req.setAttribute("meals", dao.getAll(START_TIME,END_TIME,CALORIES_PER_DAY));
            req.setAttribute("dateTimeFormatter", dateTimeFormatter);
            RequestDispatcher view = req.getRequestDispatcher(jspToShow);
            view.forward(req, resp);
        }

        if (action.equalsIgnoreCase("insert")){
            jspToShow = EDIT_PAGE;
            req.setAttribute("id" , 0);

        } else if (action.equalsIgnoreCase("edit")) {
            jspToShow = EDIT_PAGE;
            int id = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = dao.getById(id);
            req.setAttribute("id", id);
            req.setAttribute("meal", meal);

        } else if (action.equalsIgnoreCase("delete")){
            jspToShow = LIST_ALL;
            int id = Integer.parseInt(req.getParameter("mealId"));
            dao.delete(id);
            req.setAttribute("meals", dao.getAll(START_TIME,END_TIME,CALORIES_PER_DAY));

        } else {
            jspToShow = LIST_ALL;
            req.setAttribute("meals", dao.getAll(START_TIME,END_TIME,CALORIES_PER_DAY));
        }

        req.setAttribute("dateTimeFormatter", dateTimeFormatter);
        RequestDispatcher view = req.getRequestDispatcher(jspToShow);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ISO_DATE_TIME);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        Meal meal = dao.getById(id);
        if (meal == null) {
            dao.add(dateTime,description,calories);
        } else {
            dao.update(id, dateTime, description, calories);
        }

        RequestDispatcher view = req.getRequestDispatcher(LIST_ALL);
        req.setAttribute("dateTimeFormatter", dateTimeFormatter);
        req.setAttribute("meals", dao.getAll(START_TIME,END_TIME,CALORIES_PER_DAY));
        view.forward(req, resp);
    }
}
