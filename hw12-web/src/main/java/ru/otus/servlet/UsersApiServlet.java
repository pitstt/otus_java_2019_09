package ru.otus.servlet;

import com.google.gson.Gson;
import ru.otus.model.User;
import ru.otus.services.HibernateTemplate;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class UsersApiServlet extends HttpServlet {

    private final Gson gson;
    private HibernateTemplate hibernateTemplate;


    public UsersApiServlet(HibernateTemplate hibernateTemplate, Gson gson) {
        this.hibernateTemplate = hibernateTemplate;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = hibernateTemplate.findAllUser();

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(users));
    }


}
