package com.kronos.api.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kronos.api.entity.Subject;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class SubjectDao {

    @Autowired
    private SessionFactory factory;

    public Subject getSubjectById(long subjectId) {
        Session session = null;
        Subject subject = null;
        try {
            session = factory.openSession();
            subject = session.get(Subject.class, subjectId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return subject;
    }

    public List<Subject> getAllSubjects() {
        Session session = null;
        List<Subject> list = null;
        try {
            session = factory.openSession();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
            Root<Subject> root = cq.from(Subject.class);
            cq.select(root);

            list = session.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    public Subject createSubject(Subject subject) {
        Session session = null;
        Subject sub = null;
        try {
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();

            // Check if subject with same name exists
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
            Root<Subject> root = cq.from(Subject.class);

            Predicate nameEq = cb.equal(root.get("name"), subject.getName());
            cq.select(root).where(nameEq);

            List<Subject> list = session.createQuery(cq).getResultList();

            if (list.isEmpty()) {
                session.persist(subject);
                transaction.commit();
                sub = subject;
            } else {
                transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return sub;
    }

    public Subject updateSubject(Subject subjectDetails) {
        Session session = null;
        Subject sub = null;
        try {
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            session.merge(subjectDetails);
            transaction.commit();
            sub = subjectDetails;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return sub;
    }

    public String deleteSubject(long id) {
        Session session = null;
        String msg = null;
        try {
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            Subject subject = session.get(Subject.class, id);

            if (subject != null) {
                session.remove(subject);
                transaction.commit();
                msg = "Deleted !!";
            } else {
                msg = "Not Exists !!";
            }
        } catch (Exception e) {
            msg = null;
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return msg;
    }
}
