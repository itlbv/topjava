package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static List<MealWithExceed> meals = Arrays.asList(
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, false),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, false),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, true),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, true),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, false),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, false)
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        req.setAttribute("meals", meals);
        req.setAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
