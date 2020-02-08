package ru.otus.servlet;

import ru.otus.model.User;
import ru.otus.services.HibernateTemplate;
import ru.otus.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String CREATE_USER_ID = "create_user_id";
    private static final String ERROR = "error";

    private final HibernateTemplate hibernateTemplate;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, HibernateTemplate hibernateTemplate) {
        this.templateProcessor = templateProcessor;
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter(PARAM_NAME);
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        Map<String, Object> paramsMap = new HashMap<>();
        response.setContentType("text/html");
        if (hibernateTemplate.findByLogin(login).equals(Optional.empty())) {
            long id = hibernateTemplate.create(new User(name, login, password));
            paramsMap.put(CREATE_USER_ID, id);
            response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
        } else {
            paramsMap.put(ERROR, "Такой пользователь существует!");
            response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
        }
    }

}
