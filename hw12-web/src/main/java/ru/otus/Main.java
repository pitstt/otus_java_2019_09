package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import ru.otus.dao.UserDao;
import ru.otus.dao.UserDaoHibernate;
import ru.otus.hw13.hibernate.HibernateUtils;
import ru.otus.hw13.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.User;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerImpl;
import ru.otus.services.*;

import java.util.HashMap;
import java.util.Map;

import static ru.otus.server.SecurityType.FILTER_BASED;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String REALM_NAME = "AnyRealm";

    private static Map<Long, User> users = new HashMap<>();

    public static void main(String[] args) throws Exception {
        var dbServiceUser = templateInitialization();
        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(dbServiceUser);
        LoginService loginServiceForBasicSecurity = new HashLoginService(REALM_NAME);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                FILTER_BASED,
                userAuthServiceForFilterBasedSecurity,
                loginServiceForBasicSecurity,
                dbServiceUser,
                gson,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static HibernateTemplate templateInitialization() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        users.put(1L, new User("Крис Гир", "user1", "11111"));
        users.put(2L, new User("Ая Кэш", "user2", "11111"));
        users.put(3L, new User("Десмин Боргес", "user3", "11111"));
        users.put(4L, new User("Кетер Донохью", "user4", "11111"));
        users.put(5L, new User("Стивен Шнайдер", "user5", "11111"));
        users.put(6L, new User("Джанет Вэрни", "user6", "11111"));
        users.put(7L, new User("Брэндон Смит", "user7", "11111"));
        users.put(8L, new User("Artem", "admin", "admin"));

        var dbServiceUser = new HibernateTemplateImpl(userDao);
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            dbServiceUser.create(entry.getValue());
        }
        return dbServiceUser;
    }
}
