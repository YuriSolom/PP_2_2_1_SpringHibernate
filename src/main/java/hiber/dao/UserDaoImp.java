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

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public List<User> getUser(String model, int series) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("select user from User user join " +
                "user.userCar c where c.model = :modelParam and c.series = :seriesParam");
        query.setParameter("modelParam", model);
        query.setParameter("seriesParam", series);
        List<User> userList = query.getResultList();
        return userList.isEmpty() ? Collections.EMPTY_LIST : userList;
    }
}
