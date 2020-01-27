package ru.otus.hibernate.dao;


import api.model.User;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.l019.api.dao.UserDao;
import ru.otus.l019.api.dao.UserDaoException;
import ru.otus.l019.api.sessionmanager.SessionManager;

import java.util.Optional;

public class UserDaoHibernate<T> implements UserDao<T> {
  private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

  private final SessionManagerHibernate sessionManager;

  public UserDaoHibernate(SessionManagerHibernate sessionManager) {
    this.sessionManager = sessionManager;
  }


  @Override
  public Optional<User> load(long id,Class c) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }


  @Override
  public long create(T t) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    User user = (User) t;
    try {
      Session hibernateSession = currentSession.getHibernateSession();
      if (user.getId() > 0) {
        hibernateSession.merge(user);
      } else {
        hibernateSession.persist(user);
      }
      return user.getId();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public void update(T o) {
    create(o);
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}
