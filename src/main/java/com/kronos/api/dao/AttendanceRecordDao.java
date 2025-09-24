package com.kronos.api.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kronos.api.entity.AttendanceRecord;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class AttendanceRecordDao {

    @Autowired
    private SessionFactory factory;

    // ✅ Fetch all records
    public List<AttendanceRecord> getAllAttendanceRecords() {
        Session session = null;
        List<AttendanceRecord> list = null;
        try {
            session = factory.openSession();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AttendanceRecord> cq = cb.createQuery(AttendanceRecord.class);
            Root<AttendanceRecord> root = cq.from(AttendanceRecord.class);
            cq.select(root);

            list = session.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    // ✅ Fetch records by date & subjectId
    public List<AttendanceRecord> getAllAttendanceRecords(String date, long subjectId) {
        Session session = null;
        List<AttendanceRecord> list = null;
        try {
            session = factory.openSession();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AttendanceRecord> cq = cb.createQuery(AttendanceRecord.class);
            Root<AttendanceRecord> root = cq.from(AttendanceRecord.class);

            Predicate dateEq = cb.equal(root.get("date"), date);
            Predicate subjectEq = cb.equal(root.get("subject").get("id"), subjectId);

            cq.select(root).where(cb.and(dateEq, subjectEq));

            list = session.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    // ✅ Save record
    public AttendanceRecord saveAttendance(AttendanceRecord attendanceRecord) {
        Session session = null;
        AttendanceRecord record = null;
        try {
            session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(attendanceRecord); // use persist instead of save in Hibernate 6
            transaction.commit();
            record = attendanceRecord;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return record;
    }
}
