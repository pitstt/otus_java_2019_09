package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.api.service.HibernateTemplate;
import ru.otus.api.service.UserAuthService;
import ru.otus.domain.User;

import javax.servlet.http.HttpServlet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String CREATE_USER_ID = "create_user_id";
    private static final String ERROR = "error";

    private final HibernateTemplate<User> hibernateTemplate;
    private UserAuthService userAuthService;

    public UsersServlet(HibernateTemplate<User> hibernateTemplate, UserAuthService userAuthService) {
        this.hibernateTemplate = hibernateTemplate;
        this.userAuthService = userAuthService;
        this.userAuthService = userAuthService;
    }


    @GetMapping({"/"})
    public String indexView() {
        return "login.html";
    }

    @PostMapping("/login")
    public RedirectView loginSubmit(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        AtomicReference<RedirectView> redirectView = new AtomicReference<>();
        if (userAuthService.authenticate(user.getLogin(), user.getPassword())) {
            redirectView.set(new RedirectView("/users", true));
        } else {
            redirectView.set(new RedirectView("/login", true));
        }
        return redirectView.get();
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        AtomicReference<RedirectView> redirectView = new AtomicReference<>();
        if (hibernateTemplate.findByLogin(user.getLogin()).equals(Optional.empty())) {
            long id = hibernateTemplate.create(user);
            redirectView.set(new RedirectView("/users", true));
        }
        return redirectView.get();
    }

}
