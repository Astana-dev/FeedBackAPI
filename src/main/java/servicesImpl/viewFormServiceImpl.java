package servicesImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import services.viewFormService;

import java.util.List;

/**
 * Created by Nurzhan on 31.10.2017.
 */

@Repository
@Transactional
public class viewFormServiceImpl implements viewFormService {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Boolean getAccessDomain(String serverName) {
        Session ses = sessionFactory.openSession();
        String sql = "select site from feedback.domain where site = '" + serverName + "'";
        Integer r = ses.createSQLQuery(sql).list().indexOf(serverName);
        ses.close();
        return r == 0;
    }

    /*строка из таблицы по ID*/
    private <T> T findByPrimaryKeyInt(Class<T> tClass, Integer primaryKey, String... fetchProfiles) {
        Session ses = sessionFactory.openSession();
        try {
            Criteria criteria = ses.createCriteria(tClass);
            if (primaryKey != null) {
                criteria.add(Restrictions.eq("id", primaryKey));
            }
            T tt = (T) criteria.uniqueResult();
            ses.close();
            return tt;
        } catch (NullPointerException e) {
            ses.close();
            return null;
        }
    }
}
