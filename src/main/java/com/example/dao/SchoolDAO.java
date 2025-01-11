package com.example.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.example.model.School;
import bdUtil.HibernateCF;


@Repository
public class SchoolDAO {

    public List<School> getAllSchools() {
        Session session = HibernateCF.getSessionFactory().openSession();
        List<School> schools = null;
        try {
            Query<School> query = session.createQuery("from School", School.class);
            schools = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return schools;
    }

    public void saveSchool(School school) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (school.getId() == 0) { // Only save for new records
                session.save(school);
            } else {
                session.saveOrUpdate(school);
            }
            transaction.commit();
            System.out.println("School saved successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving school: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }



    public School getSchoolById(int id) {
        Session session = HibernateCF.getSessionFactory().openSession();
        School school = null;
        try {
            school = session.get(School.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return school;
    }

    public void deleteSchool(int id) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            School school = session.get(School.class, id);
            if (school != null) {
                session.delete(school);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
