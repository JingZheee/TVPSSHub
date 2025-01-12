package com.example.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.example.model.UserViewModel;

import bdUtil.HibernateCF;

@Repository
public class UserDAO {

    public void saveUser(UserViewModel user) {
        System.out.println("Entering saveUser method with user: " + user);
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            System.out.println("Attempting to save user: " + user);
            session.save(user);
            transaction.commit();
            System.out.println("User saved: " + user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<UserViewModel> findUsersBySchool(String schoolName) {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            Query<UserViewModel> query = session.createQuery("FROM UserViewModel WHERE lower(school) = :schoolName",
                    UserViewModel.class);
            query.setParameter("schoolName", schoolName.toLowerCase());
            return query.list();
        } finally {
            session.close();
        }
    }

    public UserViewModel findUserByEmail(String email) {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            Query<UserViewModel> query = session.createQuery("FROM UserViewModel WHERE email = :email",
                    UserViewModel.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    public List<UserViewModel> findUsersByIC(String identityCardNumber) {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            Query<UserViewModel> query = session.createQuery(
                    "FROM UserViewModel WHERE identityCardNumber = :identityCardNumber",
                    UserViewModel.class);
            query.setParameter("identityCardNumber", identityCardNumber);
            return query.list();
        } finally {
            session.close();
        }
    }

    public List<UserViewModel> findUsersByFilter(String schoolName, String name, String email, Integer role) {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            StringBuilder hql = new StringBuilder("FROM UserViewModel WHERE lower(school) = :schoolName");

            if (name != null && !name.isEmpty()) {
                hql.append(" AND lower(fullName) LIKE :name");
            }
            if (email != null && !email.isEmpty()) {
                hql.append(" AND lower(email) LIKE :email");
            }
            if (role != null) {
                hql.append(" AND role = :role");
            }

            Query<UserViewModel> query = session.createQuery(hql.toString(), UserViewModel.class);
            query.setParameter("schoolName", schoolName.toLowerCase());

            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name.toLowerCase() + "%");
            }
            if (email != null && !email.isEmpty()) {
                query.setParameter("email", "%" + email.toLowerCase() + "%");
            }
            if (role != null) {
                query.setParameter("role", role);
            }

            return query.list();
        } finally {
            session.close();
        }
    }

    public UserViewModel findUserById(Long id) {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            return session.get(UserViewModel.class, id);
        } finally {
            session.close();
        }
    }

    public void deleteUserById(Long id) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Find the user by ID
            UserViewModel user = session.get(UserViewModel.class, id);
            if (user != null) {
                session.delete(user); // Delete the user
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

    public void updateUser(UserViewModel user) {
        Session session = HibernateCF.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Use the session to update the user object
            session.update(user); // Hibernate will automatically generate the update query

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of error
            }
            e.printStackTrace(); // Log the error
        } finally {
            session.close(); // Close the session after the operation
        }
    }

    public List<UserViewModel> getAllUsers() {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            Query<UserViewModel> query = session.createQuery("FROM UserViewModel", UserViewModel.class);
            return query.list(); // Returning the list of users
        } finally {
            session.close();
        }
    }

    public UserViewModel getUserByEmail(String email) {
        Session session = HibernateCF.getSessionFactory().openSession();
        try {
            Query<UserViewModel> query = session.createQuery(
                "FROM UserViewModel WHERE email = :email", 
                UserViewModel.class
            );
            query.setParameter("email", email);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
}
