package ru.otus.hibernate;

import api.model.AddressDataSet;
import api.model.PhoneDataSet;
import api.model.User;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.l019.api.dao.UserDao;
import ru.otus.l019.api.service.JdbcTemplate;
import ru.otus.l019.api.service.JdbcTemplateImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        JdbcTemplate dbServiceUser = new JdbcTemplateImpl(userDao);

        User vasya = new User(0, "Вася", 11);
        vasya.setAddressDataSet(new AddressDataSet("1 street"));

        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet("123", vasya));
        phones.add(new PhoneDataSet("321", vasya));
        vasya.setPhones(phones);

        long id = dbServiceUser.create(vasya);
        Optional<User> mayBeCreatedUser = dbServiceUser.load(id, User.class);


        User petya = new User(1l, "Петя", 12);
        petya.setAddressDataSet(new AddressDataSet("2 street"));
        List<PhoneDataSet> newPhones = new ArrayList<>();
        newPhones.add(new PhoneDataSet("333", petya));
        newPhones.add(new PhoneDataSet("222", petya));
        petya.setPhones(newPhones);

        dbServiceUser.update(petya);
        Optional<User> mayBeUpdatedUser = dbServiceUser.load(id, User.class);

        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }
}
