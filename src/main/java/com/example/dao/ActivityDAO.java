package com.example.dao;

import com.example.model.ActivityViewModel;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import bdUtil.HibernateCF;
import org.hibernate.Hibernate;


import java.util.List;


@Repository
public class ActivityDAO {
    
    // Save an activity
    public void saveActivity(ActivityViewModel activity) {
    	System.out.println("Activity to be saved: " + activity);
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(activity);
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

    // Find an activity by ID
    public ActivityViewModel findActivityById(int id) {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            return session.get(ActivityViewModel.class, id);
        } finally {
            session.close();
        }
    }

    public List<ActivityViewModel> getFilteredActivities(String searchKeyword, String location) {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            StringBuilder queryString = new StringBuilder("FROM ActivityViewModel WHERE 1=1");

            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                queryString.append(" AND (title LIKE :searchKeyword OR organizer LIKE :searchKeyword)");
            }

            if (location != null && !location.isEmpty()) {
                queryString.append(" AND district = :location");
            }

            Query<ActivityViewModel> query = session.createQuery(queryString.toString(), ActivityViewModel.class);

            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                query.setParameter("searchKeyword", "%" + searchKeyword + "%");
            }

            if (location != null && !location.isEmpty()) {
                query.setParameter("location", location);
            }

            return query.list();
        } finally {
            session.close();
        }
    }

    // Get all activities
    public List<ActivityViewModel> getAllActivities() {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            Query<ActivityViewModel> query = session.createQuery("FROM ActivityViewModel", ActivityViewModel.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Update an activity
    public void updateActivity(ActivityViewModel activity) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(activity);
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

    
    // Delete an activity
    public void deleteActivityById(int id) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ActivityViewModel activity = session.get(ActivityViewModel.class, id);
            if (activity != null) {
                session.delete(activity);
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
