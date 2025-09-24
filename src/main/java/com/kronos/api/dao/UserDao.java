package com.kronos.api.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kronos.api.entity.User;
import com.kronos.api.model.LoginRequest;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory factory;

    public User loginUser(LoginRequest request) {
        Session session = null;
        User user = null;
        try {
            session = factory.openSession();
            user = session.get(User.class, request.getUsername());
            if (user != null && user.getPassword().equals(request.getPassword())) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    public String deleteUserById(String username) {
        Session session = null;
        Transaction tx = null;
        String msg = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            User user = session.get(User.class, username);
            if (user != null) {
                session.remove(user);
                tx.commit();
                msg = "deleted";
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            msg = null;
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return msg;
    }

    public User updateUser(User user) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public List<User> getAllUser() {
        Session session = null;
        List<User> list = null;
        try {
            session = factory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            list = session.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    public User getUserByName(String username) {
        Session session = null;
        User user = null;
        try {
            session = factory.openSession();
            user = session.get(User.class, username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return user;
    }

    public User registerUser(User user) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            User existing = session.get(User.class, user.getUsername());
            if (existing == null) {
                session.persist(user);
                tx.commit();
                return user;
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    public List<User> getAllAdmins() {
        Session session = null;
        List<User> list = null;
        try {
            session = factory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.equal(root.get("role"), "admin"));
            list = session.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    public List<User> getAllFaculties() {
        Session session = null;
        List<User> list = null;
        try {
            session = factory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.equal(root.get("role"), "faculty"));
            list = session.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }
}
