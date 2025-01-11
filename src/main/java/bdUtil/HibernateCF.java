package bdUtil;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import com.example.model.UserViewModel;
import com.example.model.ActivityViewModel;
import com.example.model.Feedback;
import com.example.model.School;
import com.example.model.Resource;


public class HibernateCF {
    static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");  // Load configuration file
            // Add annotated classes for all the entities used in your project
            config.addAnnotatedClass(UserViewModel.class);
            config.addAnnotatedClass(ActivityViewModel.class);
            config.addAnnotatedClass(Feedback.class);
            config.addAnnotatedClass(School.class);
            config.addAnnotatedClass(Resource.class);

            sessionFactory = config.buildSessionFactory();
        }
        return sessionFactory;
    }
}
