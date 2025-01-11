package com.example.dao;

import com.example.model.ActivityViewModel;
import com.example.model.Feedback;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import bdUtil.HibernateCF;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class FeedbackDAO {

	public void save(Feedback feedback) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(feedback);
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

	public List<Feedback> getFeedbackByActivityId(int activityId) {
	    Session session = HibernateCF.getSessionFactory().openSession();
	    try {
	        System.out.println("Fetching feedback for activityId: " + activityId);
	        List<Feedback> feedbackList = session.createQuery("FROM Feedback WHERE activityId = :activityId", Feedback.class)
	                                             .setParameter("activityId", activityId)
	                                             .list();
	        System.out.println("Feedback fetched: " + feedbackList.size());
	        return feedbackList;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        session.close();
	    }
	}


}
