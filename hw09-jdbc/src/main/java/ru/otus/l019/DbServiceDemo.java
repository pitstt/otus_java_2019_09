package ru.otus.l019;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l019.api.dao.UserDao;
import ru.otus.l019.api.model.Account;
import ru.otus.l019.api.model.User;
import ru.otus.l019.api.service.DBServiceUser;
import ru.otus.l019.api.service.DbServiceUserImpl;
import ru.otus.l019.h2.DataSourceH2;
import ru.otus.l019.jdbc.DbExecutor;
import ru.otus.l019.jdbc.dao.UserDaoJdbc;
import ru.otus.l019.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;


public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        demo.createTable(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<Object> dbExecutor = new DbExecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);


        long id = dbServiceUser.crate(new User("dbServiceUser", 11));
        Optional<Object> user = dbServiceUser.load(id, User.class);

        System.out.println(user);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{} , age:{}", ((User) crUser).getName(), ((User) crUser).getAge()),
                () -> logger.info("user was not created")
        );

        dbServiceUser.update(new User(id, "updateUser", 12));
        Optional<Object> updateUser = dbServiceUser.load(id, User.class);

        System.out.println(updateUser);
        updateUser.ifPresentOrElse(
                crUser -> logger.info("created user, name:{} , age:{}", ((User) crUser).getName(), ((User) crUser).getAge()),
                () -> logger.info("user was not created")
        );


        //Account
        long idAccount = dbServiceUser.crate(new Account("type", 11));
        Optional<Object> account = dbServiceUser.load(idAccount, Account.class);

        System.out.println(account);
        account.ifPresentOrElse(
                crUser -> logger.info("created Account, rest:{} , type:{}", ((Account) crUser).getRest(), ((Account) crUser).getType()),
                () -> logger.info("Account was not created")
        );

        dbServiceUser.update(new Account(idAccount, "updateAccount", 12));
        Optional<Object> updateAccount = dbServiceUser.load(idAccount, Account.class);

        System.out.println(updateAccount);
        updateAccount.ifPresentOrElse(
                crUser -> logger.info("update Account, rest:{} , type:{}", ((Account) crUser).getRest(), ((Account) crUser).getType()),
                () -> logger.info("Account was not created")
        );
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int(3));" +
                     "create table account(no long auto_increment, type varchar(250), rest number)")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
