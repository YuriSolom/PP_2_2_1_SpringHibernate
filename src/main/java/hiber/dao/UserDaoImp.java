package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public List<User> getUser(String model, int series) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("select user from User user where " +
                "user.userCar.model = :modelParam and user.userCar.series = :seriesParam");
        query.setParameter("modelParam", model);
        query.setParameter("seriesParam", series);
        List<User> userList = query.getResultList();
        return userList.isEmpty() ? Collections.EMPTY_LIST : userList;
    }
}
