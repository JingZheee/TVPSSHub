package com.example.dao;

import com.example.model.Resource;
import bdUtil.HibernateCF;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceDAO {

    public List<Resource> getAllResources() {
        Session session = HibernateCF.getSessionFactory().openSession();
        List<Resource> resources = null;
        try {
            Query<Resource> query = session.createQuery("from Resource", Resource.class);
            resources = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return resources;
    }

    public void saveResource(Resource resource) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (resource.getId() == 0) {
                session.save(resource);
            } else {
                session.saveOrUpdate(resource);
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

    public Resource getResourceById(long id) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Resource resource = null;
        try {
            resource = session.get(Resource.class, id);
        } finally {
            session.close();
        }
        return resource;
    }


    public void deleteResource(long id) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Resource resource = session.get(Resource.class, id);
            if (resource != null) {
                session.delete(resource);
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
